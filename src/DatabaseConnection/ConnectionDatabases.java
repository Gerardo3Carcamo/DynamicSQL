/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseConnection;

import SQLService.SQLSentences;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author 000093883
 */
public class ConnectionDatabases {

    public Connection getOracleConnection(String Database, String User, String Password) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            String myDB = Database;
            String user = User;
            String password = Password;
            java.sql.Connection cnx = DriverManager.getConnection(myDB, user, password);
            return cnx;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new SQLException("No se pudo establecer conexión con la base de datos");
        }
    }

    public Connection getMySQLConnection(String Database, String User, String Password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(Database, User, Password);
    }
    
    public Connection getMariaDbConnection(String Database, String User, String Password) throws SQLException, ClassNotFoundException { 
        String url = "jdbc:mariadb://localhost:3306/"+Database;
        Connection conexion = DriverManager.getConnection(url, User, Password);
        return conexion;
    }
}
