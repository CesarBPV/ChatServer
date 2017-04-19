/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author César
 */
public class conexion {
    private static final String url="jdbc:mysql://localhost:3306/dbchat";
    private static final String user="root";
    private static final String pass="root";
    private static final String driver = "com.mysql.jdbc.Driver";
    private static Connection cn;
    
    public static Connection getConexion(){
        try{
            Class.forName(driver).newInstance();
            cn=DriverManager.getConnection(url, user, pass);
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex){
            System.err.println("Error al conectar: "+ex);
        }
        return cn;
    }
    public void cerrar(){
        try {
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
