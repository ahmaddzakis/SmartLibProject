/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.dao;

/**
 *
 * @author Ariska Putri
 */

import id.ac.unpas.config.Database;
import id.ac.unpas.model.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection conn = Database.getConnection();

    // 1. FITUR LOGIN (Paling Penting)
    // Mengembalikan objek Admin jika username/pass benar, null jika salah
    public Admin login(String username, String password) {
        Admin admin = null;
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // Note: Di project nyata, password harus di-hash (MD5/Bcrypt)
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                admin = new Admin();
                admin.setIdAdmin(rs.getInt("id_admin"));
                admin.setNama(rs.getString("nama"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    // 2. CREATE (Tambah Admin Baru)
    public void insert(Admin a) {
        String sql = "INSERT INTO admin (nama, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNama());
            ps.setString(2, a.getUsername());
            ps.setString(3, a.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. READ (Lihat Daftar Admin)
    public List<Admin> getAll() {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Admin a = new Admin();
                a.setIdAdmin(rs.getInt("id_admin"));
                a.setNama(rs.getString("nama"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. UPDATE (Ganti Password/Nama)
    public void update(Admin a) {
        String sql = "UPDATE admin SET nama=?, username=?, password=? WHERE id_admin=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNama());
            ps.setString(2, a.getUsername());
            ps.setString(3, a.getPassword());
            ps.setInt(4, a.getIdAdmin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. DELETE (Hapus Admin)
    public void delete(int id) {
        String sql = "DELETE FROM admin WHERE id_admin=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
