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
import id.ac.unpas.model.Transaksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {
    // --- HAPUS VARIABEL GLOBAL "conn" DI SINI ---
    // Kita panggil koneksi di dalam masing-masing method biar selalu fresh.

    // 1. CREATE (INSERT)
    public void insert(Transaksi t) {
        String sql = "INSERT INTO transaksi (kode_nota, id_pelanggan, id_layanan, id_admin, berat, total_harga, status, tgl_selesai) VALUES (?, ?, ?, ?, ?, ?, 'Proses', ?)";

        // Panggil Database.getConnection() DI DALAM try
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getKodeNota());
            ps.setInt(2, t.getIdPelanggan());
            ps.setInt(3, t.getIdLayanan());
            ps.setInt(4, t.getIdAdmin());
            ps.setDouble(5, t.getBerat());
            ps.setInt(6, t.getTotalHarga());
            ps.setTimestamp(7, new Timestamp(t.getTglSelesai().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. READ (GET ALL)
    public List<Transaksi> getAll() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, p.nama AS nama_pelanggan, l.nama_layanan " +
                "FROM transaksi t " +
                "JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan " +
                "JOIN layanan l ON t.id_layanan = l.id_layanan " +
                "ORDER BY t.tgl_masuk DESC";

        // Panggil Database.getConnection() DI SINI JUGA
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setIdTransaksi(rs.getInt("id_transaksi"));
                t.setKodeNota(rs.getString("kode_nota"));
                t.setIdPelanggan(rs.getInt("id_pelanggan"));
                t.setIdLayanan(rs.getInt("id_layanan"));
                t.setBerat(rs.getDouble("berat"));
                t.setTotalHarga(rs.getInt("total_harga"));
                t.setStatus(rs.getString("status"));
                t.setTglMasuk(rs.getTimestamp("tgl_masuk"));
                t.setTglSelesai(rs.getTimestamp("tgl_selesai"));

                t.setNamaPelanggan(rs.getString("nama_pelanggan"));
                t.setNamaLayanan(rs.getString("nama_layanan"));

                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. UPDATE DATA LENGKAP
    public void updateStatus(Transaksi t) {
        String sql = "UPDATE transaksi SET id_pelanggan=?, id_layanan=?, berat=?, total_harga=?, status=? WHERE id_transaksi=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getIdPelanggan());
            ps.setInt(2, t.getIdLayanan());
            ps.setDouble(3, t.getBerat());
            ps.setInt(4, t.getTotalHarga());
            ps.setString(5, t.getStatus());
            ps.setInt(6, t.getIdTransaksi());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. UPDATE STATUS SAJA (Dipakai saat Pembayaran)
    public void updateStatusOnly(int idTransaksi, String statusBaru) {
        String sql = "UPDATE transaksi SET status = ? WHERE id_transaksi = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statusBaru);
            ps.setInt(2, idTransaksi);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. DELETE
    public void delete(int id) {
        String sql = "DELETE FROM transaksi WHERE id_transaksi=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 6. SEARCH
    public List<Transaksi> search(String keyword) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, p.nama AS nama_pelanggan, l.nama_layanan " +
                "FROM transaksi t " +
                "JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan " +
                "JOIN layanan l ON t.id_layanan = l.id_layanan " +
                "WHERE t.kode_nota LIKE ? OR p.nama LIKE ? " +
                "ORDER BY t.tgl_masuk DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaksi t = new Transaksi();
                    t.setIdTransaksi(rs.getInt("id_transaksi"));
                    t.setKodeNota(rs.getString("kode_nota"));
                    t.setBerat(rs.getDouble("berat"));
                    t.setTotalHarga(rs.getInt("total_harga"));
                    t.setStatus(rs.getString("status"));
                    t.setTglMasuk(rs.getTimestamp("tgl_masuk"));

                    t.setNamaPelanggan(rs.getString("nama_pelanggan"));
                    t.setNamaLayanan(rs.getString("nama_layanan"));

                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 7. GET BY KODE (Untuk Pembayaran)
    public Transaksi getByKode(String kodeNota) {
        String sql = "SELECT t.*, p.nama AS nama_pelanggan " +
                "FROM transaksi t " +
                "JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan " +
                "WHERE t.kode_nota = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeNota);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transaksi t = new Transaksi();
                    t.setIdTransaksi(rs.getInt("id_transaksi"));
                    t.setTotalHarga(rs.getInt("total_harga"));
                    t.setNamaPelanggan(rs.getString("nama_pelanggan"));
                    return t;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // 8. GET LAST KODE (Untuk Auto Number) - Ini yang tadi error di log kamu!
    public String getLastKodeNota() {
        String sql = "SELECT kode_nota FROM transaksi ORDER BY id_transaksi DESC LIMIT 1";

        // Perhatikan ini: Pakai try-with-resources connection
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getString("kode_nota");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
