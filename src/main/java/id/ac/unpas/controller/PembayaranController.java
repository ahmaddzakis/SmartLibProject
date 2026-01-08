/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.controller;

/**
 *
 * @author Mustika Weni
 */

import id.ac.unpas.dao.PembayaranDAO;
import id.ac.unpas.dao.TransaksiDAO;
import id.ac.unpas.model.Pembayaran;
import id.ac.unpas.model.Transaksi;
import id.ac.unpas.view.PembayaranView;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.List;

public class PembayaranController {
   private PembayaranView view;
    private PembayaranDAO bayarDAO;
    private TransaksiDAO transDAO;

    public PembayaranController(PembayaranView view) {
        this.view = view;
        this.bayarDAO = new PembayaranDAO();
        this.transDAO = new TransaksiDAO();
    }

    // --- LOGIKA 1: CEK NOTA ---
    public void cekNota() {
        String kodeInput = view.getTxtCariNota().getText();
        
        if (kodeInput.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Masukkan Kode Nota dulu!");
            return;
        }

        // 1. Cari Transaksi ke Database
        Transaksi t = transDAO.getByKode(kodeInput);

        // 2. Validasi: Apakah Ada?
        if (t == null) {
            JOptionPane.showMessageDialog(view, "Nota tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            resetForm();
            return;
        }

        // 3. Validasi: Apakah Sudah Lunas?
        if (bayarDAO.isLunas(t.getIdTransaksi())) {
            JOptionPane.showMessageDialog(view, "Transaksi ini SUDAH LUNAS!", "Info", JOptionPane.INFORMATION_MESSAGE);
            // Opsional: Tetap tampilkan datanya tapi tombol bayar dimatikan
            resetForm(); 
            return;
        }

        // 4. Jika Valid, Tampilkan Data
        view.getTxtIdTransaksi().setText(String.valueOf(t.getIdTransaksi())); // Simpan ID (Hidden)
        view.getTxtNamaPelanggan().setText(t.getNamaPelanggan());
        view.getTxtTotalTagihan().setText(String.valueOf(t.getTotalHarga()));
        
        // Fokuskan kursor ke input uang biar kasir langsung ngetik
        view.getTxtUangBayar().requestFocus();
    }

    // --- LOGIKA 2: HITUNG KEMBALIAN (Realtime UX) ---
    public void hitungKembalianRealtime() {
        try {
            // Ambil angka dari textfield (hapus spasi kalau ada)
            int tagihan = Integer.parseInt(view.getTxtTotalTagihan().getText());
            int bayar = Integer.parseInt(view.getTxtUangBayar().getText());
            
            int kembalian = bayar - tagihan;
            view.getLblKembalian().setText("Rp " + kembalian);
            
            // Visual Feedback (Merah kalau kurang, Hijau kalau cukup)
            if (kembalian < 0) {
                view.getLblKembalian().setForeground(Color.RED);
            } else {
                view.getLblKembalian().setForeground(new Color(0, 150, 0));
            }
        } catch (Exception e) {
            view.getLblKembalian().setText("Rp 0");
        }
    }

    // --- LOGIKA 3: PROSES BAYAR ---
    public void prosesBayar() {
        // Cek apakah user sudah cari nota?
        if (view.getTxtIdTransaksi().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Lakukan 'Cek Nota' terlebih dahulu!");
            return;
        }

        try {
            int tagihan = Integer.parseInt(view.getTxtTotalTagihan().getText());
            int bayar = Integer.parseInt(view.getTxtUangBayar().getText());

            // 1. Validasi Uang Kurang
            if (bayar < tagihan) {
                JOptionPane.showMessageDialog(view, "Uang pembayaran KURANG!", "Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Siapkan Objek
            Pembayaran p = new Pembayaran();
            p.setIdTransaksi(Integer.parseInt(view.getTxtIdTransaksi().getText()));
            p.setMetode(view.getComboMetode().getSelectedItem().toString());
            p.setJumlahBayar(bayar);
            p.setStatusBayar("lunas");

            // 3. Simpan ke Database
            bayarDAO.insert(p);
            
            // 4. Update Status Transaksi jadi 'selesai' (Opsional tapi bagus)
            transDAO.updateStatusOnly(p.getIdTransaksi(), "selesai");

            // 5. Beri Kembalian & Reset
            int kembalian = bayar - tagihan;
            JOptionPane.showMessageDialog(view, "Pembayaran Berhasil!\nKembalian: Rp " + kembalian);
            
            resetForm();
            isiTabelRiwayat();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Pastikan input angka benar!");
        }
    }

    public void isiTabelRiwayat() {
        List<Pembayaran> list = bayarDAO.getAll();
        view.getTableModel().setRowCount(0);
        for (Pembayaran p : list) {
            view.getTableModel().addRow(new Object[]{
                p.getIdPembayaran(), p.getKodeNota(), p.getMetode(), 
                p.getJumlahBayar(), p.getStatusBayar(), p.getTglBayar()
            });
        }
    }

    public void resetForm() {
        view.getTxtCariNota().setText("");
        view.getTxtIdTransaksi().setText("");
        view.getTxtNamaPelanggan().setText("");
        view.getTxtTotalTagihan().setText("");
        view.getTxtUangBayar().setText("");
        view.getLblKembalian().setText("Rp 0");
        view.getTxtCariNota().requestFocus(); // Balikin fokus ke pencarian
    }
    
    // Tambahkan method ini di dalam class PembayaranController
    public void cetakLaporan() {
        // Cek apakah tabel ada datanya?
        if (view.getTableModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Tidak ada data untuk dicetak!");
            return;
        }

        try {
            // Header: Judul Laporan
            MessageFormat header = new MessageFormat("Laporan Riwayat Pembayaran - SILAU APP");
            
            // Footer: Halaman
            MessageFormat footer = new MessageFormat("Halaman {0,number,integer}");
            
            // Perintah Cetak Bawaan Java Swing
            // Akan memunculkan dialog print (Pilih "Microsoft Print to PDF" atau "Save as PDF")
            boolean complete = view.getTabelPembayaran().print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
            if (complete) {
                JOptionPane.showMessageDialog(view, "Laporan berhasil dicetak/disimpan!");
            } else {
                JOptionPane.showMessageDialog(view, "Pencetakan dibatalkan.");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal mencetak: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
