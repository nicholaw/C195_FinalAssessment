package database;

import appointment.Appointment;
import com.mysql.cj.jdbc.MysqlDataSource;
import controller.Controller;
import customer.Customer;
import utils.Contact;
import utils.Country;
import utils.Division;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBConnection
{
    Connection conn;
    Controller controller;

    public DBConnection(Controller controller)
    {
        this.controller = controller;

        try
        {
            conn = getDataSource().getConnection();
            //this.printDbMetaData();
        } catch (SQLException e)
        {
            conn = null;
            e.printStackTrace();
        }
    }//constructor

    public boolean deleteAppointment(int id)
    {
		String sql =	"DELETE FROM " 				+ 
							"appointments " 		+
						"WHERE " 					+ 
							"AppointmentID = ?";
		try(var stmt = conn.prepareStatement(sql))
		{
			stmt.setInt(1, id);
			return (stmt.executeUpdate() > 0);
		} catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
    }//deleteAppointment
	
    public boolean deleteCustomer(int id) {
        String sql =	"DELETE FROM " 			+ 
							"customers " 		+
						"WHERE " 				+ 
							"CustomerID = ?";
		try(var stmt = conn.prepareStatement(sql))
		{
			stmt.setInt(1, id);
			return (stmt.executeUpdate() > 0);
		} catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
    }//deleteCustomer
	
	/**
	 *
	 */
	public Collection<Contact> getContacts() {
		var contacts = new LinkedHashSet<Contact>();
		String sql =	"SELECT " 				+ 
							"Contact_ID " 		+ 
							"Contact_Name "		+ 
							"Email " 			+ 
						"FROM " 				+ 
							"contacts " 		+ 
						"ORDER BY " 			+ 
							"Contact_Name";
		try(var stmt = conn.prepareStatement(sql)) {
			var result = stmt.executeQuery();
			while(result.next() {
				contacts.add(new Contact(result.getInt("Contact_ID"), result.getString("Contact_Name"), result.getString("Email")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return contacts;
	}//getContacts

    /**
     * Returns a collection of all the countries in the database to be displayed in the combo box for selecting
     * a customer's country.
     *
     * @return The collection of countries
     */
    public Collection<Country> getCountries() {
        String sql =    "SELECT "                   +
                            "country_id AS id, "    +
                            "country AS name "      +
                        "FROM countries "           +
                        "ORDER BY name";
        try(var stmt = conn.prepareStatement(sql))
        {
            var result = stmt.executeQuery();
            var countries = new LinkedHashSet<Country>();
            while(result.next())
            {
                countries.add(new Country(result.getInt("id"), result.getString("name")));
            }
            setCountryDivisions(countries);
            return countries;
        } catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }//getCountries

    /**
     * Returns a collection of all the customers in the database to be displayed on the customer overview scene.
     *
     * @return The collection of customers
     */
    public Collection<Customer> getCustomers()
    {
        var list = new LinkedHashSet<Customer>();
        String sql =    "SELECT "                                                                   +
                            "Customer_ID AS id, "                                                   +
                            "Customer_Name AS name, "                                               +
                            "Phone AS phone, "                                                      +
                            "countrynames.Country_ID AS country "                                   +
                        "FROM "                                                                     +
                            "customers "                                                            +
                            "LEFT JOIN ("                                                           +
                                "SELECT "                                                           +
                                    "countries.Country_ID, "                                        +
                                    "divs.Division_ID "                                             +
                                "FROM "                                                             +
                                    "countries "                                                    +
                                    "LEFT JOIN first_level_divisions AS divs "                      +
                                    "ON countries.Country_ID = divs.Country_ID) AS countrynames "   +
                            "ON customers.Division_ID = countrynames.Division_ID "                 	+
                        "ORDER BY name, id";
        try(var stmt = conn.prepareStatement(sql))
        {
            var result = stmt.executeQuery();
            while(result.next())
            {
                list.add(new Customer(result.getInt("id"), result.getString("name"),result.getInt("country"), result.getString("phone")));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }//getCustomers

    /**
     * Returns a collection of all the appointments currently scheduled by the given customer.
     *
     * @param id    Id of the customer
     * @return  The collection of appointments
     */
    public Collection<Appointment> getCustomerAppointments(int id)
    {
        var list = new LinkedHashSet<Appointment>();
        String sql =    "SELECT "                                           +
                            "appointment_id, "                              +
                            "title, "                                       +
                            "description, "                                 +
                            "type, "                                        +
                            "start, "                                       +
                            "end, "                                         +
                            "contact_name, "                                +
                            "contact_id "                                   +
                        "FROM "                                             +
                            "appointments AS appts"                         +
                            "LEFT JOIN "                                    +
                                "contacts AS conts"                         +
                            "ON appts.contact_id = conts.contact_id "       +
                        "WHERE "                                            +
                            "appts.customer_id = ? "                        +
                        "ORDER BY "                                         +
                            "start";
        try(var stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            var result = stmt.executeQuery();
            while(result.next())
            {
                list.add(new Appointment(result.getInt("appointment_id"), result.getString("title"), result.getString("description"),
                        result.getString("type"), (LocalDateTime)result.getObject("start"), (LocalDateTime)result.getObject("end"),
                        id, controller.getSessionUser().getUserId(), result.getInt("contact_id")));
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }//getCustomerAppointments

	/**
	 *
	 */
    private static DataSource getDataSource() {
        Properties properties = new Properties();
        MysqlDataSource mysqlDS = null;
        try (var fis = new FileInputStream(new File("src\\database\\db.properties")))
        {
            properties.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(properties.getProperty("URL"));
            mysqlDS.setUser(properties.getProperty("USERNAME"));
            mysqlDS.setPassword(properties.getProperty("PASSWORD"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return mysqlDS;
    }//getDataSource
	
	/**
	 *	Returns a collection of Strings describing appointments in the database which start
	 *  withing fifteen minutes of the given date and time.
	 *
	 *  @param date
	 *  @param time
	 *  return
	 */
	public Collection<String> getUpcomingAppointments(LocalDateTime dateTime, int interval, String units) {
		Collection<String> appointments = new HashSet<>();
		String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN));
		String sql =	"SELECT " 													+ 
							"Appointment_ID AS id " 								+ 
							"Title AS title, " 										+ 
							"Name AS customer, " 									+ 
							"customers.Customer_ID AS cusotmerId, " 				+ 
							"Start AS start " 										+
						"FROM " 													+ 
							"appointments " 										+ 
							"LEFT JOIN customers " 									+ 
							"ON appointments.Customer_ID = customers.Customer_ID " 	+ 
						"WHERE " 													+
							"start BETWEEN ? " 										+ 
							"AND DATE_ADD(start, INTERVAL ? ?) " 					+ 
						"ORDER BY " 												+ 
							"start, " 												+
							"id";
		try(var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, formattedDateTime);
			stmt.setInt(2, interval);
			stmt.setString(3, units);
			var result = stmt.executeQuery();
			String appointmentInfo = "";
			while(result.next()) {
				appointmentInfo = result.getString("id") + " " + result.getString("title") + " " + 
					result.getString("name") + "(id: " + result.getInt("customerId") + ")";
				appointments.add(appointmentInfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return appointments;
	} //getUpcomingAppointments
	
	/**
	 *
	 */
	public User getUser(String username) {
		String sql =	"SELECT " + 
							"User_Id, " +
							"User_Name " + 
						"FROM " + 
							"users " + 
						"WHERE " + 
							"User_Name = ?";
		try(var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			var result = stmt.executeQuery();
			if(result.next()) {
				return new User(result.getInt("User_ID"), result.getString("User_Name"));
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace;
			return null;
		}
	}//getUser

    public boolean insertAppointment(Appointment a, User user, String timestamp) {
		String sql = 	"INSERT INTO appointments (Appointment_ID, Title, Description, Type, Start, End, Create_Date, " +
							"Created_By, Last_Update, Last_Updated_By, Customer_Id, User_Id, Contact_Id) " + 
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(var stmt = conn.prepareStatement(sql)) {
			stmt.setInt		(1, a.getAppointmentId());
			stmt.setString	(2, a.getTitle());
			stmt.setString	(3, a.getDescription());
			stmt.setString	(4, a.getType());
			stmt.setString	(5, a.getStartDateTime());	//TODO: change to String with format
			stmt.setString	(6, a.getEndDateTime());	//TODO: change to String with format
			stmt.setString	(7, timestamp);
			stmt.setString	(8, user.getName());
			stmt.setString	(9, timestamp);
			stmt.setString	(10, user.getName());
			stmt.setInt		(11, a.getCustomerId());
			stmt.setInt		(12, user.getId());
			stmt.setInt		(13, a.getContactId());
			int rows = stmt.executeUpdate();
			System.out.println("Adding customer\nRows affected: " + rows);
			return (rows > 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }//insertAppointment

    public boolean insertCustomer(Customer c, String creator, String timestamp)
    {
        String sql = "INSERT INTO customers (customer_id, customer_name, address, postal_code, phone, " +
                "create_date, created_by, last_update, last_updated_by, division_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(var stmt = conn.prepareStatement(sql)) {
            stmt.setInt		(1, c.getCustomerId());
            stmt.setString	(2, c.getName());
            stmt.setString	(3, c.getAddress());
            stmt.setString	(4, c.getPostCode());
            stmt.setString	(5, c.getPhoneNum());
            stmt.setString	(6, timestamp);
            stmt.setString	(7, creator);
            stmt.setString	(8, timestamp);
            stmt.setString	(9, creator);
            stmt.setInt		(10, c.getDivisionId());
			int rows = stmt.executeUpdate();
            System.out.println("Adding customer\nRows affected: " + rows);
			return (rows > 0);
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }//insertCustomer

    private void printDbMetaData()
    {
        String sql = "SHOW TABLES";
        try(var stmt = conn.prepareStatement(sql))
        {
            var result = stmt.executeQuery();
            while(result.next())
            {
                System.out.print("" + result.getString(1) + "\n");
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }//printDbMetaData

    private void setCountryDivisions(Collection<Country> countries)
    {
        String sql = "";
        //TODO: batch queries to improve performance
        for(Country c : countries)
        {
            sql =   "SELECT "                                           +
                        "Division_ID AS divId, "                        +
                        "Division AS name, Country_ID AS countryId "    +
                    "FROM `first_level_divisions` "                     +
                    "WHERE "                                            +
                        "Country_ID = ? ORDER BY name";
            try(var stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, c.getCountryId());
                var result = stmt.executeQuery();
                while(result.next())
                {
                    c.addDivision(new Division(result.getInt("divId"), result.getString("name"),
                            result.getInt("countryId")));
                }
            } catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }//setCountryDivisions

    /**
     * Updates information of the given appointment in the database. Returns true if at least one table row
     * was affected and false otherwise.
     *
     * @param updates Updates to be enacted
     * @param appointmentId Id of customer to be updated
     * @return  True if at least one table row was affected
     */
    public boolean updateAppointment(HashMap<String, String> updates, int appointmentId)
    {
        if(updates != null)
        {
            Set<String> keys = updates.keySet();
            String sql = "UPDATE customers SET ";
            //Add the correct number of bind variables to sql statement
            for(int i = 0; i < keys.size(); i++)
            {
                if(i == keys.size() - 1)
                    sql += "? = ? ";
                else
                    sql += "? = ?, ";
            }
            sql += "WHERE CustomerID = ?";
            try(var stmt = conn.prepareStatement(sql))
            {
                int bindIndex = 1;
                for(String str : keys)
                {
                    //Contact Id column holds integers and update value must be parsed
                    if(str.equals(DBConstants.APPOINTMENT_CONTACT_ID))
                    {
                        stmt.setString(bindIndex, str);
                        bindIndex++;
                        stmt.setInt(bindIndex, Integer.parseInt(updates.get(str)));
                        bindIndex++;
                    } else
                    {
                        stmt.setString(bindIndex, str);
                        bindIndex++;
                        stmt.setString(bindIndex, updates.get(str));
                        bindIndex++;
                    }
                }//for str:keys
                stmt.setInt(bindIndex, appointmentId);
                return (stmt.executeUpdate() > 0);
            } catch(SQLException e) {
                e.printStackTrace();
                return false;
            }
        }//if updates!=null
        return false;

    }//updateAppointment

    /**
     * Updates information of the given customer in the database. Returns true if at least one table row
     * was affected and false otherwise.
     *
     * @param updates   Updates to be enacted
     * @param customerId Id of customer to be updated
     * @return  true if at least one table row was affected
     */
    public boolean updateCustomer(HashMap<String, String> updates, int customerId)
    {
        if(updates != null)
        {
            Set<String> keys = updates.keySet();
            String sql = "UPDATE customers SET ";
            //Add the correct number of bind variables to sql statement
            for(int i = 0; i < keys.size(); i++)
            {
                if(i == keys.size() - 1)
                    sql += "? = ? ";
                else
                    sql += "? = ?, ";
            }
            sql += "WHERE CustomerID = ?";
            try(var stmt = conn.prepareStatement(sql))
            {
                int bindIndex = 1;
                for(String str : keys)
                {
                    //Division id column holds integers and update value must be parsed
                    if(str.equals(CustomerColumns.CUSTOMER_DIVISION_ID.getColName()))
                    {
                        stmt.setString(bindIndex, str);
                        bindIndex++;
                        stmt.setInt(bindIndex, Integer.parseInt(updates.get(str)));
                        bindIndex++;
                    } else
                    {
                        stmt.setString(bindIndex, str);
                        bindIndex++;
                        stmt.setString(bindIndex, updates.get(str));
                        bindIndex++;
                    }
                }//for str:keys
                stmt.setInt(bindIndex, customerId);
                return (stmt.executeUpdate() > 0);
            } catch(SQLException e) {
                e.printStackTrace();
                return false;
            }
        }//if updates!=null
        return false;
    }//updateCustomer

    /**
     * Returns the given user's password if that user exists in the database.
     *
     * @param username  Username of the user in question
     * @return  Password associated with the username if it exists and null otherwise
     */
    public CharSequence validateCredentials(String username)
    {
        String sql =    "SELECT Password " +
                        "FROM users " +
                        "WHERE User_Name = ?";
        try(var stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, username);
            var result = stmt.executeQuery();
            if(result.next())
                return (CharSequence) result.getObject("password");
            else
                return null;
        } catch (SQLException e)
        {
            return null;
        }
    }//validateCredentials
}//DBConnection