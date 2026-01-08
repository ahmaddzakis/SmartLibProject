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
import id.ac.unpas.model.Pembayaran;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {
    // 1. INSERT (Bayar)
    public void insert(Pembayaran p) {
        // PERBAIKAN: Menggunakan 'metode' (bukan metode_pembayaran)
        String sql = "INSERT INTO pembayaran (id_transaksi, tgl_bayar, jumlah_bayar, metode, status_bayar) VALUES (?, NOW(), ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdTransaksi());
            ps.setInt(2, p.getJumlahBayar());
            ps.setString(3, p.getMetode());
            ps.setString(4, p.getStatusBayar());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. GET ALL (Riwayat)
    public List<Pembayaran> getAll() {
        List<Pembayaran> list = new ArrayList<>();
        // Query Join tetap sama
        String sql = "SELECT p.*, t.kode_nota " +
                "FROM pembayaran p " +
                "JOIN transaksi t ON p.id_transaksi = t.id_transaksi " +
                "ORDER BY p.tgl_bayar DESC";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setIdPembayaran(rs.getInt("id_pembayaran"));
                p.setIdTransaksi(rs.getInt("id_transaksi"));
                p.setKodeNota(rs.getString("kode_nota"));
                p.setTglBayar(rs.getTimestamp("tgl_bayar"));
                p.setJumlahBayar(rs.getInt("jumlah_bayar"));

                // PERBAIKAN DI SINI:
                // Mengambil kolom 'metode' dari database
                try {
                    p.setMetode(rs.getString("metode"));
                } catch (SQLException ex) {
                    // Jaga-jaga kalau ternyata namanya 'metode_pembayaran' (Fallback)
                    p.setMetode(rs.getString("metode_pembayaran"));
                }

                p.setStatusBayar(rs.getString("status_bayar"));

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. CEK LUNAS
    public boolean isLunas(int idTransaksi) {
        boolean lunas = false;
        String sql = "SELECT COUNT(*) FROM pembayaran WHERE id_transaksi = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTransaksi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (rs.getInt(1) > 0) {
                        lunas = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lunas;
    }
}
