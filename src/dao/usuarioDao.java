/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.conexion;
import interfaces.ImpUsuarioDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author César
 */
public class usuarioDao implements ImpUsuarioDao{
    
    private PreparedStatement ps;
    private Connection cn;
    private ResultSet rs;
    private String sql;

    @Override
    public String validar(String user, String pass) {
        String nombre="0";
        sql="select nombre from usuario where nombre=? and contraseña=?";
        try{
            cn=conexion.getConexion();
            ps=cn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs=ps.executeQuery();
            while(rs.next()){
                nombre=rs.getString("nombre");
            }
        }catch(SQLException ex){
            System.err.println("No se puedo validar: "+ex);
        }
        return nombre;
    }
    
}
