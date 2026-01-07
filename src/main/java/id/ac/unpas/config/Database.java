/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.config;

/**
 *
 * @author Fikri Lazuardi
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection mysqlconfig;
    
    public static Connection getConnection() {
        try {
            if (mysqlconfig == null || mysqlconfig.isClosed()) {
                // Sesuaikan nama database, user, dan password di sini
                String url = "jdbc:mysql://localhost:3306/laundry_db"; // ganti 'laundry_db' dengan nama DB mu
                String user = "root"; // default xampp
                String pass = "";     // default xampp kosong
                
                // Register Driver
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                mysqlconfig = DriverManager.getConnection(url, user, pass);
                // System.out.println("Koneksi Berhasil!"); // Nyalakan untuk testing
            }
        } catch (SQLException e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());
        }
        return mysqlconfig;
    }
}
