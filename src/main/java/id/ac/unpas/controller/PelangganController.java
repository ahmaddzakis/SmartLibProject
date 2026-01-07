/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.controller;

/**
 *
 * @author Mustika Weni
 */

import id.ac.unpas.dao.PelangganDAO;
import id.ac.unpas.model.Pelanggan;
import id.ac.unpas.view.PelangganView;
import java.util.List;
import javax.swing.JOptionPane;

public class PelangganController {
    private PelangganView view;
    private PelangganDAO dao;

    public PelangganController(PelangganView view) {
        this.view = view;
        this.dao = new PelangganDAO();
    }

    // 1. Tampil Data ke Tabel
    public void isiTabel() {
        List<Pelanggan> list = dao.getAll();
        view.getTableModel().setRowCount(0); // Reset tabel
        for (Pelanggan p : list) {
            view.getTableModel().addRow(new Object[]{
                p.getIdPelanggan(),
                p.getNama(),
                p.getNoHp(),
                p.getAlamat()
            });
        }
    }

    // 2. Simpan Data (CREATE)
    // 2. Simpan Data (CREATE)
    // 1. UPDATE METHOD insertData()
    public void insertData() {
        String nama = view.getTxtNama().getText().trim();
        String hp = view.getTxtHp().getText().trim();
        String alamat = view.getTxtAlamat().getText().trim(); // Ambil teks alamat

        // --- VALIDASI WAJIB DIISI (Termasuk Alamat) ---
        if (nama.isEmpty() || hp.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama, No HP, dan Alamat wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // ----------------------------------------------

        // Validasi Panjang Nama
        if (nama.length() > 100) {
            JOptionPane.showMessageDialog(view, "Nama terlalu panjang! Maksimal 100 karakter.", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi Nama (Tidak boleh angka)
        if (nama.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(view, "Nama Pelanggan tidak boleh mengandung angka!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi No HP (Hanya angka)
        if (!hp.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(view, "No HP hanya boleh berisi angka!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validasi Panjang HP
        if (hp.length() < 10 || hp.length() > 13) {
             JOptionPane.showMessageDialog(view, "Panjang No HP tidak wajar (10-13 digit)!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
             return;
        }

        // Simpan Data
        Pelanggan p = new Pelanggan();
        p.setNama(nama);
        p.setNoHp(hp);
        p.setAlamat(alamat);

        dao.insert(p);
        JOptionPane.showMessageDialog(view, "Data Berhasil Disimpan");
        resetForm();
        isiTabel();
    }

    // 2. UPDATE METHOD updateData()
    public void updateData() {
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data dari tabel dulu!");
            return;
        }
        
        String nama = view.getTxtNama().getText().trim();
        String hp = view.getTxtHp().getText().trim();
        String alamat = view.getTxtAlamat().getText().trim(); // Ambil teks alamat
        
        // --- VALIDASI WAJIB DIISI (Termasuk Alamat) ---
        if (nama.isEmpty() || hp.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama, No HP, dan Alamat wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // ----------------------------------------------

        // Validasi lainnya sama seperti insert...
        if (nama.length() > 100) {
            JOptionPane.showMessageDialog(view, "Nama terlalu panjang! Maksimal 100 karakter.");
            return;
        }
        
        if (nama.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(view, "Nama Pelanggan tidak boleh mengandung angka!");
            return;
        }

        if (!hp.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(view, "No HP hanya boleh berisi angka!");
            return;
        }

        if (hp.length() < 10 || hp.length() > 13) {
             JOptionPane.showMessageDialog(view, "Panjang No HP tidak wajar (10-13 digit)!");
             return;
        }

        // Update Data
        Pelanggan p = new Pelanggan();
        p.setIdPelanggan(Integer.parseInt(view.getTxtId().getText()));
        p.setNama(nama);
        p.setNoHp(hp);
        p.setAlamat(alamat);

        dao.update(p);
        JOptionPane.showMessageDialog(view, "Data Berhasil Diubah");
        resetForm();
        isiTabel();
    }

    // 4. Hapus Data (DELETE)
    public void deleteData() {
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data dari tabel dulu!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(view.getTxtId().getText());
            dao.delete(id);
            resetForm();
            isiTabel();
        }
    }

    // 5. Cari Data (SEARCH)
    public void cariData() {
        String keyword = view.getTxtCari().getText();
        List<Pelanggan> list = dao.search(keyword);
        
        view.getTableModel().setRowCount(0); // Bersihkan tabel
        for (Pelanggan p : list) {
            view.getTableModel().addRow(new Object[]{
                p.getIdPelanggan(),
                p.getNama(),
                p.getNoHp(),
                p.getAlamat()
            });
        }
    }

    // Helper: Reset Form
    public void resetForm() {
        view.getTxtId().setText("");
        view.getTxtNama().setText("");
        view.getTxtHp().setText("");
        view.getTxtAlamat().setText("");
        view.getTxtCari().setText("");
        isiTabel(); // Kembalikan tabel ke kondisi awal (jika habis search)
    }

    // Helper: Saat baris tabel diklik
    public void pilihBaris() {
        int row = view.getTabelPelanggan().getSelectedRow();
        if (row != -1) {
            // Ambil data dari tabel, masukkan ke form
            view.getTxtId().setText(view.getTabelPelanggan().getValueAt(row, 0).toString());
            view.getTxtNama().setText(view.getTabelPelanggan().getValueAt(row, 1).toString());
            view.getTxtHp().setText(view.getTabelPelanggan().getValueAt(row, 2).toString());
            view.getTxtAlamat().setText(view.getTabelPelanggan().getValueAt(row, 3).toString());
        }
    }
}
