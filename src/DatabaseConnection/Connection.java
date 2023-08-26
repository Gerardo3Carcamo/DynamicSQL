/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseConnection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 000093883
 */
public class Connection {
    
    public java.sql.Connection getOracleConnection(String Database, String User, String Password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            String myDB = Database;
            String user = User;
            String password = Password;
            java.sql.Connection cnx = DriverManager.getConnection(myDB, user, password);
            return cnx;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
    
}
