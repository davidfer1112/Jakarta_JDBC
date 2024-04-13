package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    //Se defiene la url de conexion
    private static String url = "jdbc:mysql://localhost:3306/java_curso";
    // Se define el usuario de la base de datos
    private static String userName = "root" ;
    // Se define la contraseña de la base de datos
    private static String password = "admin";

    private static Connection connection = null;

    public  static Connection getInstance() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url, userName, password);
        }
        return connection;
    }
}
