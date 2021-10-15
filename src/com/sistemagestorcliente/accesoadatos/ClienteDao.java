/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemagestorcliente.accesoadatos;

/**
 *
 * @author FacundoCordoba
 */
import com.mysql.cj.xdevapi.Statement;

import com.sistemagestorclientes.model.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
public class ClienteDao {

    private static final String URL = ClienteDao.setUrl("clientes", "localhost", "3306");
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conexion = conectar();
    

    public Connection getConexion() {
        return conexion;
    }

    private static Connection conectar() {
        Connection con = null;
        try
        {
            Class.forName(DRIVER);
            con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
        return con;
    }

    private static void agregarCliente(Cliente cliente ) {
        String sql = "INSERT INTO `cliente` (`nombre`, `apellido`, `email`, `telefono`) VALUES ('"+cliente.getNombre()+"', '"+cliente.getApellido()+"', '"+cliente.getEmail()+"','"+cliente.getTelefono()+"')";
        try
        {
            conexion.createStatement().execute(sql);
        } catch (SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
        private static void modificarCliente(Cliente cliente ) {
  
        String sql = "UPDATE `cliente` SET `nombre` = '"+cliente.getNombre()+"', `apellido` = '"+cliente.getApellido()+"', `email` = '"+cliente.getEmail()+"' , `telefono` = '"+cliente.getTelefono()+"'  WHERE `cliente`.`idCliente` = "+cliente.getIdCliente()+"";

        try
        {
            conexion.createStatement().execute(sql);
        } catch (SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }
        
        
    
    public static List<Cliente> listarCliente() throws SQLException{      
       List<Cliente> clientes = new ArrayList ();
      String sql = "SELECT idCliente,nombre,apellido,email,telefono FROM `cliente`";   
      ResultSet resultSet = conexion.createStatement().executeQuery(sql);
      while(resultSet.next()){
       Cliente cliente = new Cliente();
       cliente.setIdCliente(resultSet.getString("idCliente"));
       cliente.setNombre(resultSet.getString("nombre"));
       cliente.setApellido(resultSet.getString("apellido"));
       cliente.setEmail(resultSet.getString("email"));
       cliente.setTelefono(resultSet.getString("telefono"));
       clientes.add(cliente);
      }                                 
      return clientes;
    }
    
    
    public static void eliminarCliente (String idCliente){
        String sql ="DELETE FROM `cliente` WHERE `cliente`.`idCliente`= "+idCliente+"";
     try
        {
            conexion.createStatement().execute(sql);
        } catch (SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static String setUrl(String db, String host, String port) {
        return "jdbc:mysql://" + host + ":" + port + "/" + db + "";
    }
    
    
    public static void guardarCliente(Cliente cliente){
        if(cliente.getIdCliente() == null ){
         agregarCliente(cliente);
        }
        else{
        modificarCliente(cliente);
        }
    
    }
    
}
