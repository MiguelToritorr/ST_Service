/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Sakai
 */
public class Conexion {
    
    public static Connection ConnectDB(){
        Connection conn = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st","root", "1234");
            return conn;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error de Conexion" + e);
        }
        return  conn;
    }
    
}
