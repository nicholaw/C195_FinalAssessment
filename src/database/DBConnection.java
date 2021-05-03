package database;

import com.mysql.cj.jdbc.MysqlDataSource;
import controller.Controller;

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

    private static DataSource getDataSource() {
        Properties properties = new Properties();
        MysqlDataSource mysqlDS = null;
        try (FileInputStream fis = new FileInputStream(new File("src\\database\\db.properties")))
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
}
