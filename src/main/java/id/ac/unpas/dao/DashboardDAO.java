package id.ac.unpas.dao;

import id.ac.unpas.config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {
    // 1. Hitung Pemasukan Hari Ini
    public int getPemasukanHarian() {
        int total = 0;

        // REVISI LOGIKA:
        // Menambahkan: AND (status = 'selesai' OR status = 'diambil')
        // Artinya: Hanya hitung total_harga jika cucian sudah beres/lunas.
        String sql = "SELECT COALESCE(SUM(total_harga), 0) FROM transaksi " +
                "WHERE DATE(tgl_masuk) = CURDATE() " +
                "AND (status = 'selesai' OR status = 'diambil')";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // ... (Method getCountStatus & getTotalPelanggan TETAP SAMA seperti sebelumnya) ...
    public int getCountStatus(String status) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM transaksi WHERE status = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) count = rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    public int getTotalPelanggan() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM pelanggan";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    // 4. Ambil SEMUA Transaksi (LIMIT DIHAPUS)
    public List<String[]> getAllTransactions() {
        List<String[]> list = new ArrayList<>();
        // LIMIT 5 Dihapus agar semua data muncul
        String sql = "SELECT t.kode_nota, p.nama, t.total_harga, t.status " +
                "FROM transaksi t JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan " +
                "ORDER BY t.id_transaksi DESC";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("kode_nota"),
                        rs.getString("nama"),
                        "Rp " + rs.getInt("total_harga"),
                        rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
