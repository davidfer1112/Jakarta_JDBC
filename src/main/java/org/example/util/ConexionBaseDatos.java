package org.example.util;

import org.apache.commons.dbcp2.BasicDataSource;

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

    // Se define el pool de conexiones
    private static BasicDataSource pool;
    // Se define el metodo para obtener la conexion
    public  static BasicDataSource getInstance() throws SQLException {
        // Si el pool es nulo se crea una nueva instancia
        if(pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(userName);
            pool.setPassword(password);
            pool.setInitialSize(3); // Se definen 3 conexiones iniciales
            pool.setMinIdle(3); // Se definen 3 conexiones minimas
            pool.setMaxIdle(8); // Se definen 8 conexiones maximas
            pool.setMaxTotal(8); // Se definen 8 conexiones maximas
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }

}
