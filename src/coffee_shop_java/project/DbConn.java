/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KHEANG
 */
public class DbConn {
    private static final String serverName = "localhost";
    private static final String userName = "coffee";
    private static final String password = "not4you";
    private static final Integer port = 3306;
    private static final String dbName = "coffee_shop";
    
    public static Connection getConnection(){
        Connection cnx = null;
        
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(serverName);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(dbName);
        dataSource.setPortNumber(port);
        
        try {
            cnx = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cnx;
    }
}
