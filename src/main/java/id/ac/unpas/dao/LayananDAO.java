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
import id.ac.unpas.model.Layanan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LayananDAO {
    private Connection conn = Database.getConnection();

    // CREATE
    public void insert(Layanan l) {
        String sql = "INSERT INTO layanan (nama_layanan, harga_per_kg, estimasi_hari) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getNamaLayanan());
            ps.setInt(2, l.getHargaPerKg());
            ps.setInt(3, l.getEstimasiHari());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public List<Layanan> getAll() {
        List<Layanan> list = new ArrayList<>();
        String sql = "SELECT * FROM layanan";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Layanan l = new Layanan();
                l.setIdLayanan(rs.getInt("id_layanan"));
                l.setNamaLayanan(rs.getString("nama_layanan"));
                l.setHargaPerKg(rs.getInt("harga_per_kg"));
                l.setEstimasiHari(rs.getInt("estimasi_hari"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public void update(Layanan l) {
        String sql = "UPDATE layanan SET nama_layanan=?, harga_per_kg=?, estimasi_hari=? WHERE id_layanan=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getNamaLayanan());
            ps.setInt(2, l.getHargaPerKg());
            ps.setInt(3, l.getEstimasiHari());
            ps.setInt(4, l.getIdLayanan());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM layanan WHERE id_layanan=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SEARCH
    public List<Layanan> search(String keyword) {
        List<Layanan> list = new ArrayList<>();
        String sql = "SELECT * FROM layanan WHERE nama_layanan LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Layanan l = new Layanan();
                l.setIdLayanan(rs.getInt("id_layanan"));
                l.setNamaLayanan(rs.getString("nama_layanan"));
                l.setHargaPerKg(rs.getInt("harga_per_kg"));
                l.setEstimasiHari(rs.getInt("estimasi_hari"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
