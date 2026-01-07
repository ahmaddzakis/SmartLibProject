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
    private Connection conn = Database.getConnection();

    // 1. CREATE (Input Pembayaran)
    public void insert(Pembayaran p) {
        // Kita set tgl_bayar otomatis ke Waktu Sekarang (CURRENT_TIMESTAMP)
        String sql = "INSERT INTO pembayaran (id_transaksi, metode, jumlah_bayar, status_bayar, tgl_bayar) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdTransaksi());
            ps.setString(2, p.getMetode());
            ps.setInt(3, p.getJumlahBayar());
            ps.setString(4, p.getStatusBayar()); // Biasanya langsung 'lunas' kalau insert
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. READ (Ambil Semua Data + JOIN Kode Nota)
    public List<Pembayaran> getAll() {
        List<Pembayaran> list = new ArrayList<>();
        // Join supaya kita bisa tahu pembayaran ini milik Nota yang mana
        String sql = "SELECT p.*, t.kode_nota " +
                     "FROM pembayaran p " +
                     "JOIN transaksi t ON p.id_transaksi = t.id_transaksi " +
                     "ORDER BY p.tgl_bayar DESC";
                     
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setIdPembayaran(rs.getInt("id_pembayaran"));
                p.setIdTransaksi(rs.getInt("id_transaksi"));
                p.setMetode(rs.getString("metode"));
                p.setJumlahBayar(rs.getInt("jumlah_bayar"));
                p.setStatusBayar(rs.getString("status_bayar"));
                p.setTglBayar(rs.getTimestamp("tgl_bayar"));
                
                // Set Helper Field (Dari tabel transaksi)
                p.setKodeNota(rs.getString("kode_nota"));
                
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. UPDATE (Edit Pembayaran - Misal salah input nominal/metode)
    public void update(Pembayaran p) {
        String sql = "UPDATE pembayaran SET metode=?, jumlah_bayar=?, status_bayar=? WHERE id_pembayaran=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getMetode());
            ps.setInt(2, p.getJumlahBayar());
            ps.setString(3, p.getStatusBayar());
            ps.setInt(4, p.getIdPembayaran());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. DELETE (Hapus History Pembayaran)
    public void delete(int id) {
        String sql = "DELETE FROM pembayaran WHERE id_pembayaran=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. SEARCH (Cari berdasarkan Kode Nota Transaksi)
    public List<Pembayaran> search(String keyword) {
        List<Pembayaran> list = new ArrayList<>();
        // User mencari "TRX-001", sistem mencocokkan lewat tabel transaksi
        String sql = "SELECT p.*, t.kode_nota " +
                     "FROM pembayaran p " +
                     "JOIN transaksi t ON p.id_transaksi = t.id_transaksi " +
                     "WHERE t.kode_nota LIKE ? OR p.metode LIKE ?";
                     
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%"); // Cari Kode Nota
            ps.setString(2, "%" + keyword + "%"); // Atau cari metode (e.g. 'transfer')
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setIdPembayaran(rs.getInt("id_pembayaran"));
                p.setIdTransaksi(rs.getInt("id_transaksi"));
                p.setMetode(rs.getString("metode"));
                p.setJumlahBayar(rs.getInt("jumlah_bayar"));
                p.setStatusBayar(rs.getString("status_bayar"));
                p.setTglBayar(rs.getTimestamp("tgl_bayar"));
                p.setKodeNota(rs.getString("kode_nota"));
                
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // EXTRA: Cek apakah transaksi tertentu sudah lunas?
    // Berguna untuk validasi sebelum pengambilan barang
    public boolean isLunas(int idTransaksi) {
        String sql = "SELECT status_bayar FROM pembayaran WHERE id_transaksi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "lunas".equalsIgnoreCase(rs.getString("status_bayar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Belum ada data pembayaran
    }
}
