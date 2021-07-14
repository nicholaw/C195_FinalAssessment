package database;

import appointment.Appointment;
import utils.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import controller.Controller;
import customer.Customer;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Establishes a connection with the database and performs database operations such as inserting, deleting,
 * and updating customers and appointments.
 */
public class DBConnection {
    Connection conn;
    Controller controller;

    /**
     * Constructs this DBConnection.
     * @param controller
     */
    public DBConnection(Controller controller) {
        this.controller = controller;

        try {
            conn = getDataSource().getConnection();
        } catch (SQLException e) {
            conn = null;
            e.printStackTrace();
        }
    }//constructor

    /**
     * Deletes an appointment from the database. Returns true if at least one row was affected by the update
     * and false otherwise.
     * @param id    -id of the appointment to delete
     * @return      -whether a row was affected
     */
    public boolean deleteAppointment(long id) {
		String sql =	"DELETE FROM " 				+ 
							"appointments " 		+
						"WHERE " 					+ 
							"Appointment_ID = ?";
		try(var stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, id);
			return (stmt.executeUpdate() > 0);
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
    }//deleteAppointment

    /**
     * Deletes a customer from the database. Returns true if at least one row was affected by the update
     * and false otherwise.
     * @param id    -id of the customer to delete
     * @return      -whether a row was affected
     */
    public boolean deleteCustomer(long id) {
        String sql =	"DELETE FROM " 			+ 
							"customers " 		+
						"WHERE " 				+ 
							"Customer_ID = ?";
		try(var stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, id);
			return (stmt.executeUpdate() > 0);
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
    }//deleteCustomer

    /**
     * Returns a collection of all contacts stored in the database.
     * @return  -the contacts stored in the database.
     */
	public Collection<Contact> getContacts() {
		var contacts = new LinkedHashSet<Contact>();
		String sql =	"SELECT " 				+ 
							"Contact_ID, " 		+
							"Contact_Name, "	+
							"Email " 			+
						"FROM " 				+ 
							"contacts " 		+ 
						"ORDER BY " 			+ 
							"Contact_Name";
		try(var stmt = conn.prepareStatement(sql)) {
			var result = stmt.executeQuery();
			while(result.next()) {
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
     * @return -the collection of countries
     */
    public Collection<Country> getCountries() {
        String sql =    "SELECT "               +
                            "Country_ID, "      +
                            "Country "          +
                        "FROM countries "       +
                        "WHERE Country IN(" + DBConstants.SUPPORTED_COUNTRIES + ") " +
                        "ORDER BY Country";
        try(var stmt = conn.prepareStatement(sql)) {
            var result = stmt.executeQuery();
            var countries = new LinkedHashSet<Country>();
            while(result.next()) {
                countries.add(new Country(result.getInt("Country_ID"), result.getString("Country")));
            }
            setCountryDivisions(countries);
            return countries;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }//getCountries

    /**
     * Returns a collection of all the customers in the database to be displayed on the customer overview scene.
     * @return -the collection of customers
     */
    public Collection<Customer> getCustomers() {
        var list = new LinkedHashSet<Customer>();
        String sql =    "SELECT "                                                                               +
                            "customers.Customer_ID AS id, "                                                     +
                            "Customer_Name AS name, "                                                           +
                            "Phone AS phone, "                                                                  +
                            "Address AS address, "                                                              +
                            "Postal_Code AS postcode, "                                                         +
                            "countrynames.Country_ID AS country, "                                              +
                            "countrynames.Division_ID AS division, "                                            +
                            "COUNT(appointments.Appointment_ID) AS appts "                                      +
                        "FROM "                                                                                 +
                            "customers LEFT JOIN ("                                                             +
                                "SELECT "                                                                       +
                                    "countries.Country_ID, "                                                    +
                                    "divs.Division_ID "                                                         +
                                "FROM "                                                                         +
                                    "countries LEFT JOIN first_level_divisions AS divs "                        +
                                    "ON countries.Country_ID = divs.Country_ID) AS countrynames "               +
                            "ON customers.Division_ID = countrynames.Division_ID LEFT JOIN appointments "       +
                            "ON appointments.Customer_ID = customers.Customer_ID "                              +
                        "GROUP BY "                                                                             +
                            "customers.Customer_ID "                                                            +
                        "ORDER BY "                                                                             +
                            "name, "                                                                            +
                            "id";
        try(var stmt = conn.prepareStatement(sql)) {
            var result = stmt.executeQuery();
            Country parentCountry;
            Division customerDivision = null;
            while(result.next()) {
                if ((parentCountry = controller.getCountry(result.getInt("country"))) != null) {
                    customerDivision = parentCountry.getDivision(result.getInt("division"));
                }
                list.add(new Customer(result.getLong("id"), result.getString("name"), result.getString("phone"),
                        result.getString("address"), result.getString("postcode"),
                        parentCountry, customerDivision, result.getInt("appts")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }//getCustomers

    /**
     * Returns a collection of all the appointments currently scheduled by the given customer.
     * @param id    -id of the customer
     * @return      -the collection of appointments
     */
    public Collection<appointment.Appointment> getCustomerAppointments(long id)
    {
        var list = new LinkedHashSet<appointment.Appointment>();
        String sql =    "SELECT "                                           +
                            "appointment_id, "                              +
                            "title, "                                       +
                            "description, "                                 +
                            "type, "                                        +
                            "start, "                                       +
                            "end, "                                         +
                            "contact_name, "                                +
                            "appts.Contact_ID AS contact, "                 +
                            "location "                                     +
                        "FROM "                                             +
                            "appointments AS appts "                        +
                            "LEFT JOIN "                                    +
                                "contacts AS conts "                        +
                            "ON appts.contact_id = conts.contact_id "       +
                        "WHERE "                                            +
                            "appts.customer_id = ? "                        +
                        "ORDER BY "                                         +
                            "start";
        try(var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var result = stmt.executeQuery();
            while(result.next()) {
                list.add(new appointment.Appointment(result.getLong("appointment_id"), result.getString("title"), result.getString("description"),
                        Type.getType(result.getString("type")), (LocalDateTime)result.getObject("start"), (LocalDateTime)result.getObject("end"),
                        id, controller.getContact(result.getInt("contact")), Location.getLocation(result.getString("location"))));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }//getCustomerAppointments

	/**
	 * Establishes a connection with the company database.
     * @return  -the datasource for connecting to the database
	 */
    private static DataSource getDataSource() {
        Properties properties = new Properties();
        MysqlDataSource mysqlDS = null;
        try (var fis = new FileInputStream(new File("src\\database\\db.properties"))) {
            properties.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(properties.getProperty("URL"));
            mysqlDS.setUser(properties.getProperty("USERNAME"));
            mysqlDS.setPassword(properties.getProperty("PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }//getDataSource

    /**
     * Returns an array of the reports of number of appointments for each type and location during
     * the given month.
     * @param month -the month to be reported
     * @return  -the array of reports
     */
    public HashMap[] getMonthlyReports(LocalDateTime month) {
        var mapArray = new HashMap[2];
        mapArray[0] = getMonthlyReportByType(month);
        mapArray[1] = getMonthlyReportByLocation(month);
        return mapArray;
    }//getMonthlyReports

    /**
     * Returns a HashMap containing the number of appointments for each location for the given month.
     * @param month -the month to be reported
     * @return  -the number of unique appointments for each location
     */
    private HashMap<Location, Integer> getMonthlyReportByLocation(LocalDateTime month) {
        var map = new HashMap<Location, Integer>();
        for(Location l : Location.values()) {
            map.put(l, 0);
        }
        String sql =    "SELECT "                               +
                            "location, "                        +
                            "COUNT(Appointment_ID) AS count "   +
                        "FROM appointments "                    +
                        "WHERE start BETWEEN ? AND ? "          +
                        "GROUP BY location "                    +
                        "ORDER BY location";
        try(var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, month.format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
            month.plusMonths(1);
            month.minusDays(1);
            stmt.setString(2, month.format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
            var result = stmt.executeQuery();
            Location l;
            while(result.next()) {
                l = Location.getLocation(result.getString("location"));
                if(l != null) {
                    map.put(l, result.getInt("count"));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return map;
    }//getMonthlyReportByLocation

    /**
     * Returns a HashMap containing the number of appointments for each type for the given month.
     * @param month -the month to be reported
     * @return  -the number of unique appointments for each type
     */
    private HashMap<Type, Integer> getMonthlyReportByType(LocalDateTime month) {
        var map = new HashMap<Type, Integer>();
        for(Type t : Type.values()) {
            map.put(t, 0);
        }
        String sql =    "SELECT "                                   +
                            "type, "                                +
                            "COUNT(Appointment_ID) AS count "       +
                        "FROM appointments "                        +
                        "WHERE start BETWEEN ? AND ? "              +
                        "GROUP BY type "                            +
                        "ORDER BY type";
        try(var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, month.format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
            month.plusMonths(1);
            month.minusDays(1);
            stmt.setString(2, month.format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
            var result = stmt.executeQuery();
            Type t;
            while(result.next()) {
                t = Type.getType(result.getString("location"));
                if(t != null) {
                    map.put(t, result.getInt("count"));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return map;
    }//getMonthlyReportByType
	
	/**
	 *	Returns a collection of Strings describing appointments in the database which start
	 *  withing fifteen minutes of the given date and time.
	 *  @param dateTime -the current date and time
	 *  @param interval -the amount of time in which to look for existing appointments
     *  @param units    -the unit of time in which to count the interval of time
	 *  return          -the collection of upcoming appointments
	 */
	public Collection<String> getUpcomingAppointments(LocalDateTime dateTime, int interval, String units) {
		Collection<String> appointments = new HashSet<>();
		String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN));
		String sql =	"SELECT " 													+ 
							"Appointment_ID AS id, " 								+
							"Title AS title, " 										+ 
							"customers.Customer_Name AS customer, " 				+
							"customers.Customer_ID AS customerId, " 				+
							"Start AS start " 										+
						"FROM " 													+ 
							"appointments " 										+ 
							"LEFT JOIN customers " 									+ 
							"ON appointments.Customer_ID = customers.Customer_ID " 	+ 
						"WHERE " 													+
							"start BETWEEN ? " 										+ 
							"AND DATE_ADD(?, INTERVAL ? MINUTE) " 					+
						"ORDER BY " 												+ 
							"start, " 												+
							"id";
		try(var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, formattedDateTime);
            stmt.setString(2, formattedDateTime);
			stmt.setInt(3, interval);
			//stmt.setString(4, units);
			var result = stmt.executeQuery();
			String appointmentInfo = "";
			while(result.next()) {
				appointmentInfo = result.getString("id") + " " + result.getString("title") + " " + 
					result.getString("customer") + "(id: " + result.getInt("customerId") + ")";
				appointments.add(appointmentInfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return appointments;
	} //getUpcomingAppointments

    /**
     * Returns the user from the database whose unique username matches the provided String. Returns null
     * if no such customer is found.
     * @param username  -the username to search for
     * @return          -the User whose name matches the provided String
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
				return new User(result.getLong("User_ID"), result.getString("User_Name"));
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}//getUser

    /**
     * Inserts the provided new appointment into the database. Returns true if at least one row was
     * affected by the update and false otherwise.
     * @param a -the appointment to be inserted
     * @param user  -the user inserting the appointment
     * @param timestamp -the time at insertion
     * @return  -whether the appointment was successfully inserted
     */
    public boolean insertAppointment(Appointment a, User user, String timestamp) {
		String sql = 	"INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, " +
							"Created_By, Last_Update, Last_Updated_By, Customer_Id, User_Id, Contact_Id) " + 
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(var stmt = conn.prepareStatement(sql)) {
			stmt.setLong	(1, a.getAppointmentId());
			stmt.setString	(2, a.getTitle());
			stmt.setString	(3, a.getDescription());
			stmt.setString  (4, a.getLocation().getLocation());
			stmt.setString	(5, a.getType().getType());
			stmt.setString	(6, a.getStartDateTime().format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
			stmt.setString	(7, a.getEndDateTime().format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
			stmt.setString	(8, timestamp);
			stmt.setString	(9, user.getUsername());
			stmt.setString	(10, timestamp);
			stmt.setString	(11, user.getUsername());
			stmt.setLong	(12, a.getCustomerId());
			stmt.setLong	(13, user.getUserId());
			stmt.setLong	(14, a.getContactId());
			int rows = stmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }//insertAppointment

    /**
     * Inserts the provided new contact into the database. Returns true if at least one row was
     * affected by the update and false otherwise.
     * @param id    -id of the contact to insert
     * @param name  -name of the contact to insert
     * @param email -email of the contact to insert
     * @return  -whether any rows were affected by the update
     */
    private boolean insertContact(long id, String name, String email) {
        String sql = "INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES (?, ?, ?)";
        try(var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            return (stmt.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts the provided new customer into the database. Returns true if at least one row was affected
     * by the update and false otherwise.
     * @param c -the customer to insert
     * @param creator   -the user who inserted the customer
     * @param timestamp -the time the user was inserted
     * @return  -whether any rows were affected by the update
     */
    public boolean insertCustomer(Customer c, String creator, String timestamp) {
        String sql = "INSERT INTO customers (customer_id, customer_name, address, postal_code, phone, " +
                "create_date, created_by, last_update, last_updated_by, division_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(var stmt = conn.prepareStatement(sql)) {
            stmt.setLong	(1, c.getCustomerId());
            stmt.setString	(2, c.getName());
            stmt.setString	(3, c.getAddress());
            stmt.setString	(4, c.getPostCode());
            stmt.setString	(5, c.getPhone());
            stmt.setString	(6, timestamp);
            stmt.setString	(7, creator);
            stmt.setString	(8, timestamp);
            stmt.setString	(9, creator);
            stmt.setInt		(10, c.getDivision().getDivisionId());
			int rows = stmt.executeUpdate();
			return (rows > 0);
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }//insertCustomer

    /**
     *  Prints the meta data of the database table whose name matches the provided String.
     * @param tableName -name of the table whose metadata to print
     */
    private void getDBMetaData(String tableName) {
	    System.out.println("----" + tableName + "----");
        String sql = "SHOW COLUMNS FROM " + tableName;
        try(var stmt = conn.prepareStatement(sql)) {
            //stmt.setString(1, tableName);
            var result = stmt.executeQuery();
            while(result.next()) {
                try {
                    System.out.printf("%s\t%s\n", result.getString(1), result.getString(2));
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown");
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }//getDbMetaData

    /**
     * Assigns first-level-divisions in the database to each of the countries in the provided
     * collection of countries.
     * @param countries -the collection of countries
     */
    private void setCountryDivisions(Collection<Country> countries)
    {
        String sql = "";
        //TODO: batch queries to improve performance
        for(Country c : countries)
        {
            sql =   "SELECT "                                           +
                        "Division_ID, "                                 +
                        "Division, Country_ID "                         +
                    "FROM `first_level_divisions` "                     +
                    "WHERE "                                            +
                        "Country_ID = ? ORDER BY Division";
            try(var stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, c.getCountryId());
                var result = stmt.executeQuery();
                while(result.next()) {
                    c.addDivision(new Division(result.getInt("Division_ID"), result.getString("Division"), c));
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
     * @param updates Updates to be enacted
     * @param appointmentId Id of customer to be updated
     * @return  True if at least one table row was affected
     */
    public boolean updateAppointment(HashMap<String, String> updates, long appointmentId) {
        if(updates != null) {
            String sql = "UPDATE appointments SET ";
            var keys = updates.keySet();
            var values = new ArrayList<String>();
            //Add the correct number of bind variables to sql statement
            for(String str : keys) {
                if ((updates.get(str) != null) && (AppointmentColumns.contains(str))) {
                    sql += (str + " = ?, ");
                    values.add(updates.get(str));
                }
            }
            //Remove the tailing comma
            sql = sql.substring(0, sql.length() - 2);
            sql += " WHERE Appointment_ID = ?";
            try(var stmt = conn.prepareStatement(sql)) {
                int bindIndex = 1;
                for(String str : values) {
                    try {
                        //check for the id column
                        stmt.setInt(bindIndex, Integer.parseInt(str));
                    } catch(NumberFormatException e) {
                        stmt.setString(bindIndex, str);
                    }
                    bindIndex++;
                }//for str:values
                stmt.setLong(bindIndex, appointmentId);
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
     * @param updates   Updates to be enacted
     * @param customerId Id of customer to be updated
     * @return  true if at least one table row was affected
     */
    public boolean updateCustomer(HashMap<String, String> updates, long customerId) {
        if(updates != null) {
            String sql = "UPDATE customers SET ";
            var keys = updates.keySet();
            var values = new ArrayList<String>();
            //Add the correct number of bind variables to sql statement
            for(String str : keys) {
                if ((updates.get(str) != null) && (CustomerColumns.contains(str))) {
                    sql += (str + " = ?, ");
                    values.add(updates.get(str));
                }
            }
            //Remove the tailing comma
            sql = sql.substring(0, sql.length() - 2);
            sql += " WHERE Customer_ID = ?";
            try(var stmt = conn.prepareStatement(sql)) {
                int bindIndex = 1;
                for(String str : values) {
                    try {
                       stmt.setInt(bindIndex, Integer.parseInt(str));
                    } catch(NumberFormatException e) {
                        stmt.setString(bindIndex, str);
                    }
                    bindIndex++;
                }//for str:values
                stmt.setLong(bindIndex, customerId);
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
     * @param username  Username of the user in question
     * @return  Password associated with the username if it exists and null otherwise
     */
    public CharSequence validateCredentials(String username) {
        String sql =    "SELECT Password " +
                        "FROM users " +
                        "WHERE User_Name = ?";
        try(var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            var result = stmt.executeQuery();
            if(result.next()) {
                String str = result.getString("password");
                return str.subSequence(0, str.length());
            }
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }//validateCredentials
}//DBConnection