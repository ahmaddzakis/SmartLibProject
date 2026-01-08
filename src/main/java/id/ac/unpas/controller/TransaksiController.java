/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.controller;

/**
 *
 * @author Mustika Weni
 */

import id.ac.unpas.dao.LayananDAO;
import id.ac.unpas.dao.PelangganDAO;
import id.ac.unpas.dao.TransaksiDAO;
import id.ac.unpas.model.Layanan;
import id.ac.unpas.model.Pelanggan;
import id.ac.unpas.model.Transaksi;
import id.ac.unpas.view.TransaksiView;

import javax.swing.*;
import java.util.Calendar;
import java.util.List;

public class TransaksiController {
    private TransaksiView view;
    private TransaksiDAO transDAO;
    private PelangganDAO pelDAO;
    private LayananDAO layDAO;

    public TransaksiController(TransaksiView view) {
        this.view = view;
        this.transDAO = new TransaksiDAO();
        this.pelDAO = new PelangganDAO();
        this.layDAO = new LayananDAO();
        
        resetForm();
    }

    // ... method isiComboData() dan isiTabel() tetap sama ...
    public void isiComboData() {
        List<Pelanggan> listPel = pelDAO.getAll();
        view.getComboPelanggan().removeAllItems();
        for (Pelanggan p : listPel) view.getComboPelanggan().addItem(p);

        List<Layanan> listLay = layDAO.getAll();
        view.getComboLayanan().removeAllItems();
        for (Layanan l : listLay) view.getComboLayanan().addItem(l);
    }

    public void isiTabel() {
        List<Transaksi> list = transDAO.getAll();
        view.getTableModel().setRowCount(0);
        for (Transaksi t : list) {
            view.getTableModel().addRow(new Object[]{
                t.getIdTransaksi(), t.getKodeNota(), t.getNamaPelanggan(),
                t.getNamaLayanan(), t.getBerat(), t.getTotalHarga(),
                t.getStatus(), t.getTglMasuk()
            });
        }
    }

    // -------------------------------------------------------------
    // MODIFIKASI 1: Validasi di Method Hitung Harga
    // -------------------------------------------------------------
    public void hitungHarga() {
        try {
            String beratStr = view.getTxtBerat().getText();
            
            if (beratStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Masukkan berat dulu!");
                return;
            }

            double berat = Double.parseDouble(beratStr);

            // --- VALIDASI BERAT MAX 20 KG ---
            if (berat > 20) {
                JOptionPane.showMessageDialog(view, "Berat melebihi kapasitas (Max 20 Kg)!", "Warning", JOptionPane.WARNING_MESSAGE);
                view.getTxtBerat().setText("20"); // Opsional: Auto set ke max
                return;
            }
            
            // Validasi Berat Min 0
            if (berat <= 0) {
                JOptionPane.showMessageDialog(view, "Berat harus lebih dari 0!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // -------------------------------

            Layanan layananTerpilih = (Layanan) view.getComboLayanan().getSelectedItem();
            if (layananTerpilih != null) {
                int total = (int) (berat * layananTerpilih.getHargaPerKg());
                view.getTxtTotal().setText(String.valueOf(total));
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Berat harus berupa ANGKA (Gunakan titik untuk desimal)!");
        }
    }

    // -------------------------------------------------------------
    // MODIFIKASI 2: Validasi di Method Simpan (Insert)
    // -------------------------------------------------------------
    public void insertData() {
        // Cek apakah total harga sudah dihitung
        if (view.getTxtTotal().getText().isEmpty()) {
            hitungHarga(); // Paksakan hitung dulu
            
            // Jika setelah dipaksa hitung masih kosong (karena error validasi), stop.
            if (view.getTxtTotal().getText().isEmpty()) return; 
        }

        try {
            // Kita ambil lagi nilai berat untuk memastikan user tidak curang
            // mengubah angka setelah klik hitung
            double berat = Double.parseDouble(view.getTxtBerat().getText());

            // --- VALIDASI ULANG SEBELUM MASUK DATABASE ---
            if (berat > 20) {
                JOptionPane.showMessageDialog(view, "Gagal Simpan: Berat melebihi 20 Kg!");
                return;
            }
            if (berat <= 0) {
                JOptionPane.showMessageDialog(view, "Gagal Simpan: Berat tidak valid!");
                return;
            }
            // ---------------------------------------------

            Transaksi t = new Transaksi();
            t.setKodeNota(view.getTxtNota().getText());
            
            Pelanggan p = (Pelanggan) view.getComboPelanggan().getSelectedItem();
            Layanan l = (Layanan) view.getComboLayanan().getSelectedItem();
            
            t.setIdPelanggan(p.getIdPelanggan());
            t.setIdLayanan(l.getIdLayanan());
            t.setIdAdmin(1); // Sesuaikan dengan Session user nanti
            
            t.setBerat(berat);
            t.setTotalHarga(Integer.parseInt(view.getTxtTotal().getText()));
            
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, l.getEstimasiHari());
            t.setTglSelesai(cal.getTime());

            transDAO.insert(t);
            JOptionPane.showMessageDialog(view, "Transaksi Berhasil!");
            resetForm();
            isiTabel();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Format angka salah!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    // ... Method updateStatus, deleteData, cariData, resetForm, pilihBaris TETAP SAMA ...

    public void updateData() {
        // 1. Validasi ID (Harus pilih baris dulu)
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data dari tabel terlebih dahulu!");
            return;
        }

        // 2. Ambil Data dari Form
        Pelanggan pelanggan = (Pelanggan) view.getComboPelanggan().getSelectedItem();
        Layanan layanan = (Layanan) view.getComboLayanan().getSelectedItem();
        String status = (String) view.getComboStatus().getSelectedItem();

        // Ambil teks berat dan ganti koma jadi titik (jika ada)
        String beratStr = view.getTxtBerat().getText().replace(",", ".");

        // 3. Validasi Input Kosong / Data Null
        if (pelanggan == null || layanan == null || beratStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        // --- TAMBAHKAN TRY-CATCH UNTUK MENANGKAP ERROR ANGKA/DATABASE ---
        try {
            // 4. Hitung Ulang Total Harga
            double berat = Double.parseDouble(beratStr);

            // Cek jika berat negatif
            if (berat <= 0) {
                JOptionPane.showMessageDialog(view, "Berat harus lebih dari 0!");
                return;
            }

            int totalHarga = (int) (berat * layanan.getHargaPerKg());

            // Update tampilan Total Harga
            view.getTxtTotal().setText(String.valueOf(totalHarga));

            // 5. Bungkus ke Objek Transaksi
            Transaksi t = new Transaksi();
            t.setIdTransaksi(Integer.parseInt(view.getTxtId().getText()));

            t.setIdPelanggan(pelanggan.getIdPelanggan());
            t.setIdLayanan(layanan.getIdLayanan());

            t.setBerat(berat);
            t.setTotalHarga(totalHarga);
            t.setStatus(status);

            // 6. Kirim ke DAO
            transDAO.updateStatus(t);

            JOptionPane.showMessageDialog(view, "Data Transaksi Berhasil Diubah Lengkap!");
            resetForm();
            isiTabel();

        } catch (NumberFormatException e) {
            // Error ini muncul jika "Berat" isinya huruf atau format salah
            JOptionPane.showMessageDialog(view, "Kolom Berat harus berupa ANGKA!\n(Gunakan titik untuk desimal)");
            e.printStackTrace();
        } catch (Exception e) {
            // Error lain (Database, Koneksi, dll)
            JOptionPane.showMessageDialog(view, "Terjadi Error: " + e.getMessage());
            e.printStackTrace(); // Lihat detail error merah di bawah (Console)
        }
    }

    public void deleteData() {
         // Cek apakah field ID kosong (artinya user belum klik tabel)
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data terlebih dahulu!", "Peringatan", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (view.getTxtId().getText().isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(view, "Hapus transaksi ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            transDAO.delete(Integer.parseInt(view.getTxtId().getText()));
            isiTabel();
            resetForm();
        }
    }
    
    public void cariData() {
        String keyword = view.getTxtCari().getText();
        List<Transaksi> list = transDAO.search(keyword);
        view.getTableModel().setRowCount(0);
        for (Transaksi t : list) {
            view.getTableModel().addRow(new Object[]{
                t.getIdTransaksi(), t.getKodeNota(), t.getNamaPelanggan(), t.getNamaLayanan(), t.getBerat(), t.getTotalHarga(), t.getStatus(), t.getTglMasuk()
            });
        }
    }

    public void resetForm() {
       view.getTxtId().setText("");
        generateKodeNotaOtomatis(); // Panggil generator urut
        view.getTxtBerat().setText("");
        view.getTxtTotal().setText("");
        view.getComboStatus().setEnabled(false);
        view.getComboPelanggan().setEnabled(true);
        isiTabel();
    }
    
    // Method untuk bikin kode TRX-001, TRX-002 secara otomatis
    private void generateKodeNotaOtomatis() {
        String lastCode = transDAO.getLastKodeNota();
        String newCode = "TRX-001"; // Default kalau database kosong

        if (lastCode != null) {
            try {
                // lastCode formatnya "TRX-001"
                // Kita ambil angkanya saja mulai dari indeks ke-4 ("001")
                String angkaStr = lastCode.substring(4); 
                int angka = Integer.parseInt(angkaStr);
                
                angka++; // Tambah 1
                
                // Format balik ke string dengan padding nol (001, 002, ...)
                newCode = String.format("TRX-%03d", angka);
                
            } catch (Exception e) {
                // Kalau format kode di database rusak/beda, reset ke default
                System.out.println("Gagal generate kode otomatis: " + e.getMessage());
            }
        }
        
        // Set ke Text Field
        view.getTxtNota().setText(newCode);
    }

    public void pilihBaris() {
        int row = view.getTabelTransaksi().getSelectedRow();
        if (row != -1) {
            // 1. Ambil data mentah dari tabel (sebagai String)
            String id = view.getTabelTransaksi().getValueAt(row, 0).toString();
            String nota = view.getTabelTransaksi().getValueAt(row, 1).toString();
            String namaPelanggan = view.getTabelTransaksi().getValueAt(row, 2).toString();
            String namaLayanan = view.getTabelTransaksi().getValueAt(row, 3).toString();
            String berat = view.getTabelTransaksi().getValueAt(row, 4).toString();
            String total = view.getTabelTransaksi().getValueAt(row, 5).toString();
            String status = view.getTabelTransaksi().getValueAt(row, 6).toString();

            // 2. Masukkan ke Text Field sederhana
            view.getTxtId().setText(id);
            view.getTxtNota().setText(nota);
            view.getTxtBerat().setText(berat);
            view.getTxtTotal().setText(total);
            
            // 3. Set Status (Langsung set karena isinya String sama)
            view.getComboStatus().setSelectedItem(status);
            view.getComboStatus().setEnabled(true); // Aktifkan edit status

            // 4. Set ComboBox Pelanggan (Mencocokkan Nama)
            // Kita loop semua isi dropdown, kalau namanya sama, kita pilih.
            for (int i = 0; i < view.getComboPelanggan().getItemCount(); i++) {
                Pelanggan p = view.getComboPelanggan().getItemAt(i);
                if (p.getNama().equals(namaPelanggan)) {
                    view.getComboPelanggan().setSelectedIndex(i);
                    break;
                }
            }

            // 5. Set ComboBox Layanan (Mencocokkan Nama Layanan)
            for (int i = 0; i < view.getComboLayanan().getItemCount(); i++) {
                Layanan l = view.getComboLayanan().getItemAt(i);
                if (l.getNamaLayanan().equals(namaLayanan)) {
                    view.getComboLayanan().setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
