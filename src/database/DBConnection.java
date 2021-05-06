package database;

import com.mysql.cj.jdbc.MysqlDataSource;
import controller.Controller;
import customer.Customer;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
        }
        catch (SQLException e)
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
        }
        catch (IOException e)
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
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }//insertCustomer

    public boolean updateAppointment()
    {
        return false;
    }//updateAppointment

    public boolean updateCustomer()
    {
        return false;
    }//updateCustomer
}//DBConnection
