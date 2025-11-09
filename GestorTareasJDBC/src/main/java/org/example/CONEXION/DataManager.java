package org.example.CONEXION;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataManager {
    private static DataSource dataSource;

    private DataManager() {}

    public static DataSource getDataSource() {
        if (dataSource == null) {
            var ds=new MysqlDataSource();
            ds.setURL("jdbc:mysql://localhost:3306/gestortarea");
            ds.setUser("root");
            ds.setPassword("root");
            try{
                ds.setAllowMultiQueries(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            dataSource=ds;
        }
        return dataSource;
    }
}
