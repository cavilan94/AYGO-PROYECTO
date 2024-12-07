/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.sqlserverqueryapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SQLServerQueryApp extends JFrame {

    private JTextArea areaResultado;
    private JTextField campoConsulta;
    private JButton btnEjecutarConsulta;
    private JButton btnVerificarConexion;
    private JButton btnVerificarMaestro;
    //private JLabel lblEstadoConexion;
    private boolean esClientes;

    // Componentes para la sincronización de las tablas
    private JComboBox<String> dbSelector;
    private JButton btnSincronizarTablas;

    public SQLServerQueryApp(boolean esClientes) {
        this.esClientes = esClientes;

        // Configuración básica de la ventana
        setTitle(esClientes ? "RESOFA" : "GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear los componentes
        campoConsulta = new JTextField(40);
        areaResultado = new JTextArea(10, 40);
        areaResultado.setEditable(false);
        JScrollPane scrollArea = new JScrollPane(areaResultado); // para ajsutar el panel de resultados

        btnEjecutarConsulta = new JButton("Ejecutar Consulta");
        btnVerificarConexion = new JButton("Verificar Conexión");
        btnVerificarMaestro = new JButton("Verificar Nodo Maestro");

    
        // Diseño de la GUI
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Parte superior de la GUI para la consulta
        JPanel panelConsulta = new JPanel();
        panelConsulta.add(new JLabel("Ingrese consulta SQL:"));
        panelConsulta.add(campoConsulta);
        panelConsulta.add(btnEjecutarConsulta);

        // Parte inferior GUI para el resultado
        JPanel panelResultado = new JPanel();
        panelResultado.setLayout(new BorderLayout());
        panelResultado.add(scrollArea, BorderLayout.CENTER);
     

        // Ajustar paneles
        panel.add(panelConsulta, BorderLayout.NORTH);
        panel.add(panelResultado, BorderLayout.CENTER);

        // Panel para los botones en la parte inferior
        JPanel panelBotonesInferiores = new JPanel();
        panelBotonesInferiores.add(btnVerificarMaestro);
        panel.add(panelBotonesInferiores, BorderLayout.WEST);
        panelBotonesInferiores.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Panel para las opciones de sincronización
        JPanel panelSincronizar = new JPanel();
        panelSincronizar.setLayout(new FlowLayout(FlowLayout.LEFT));

        // JComboBox para seleccionar la base de datos
        dbSelector = new JComboBox<>(new String[]{"DBp", "DBc"});
        // Botón para sincronizar las tablas
        btnSincronizarTablas = new JButton("Sincronizar Tablas");

        // Agregar el JComboBox y el JButton al panel
        panelSincronizar.add(dbSelector);
        panelSincronizar.add(btnSincronizarTablas);

        // Ubicar el panel de sincronización en la esquina inferior derecha
        panel.add(panelSincronizar, BorderLayout.SOUTH);

        add(panel);

        // Acción para el botón "Ejecutar Consulta"
        btnEjecutarConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = campoConsulta.getText();
                String resultado = ConexionDB.ejecutarConsulta(consulta);
                areaResultado.setText(resultado);
            }
        });

        // Acción para el botón "Verificar el nodo Maestro"
        btnVerificarMaestro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ConexionDB.verificarConexionClientes()) {
                    JOptionPane.showMessageDialog(null, "El nodo maestro es: producción");
                } else {
                    JOptionPane.showMessageDialog(null, "El nodo maestro es: Contingencia");
                }
            }
        });

        // Acción para el botón "Sincronizar Tablas"
        btnSincronizarTablas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDb = dbSelector.getSelectedItem().toString();
                try {
                    if (selectedDb.equals("DBp")) {
                        syncTables("jdbc:sqlserver://JARVIS94\\SQLEXPRESS:1433;databaseName=principal;encrypt=true;trustServerCertificate=true;", "jdbc:sqlserver://JARVIS94\\SQLEXPRESS:1433;databaseName=contingencia;encrypt=true;trustServerCertificate=true;");
                    } else {
                        syncTables("jdbc:sqlserver://JARVIS94\\SQLEXPRESS:1433;databaseName=contingencia;encrypt=true;trustServerCertificate=true;", "jdbc:sqlserver://JARVIS94\\SQLEXPRESS:1433;databaseName=principal;encrypt=true;trustServerCertificate=true;");
                    }
                    JOptionPane.showMessageDialog(null, "Tablas sincronizadas exitosamente.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al sincronizar las tablas: " + ex.getMessage());
                }
            }
        });
    }

    // Método para sincronizar las tablas
    public static void syncTables(String sourceDbUrl, String targetDbUrl) throws SQLException {
        Connection sourceConn = null;
        Connection targetConn = null;
        Statement sourceStmt = null;
        Statement targetStmt = null;

        try {
            // Conectar a las bases de datos
            sourceConn = DriverManager.getConnection(sourceDbUrl, "usuarioSQL", "1234");
            targetConn = DriverManager.getConnection(targetDbUrl, "usuarioSQL", "1234");

            // Crear los statements
            sourceStmt = sourceConn.createStatement();
            targetStmt = targetConn.createStatement();

            // Consulta de los datos de la base de datos de referencia
            String query = "SELECT * FROM clientes"; 
            ResultSet rs = sourceStmt.executeQuery(query);

            // Limpiar la tabla de destino
            targetStmt.executeUpdate("DELETE FROM clientes"); 

            // Insertar los datos de la tabla de referencia=tabla destino
            while (rs.next()) {
                String column1 = rs.getString("Nombre"); 
                String column2 = rs.getString("Cuenta");
                String column3 = rs.getString("Tipo_cuenta"); 
                String column4 = rs.getString("Documento");
                String column5 = rs.getString("Estado_civil"); 
                String column6 = rs.getString("Direccion");
                String column7 = rs.getString("Correo");

                String insertQuery = "INSERT INTO clientes (Nombre, Cuenta,Tipo_cuenta,Documento,Estado_civil,Direccion,Correo) VALUES ('" + column1 + "', '" + column2 + "', '" + column3 +"', '" + column4 +"', '" + column5 +"', '" + column6 +"', '" + column7 + "')";
                targetStmt.executeUpdate(insertQuery);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al sincronizar las tablas: " + e.getMessage());
        } finally {
            // Cerrar conexiones
            if (sourceStmt != null) {
                sourceStmt.close();
            }
            if (targetStmt != null) {
                targetStmt.close();
            }
            if (sourceConn != null) {
                sourceConn.close();
            }
            if (targetConn != null) {
                targetConn.close();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SQLServerQueryApp(true).setVisible(true);  // Interfaz para "clientes"
                //new SQLServerQueryApp(false).setVisible(true); // Interfaz para "clientescontingencia"
            }
        });
    }
}
