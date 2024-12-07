/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sqlserverqueryapp;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class ConexionDB {
    private static Connection connClientes = null;
    private static Connection connClientesContingencia = null;

    private static final String URL_CLIENTES = "jdbc:sqlserver://JARVIS94\\SQLEXPRESS:1433;databaseName=principal;encrypt=true;trustServerCertificate=true;";
    private static final String URL_CLIENTES_CONTINGENCIA = "jdbc:sqlserver://JARVIS94\\SQLEXPRESS:1433;databaseName=contingencia;encrypt=true;trustServerCertificate=true;";
    private static final String USUARIOP = "usuarioSQL";
    private static final String CONTRASENAP = "1234";
        private static final String USUARIOC = "usuarioSQLc";
    private static final String CONTRASENAC = "1234";
    
    // Verificar conexión a la base de datos clientes
    public static boolean verificarConexionClientes() {
        try {
            if (connClientes == null || connClientes.isClosed()) {
                connClientes = DriverManager.getConnection(URL_CLIENTES, USUARIOP, CONTRASENAP);
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    // Verificar conexión a la base de datos clientescontingencia
    public static boolean verificarConexionClientesContingencia() {
        try {
            if (connClientesContingencia == null || connClientesContingencia.isClosed()) {
                connClientesContingencia = DriverManager.getConnection(URL_CLIENTES_CONTINGENCIA, USUARIOC, CONTRASENAC);
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    // Ejecutar consulta SQL en ambas bases de datos
    public static String ejecutarConsulta(String consulta) {
        StringBuilder resultado = new StringBuilder();
        
        // Ejecutar la consulta en la base de datos clientes
        if (verificarConexionClientes()) {
            resultado.append("DB Producción:\n");
            resultado.append(ejecutarConsultaEnBaseDeDatos(connClientes, consulta));
        }
        
        // Ejecutar la consulta en la base de datos clientescontingencia
        if (verificarConexionClientesContingencia()) {
            resultado.append("DB Contingencia:\n");
            resultado.append(ejecutarConsultaEnBaseDeDatos(connClientesContingencia, consulta));
        }
        
        return resultado.toString();
    }

    // Ejecutar consulta en una base de datos específica
    private static String ejecutarConsultaEnBaseDeDatos(Connection conn, String consulta) {
        StringBuilder resultado = new StringBuilder();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnas = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    resultado.append(rs.getString(i)).append("\t");
                }
                resultado.append("\n");
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            resultado.append("Error al ejecutar la consulta: ").append(ex.getMessage()).append("\n");
        }
        return resultado.toString();
    }

    // Cerrar las conexiones a las bases de datos
    public static void cerrarConexiones() {
        try {
            if (connClientes != null && !connClientes.isClosed()) {
                connClientes.close();
            }
            if (connClientesContingencia != null && !connClientesContingencia.isClosed()) {
                connClientesContingencia.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
