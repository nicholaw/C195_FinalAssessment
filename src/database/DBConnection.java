package database;

import com.mysql.cj.jdbc.MysqlDataSource;
import controller.Controller;
import customer.Customer;
import utils.Country;
import utils.Division;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Properties;

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

    public boolean deleteAppointment()
    {
        return false;
    }//deleteAppointment

    public boolean deleteCustomer()
    {
        return false;
    }//deleteCustomer

    public ResultSet getAllDivisions()
    {
        String sql = "SELECT Division_ID AS id, Division AS name, Country_ID AS country FROM first-level divisions " +
                "ORDER BY country, name";
        try(var stmt = conn.prepareStatement(sql))
        {
            return stmt.executeQuery();
        } catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }//getAllDivisions

    public Collection<Country> getCountries()
    {
        String sql = "SELECT country_id AS id, country AS name FROM countries ORDER BY name";
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

    public Collection<Customer> getCustomers()
    {
        var list = new LinkedHashSet<Customer>();
        String sql = "SELECT Customer_ID AS id, Customer_Name AS name, customers.Division_ID AS divId, Country_ID AS countryId " +
                "FROM customers LEFT JOIN first_level_divisions AS divs " +
                "ON customers.Division_ID = divs.Division_ID " +
                "ORDER BY name, id";
        try(var stmt = conn.prepareStatement(sql))
        {
            var result = stmt.executeQuery();
            while(result.next())
            {
                Country customerCountry = controller.getCountry(result.getInt("countryId"));
                list.add(new Customer(result.getInt("id"), result.getString("name"),
                        customerCountry, controller.getDivision(customerCountry, result.getInt("divId"))));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }//getCustomers

    private void setCountryDivisions(Collection<Country> countries)
    {
        String sql = "";
        //TODO: batch queries to improve performance
        for(Country c : countries)
        {
            sql = "SELECT Division_ID AS id, Division AS name FROM `first_level_divisions` " +
                    "WHERE Country_ID = ? ORDER BY name";
            try(var stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, c.getCountryId());
                var result = stmt.executeQuery();
                while(result.next())
                {
                    c.addDivision(new Division(result.getInt("id"), result.getString("name")));
                }
            } catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }//setCountryDivisions

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

    public boolean insertAppointment()
    {
        return false;
    }//insertAppointment

    public boolean insertCustomer(Customer c, String creator, String timestamp)
    {
        String sql = "INSERT INTO customers (customer_id, customer_name, address, postal_code, phone, " +
                        "create_date, created_by, last_update, last_updated_by, division_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(var stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, c.getCustomerId());
            stmt.setString(2, c.getName());
            stmt.setString(3, c.getAddress());
            stmt.setString(4, c.getPostCode());
            stmt.setString(5, c.getPhoneNum());
            stmt.setString(6, timestamp);
            stmt.setString(7, creator);
            stmt.setString(8, timestamp);
            stmt.setString(9, creator);
            stmt.setInt(10, c.getDivisionId());
            System.out.println("Adding customer\nRows affected: " + stmt.executeUpdate());
            return true;
        } catch(SQLException e)
        {
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

    public boolean updateAppointment()
    {
        return false;
    }//updateAppointment

    public boolean updateCustomer()
    {
        return false;
    }//updateCustomer

    private void insertTestCustomers()
    {
    }
}//DBConnection