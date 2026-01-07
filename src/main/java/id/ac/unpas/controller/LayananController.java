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
import id.ac.unpas.model.Layanan;
import id.ac.unpas.view.LayananView;

import javax.swing.*;
import java.util.List;

public class LayananController {
    private LayananView view;
    private LayananDAO dao;

    public LayananController(LayananView view) {
        this.view = view;
        this.dao = new LayananDAO();
    }

    public void isiTabel() {
        List<Layanan> list = dao.getAll();
        view.getTableModel().setRowCount(0);
        for (Layanan l : list) {
            view.getTableModel().addRow(new Object[]{
                l.getIdLayanan(),
                l.getNamaLayanan(),
                l.getHargaPerKg(),
                l.getEstimasiHari()
            });
        }
    }

    public void insertData() {
        // Validasi input kosong (Estimasi dihapus dari sini karena JSpinner selalu ada isinya)
        if (view.getTxtNama().getText().isEmpty() || 
            view.getTxtHarga().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama dan Harga wajib diisi!");
            return;
        }

        try {
            Layanan l = new Layanan();
            l.setNamaLayanan(view.getTxtNama().getText());
            
            // Konversi String ke Integer untuk Harga
            int harga = Integer.parseInt(view.getTxtHarga().getText());
            
            // --- PERUBAHAN DI SINI (JSpinner) ---
            // Ambil value dari JSpinner (kembaliannya Object, jadi harus di-cast ke int)
            int hari = (int) view.getTxtEstimasi().getValue();
            
            l.setHargaPerKg(harga);
            l.setEstimasiHari(hari);

            dao.insert(l);
            JOptionPane.showMessageDialog(view, "Layanan Berhasil Disimpan");
            resetForm();
            isiTabel();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Harga harus berupa ANGKA!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateData() {
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data dulu!");
            return;
        }

        try {
            Layanan l = new Layanan();
            l.setIdLayanan(Integer.parseInt(view.getTxtId().getText()));
            l.setNamaLayanan(view.getTxtNama().getText());
            
            int harga = Integer.parseInt(view.getTxtHarga().getText());
            
            // --- PERUBAHAN DI SINI (JSpinner) ---
            int hari = (int) view.getTxtEstimasi().getValue();
            
            l.setHargaPerKg(harga);
            l.setEstimasiHari(hari);

            dao.update(l);
            JOptionPane.showMessageDialog(view, "Layanan Berhasil Diubah");
            resetForm();
            isiTabel();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Harga harus berupa ANGKA!");
        }
    }

    public void deleteData() {
        // Cek apakah field ID kosong (artinya user belum klik tabel)
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data terlebih dahulu!", "Peringatan", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // ---------------------

        int confirm = JOptionPane.showConfirmDialog(view, "Hapus layanan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(Integer.parseInt(view.getTxtId().getText()));
            resetForm();
            isiTabel();
        }
    }

    public void cariData() {
        String keyword = view.getTxtCari().getText();
        List<Layanan> list = dao.search(keyword);
        view.getTableModel().setRowCount(0);
        for (Layanan l : list) {
            view.getTableModel().addRow(new Object[]{
                l.getIdLayanan(), l.getNamaLayanan(), l.getHargaPerKg(), l.getEstimasiHari()
            });
        }
    }

    public void resetForm() {
        view.getTxtId().setText("");
        view.getTxtNama().setText("");
        view.getTxtHarga().setText("");
        
        // --- PERUBAHAN DI SINI ---
        // Reset JSpinner ke angka default (1), bukan setText("")
        view.getTxtEstimasi().setValue(1);
        
        view.getTxtCari().setText("");
        isiTabel();
    }

    public void pilihBaris() {
        int row = view.getTabelLayanan().getSelectedRow();
        if (row != -1) {
            view.getTxtId().setText(view.getTabelLayanan().getValueAt(row, 0).toString());
            view.getTxtNama().setText(view.getTabelLayanan().getValueAt(row, 1).toString());
            view.getTxtHarga().setText(view.getTabelLayanan().getValueAt(row, 2).toString());
            
            // --- PERUBAHAN DI SINI ---
            // Ambil data dari tabel (String/Object), ubah ke int, lalu masukkan ke JSpinner
            int estimasi = Integer.parseInt(view.getTabelLayanan().getValueAt(row, 3).toString());
            view.getTxtEstimasi().setValue(estimasi);
        }
    }
}