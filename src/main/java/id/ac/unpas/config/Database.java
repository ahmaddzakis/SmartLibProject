/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Sesuaikan dengan konfigurasi MySQL Anda
    private static final String DB_URL = "jdbc:mysql://localhost:3306/laundry_db";
    private static final String USER = "root";
    private static final String PASS = "";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            // PERBAIKAN DI SINI:
            // Cek apakah connection null ATAU connection sudah tertutup (isClosed)
            if (connection == null || connection.isClosed()) {

                // Register JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Koneksi Database Berhasil (Reconnected)!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Koneksi Database Gagal: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
