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
    // 1. INSERT (Simpan Data Baru)
    public void insert(Layanan l) {
        // PERBAIKAN: Ganti 'harga' jadi 'harga_per_kg'
        String sql = "INSERT INTO layanan (nama_layanan, harga_per_kg, estimasi_hari) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, l.getNamaLayanan());
            ps.setInt(2, l.getHargaPerKg());
            ps.setInt(3, l.getEstimasiHari());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. READ (Ambil Semua Data)
    public List<Layanan> getAll() {
        List<Layanan> list = new ArrayList<>();
        String sql = "SELECT * FROM layanan ORDER BY nama_layanan ASC";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Layanan l = new Layanan();
                l.setIdLayanan(rs.getInt("id_layanan"));
                l.setNamaLayanan(rs.getString("nama_layanan"));

                // PERBAIKAN: Ambil dari kolom 'harga_per_kg'
                try {
                    l.setHargaPerKg(rs.getInt("harga_per_kg"));
                } catch (SQLException ex) {
                    // Jaga-jaga kalau namanya beda lagi
                    l.setHargaPerKg(rs.getInt("harga"));
                }

                l.setEstimasiHari(rs.getInt("estimasi_hari"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. UPDATE (Ubah Data)
    public void update(Layanan l) {
        // PERBAIKAN: Ganti 'harga' jadi 'harga_per_kg'
        String sql = "UPDATE layanan SET nama_layanan=?, harga_per_kg=?, estimasi_hari=? WHERE id_layanan=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, l.getNamaLayanan());
            ps.setInt(2, l.getHargaPerKg());
            ps.setInt(3, l.getEstimasiHari());
            ps.setInt(4, l.getIdLayanan());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. DELETE (Hapus Data)
    public void delete(int id) {
        String sql = "DELETE FROM layanan WHERE id_layanan=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. SEARCH (Cari Data)
    public List<Layanan> search(String keyword) {
        List<Layanan> list = new ArrayList<>();
        String sql = "SELECT * FROM layanan WHERE nama_layanan LIKE ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Layanan l = new Layanan();
                    l.setIdLayanan(rs.getInt("id_layanan"));
                    l.setNamaLayanan(rs.getString("nama_layanan"));

                    try { l.setHargaPerKg(rs.getInt("harga_per_kg")); }
                    catch (SQLException ex) { l.setHargaPerKg(rs.getInt("harga")); }

                    l.setEstimasiHari(rs.getInt("estimasi_hari"));
                    list.add(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
