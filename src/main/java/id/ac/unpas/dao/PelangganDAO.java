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
import id.ac.unpas.model.Pelanggan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganDAO {
    // HAPUS VARIABEL GLOBAL: private Connection conn ...

    // READ (Get All)
    public List<Pelanggan> getAll() {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan ORDER BY nama ASC";

        // Panggil Connection di dalam method
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pelanggan p = new Pelanggan();
                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setNama(rs.getString("nama"));
                p.setNoHp(rs.getString("no_hp"));
                p.setAlamat(rs.getString("alamat"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // CREATE
    public void insert(Pelanggan p) {
        String sql = "INSERT INTO pelanggan (nama, no_hp, alamat) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoHp());
            ps.setString(3, p.getAlamat());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void update(Pelanggan p) {
        String sql = "UPDATE pelanggan SET nama=?, no_hp=?, alamat=? WHERE id_pelanggan=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoHp());
            ps.setString(3, p.getAlamat());
            ps.setInt(4, p.getIdPelanggan());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SEARCH (Termasuk Alamat yang tadi direquest)
    public List<Pelanggan> search(String keyword) {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan WHERE nama LIKE ? OR no_hp LIKE ? OR alamat LIKE ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchKey = "%" + keyword + "%";
            ps.setString(1, searchKey);
            ps.setString(2, searchKey);
            ps.setString(3, searchKey);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pelanggan p = new Pelanggan();
                    p.setIdPelanggan(rs.getInt("id_pelanggan"));
                    p.setNama(rs.getString("nama"));
                    p.setNoHp(rs.getString("no_hp"));
                    p.setAlamat(rs.getString("alamat"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
