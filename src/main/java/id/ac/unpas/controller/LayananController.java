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

    // 1. ISI TABEL (REFRESH)
    public void isiTabel() {
        List<Layanan> list = dao.getAll();
        view.getTableModel().setRowCount(0); // Kosongkan tabel dulu

        for (Layanan l : list) {
            view.getTableModel().addRow(new Object[]{
                    l.getIdLayanan(),
                    l.getNamaLayanan(),
                    l.getHargaPerKg(),
                    l.getEstimasiHari()
            });
        }
    }

    // 2. SIMPAN DATA
    public void insertData() {
        // Validasi Input
        if (view.getTxtNama().getText().isEmpty() || view.getTxtHarga().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama dan Harga tidak boleh kosong!");
            return;
        }

        try {
            Layanan l = new Layanan();
            l.setNamaLayanan(view.getTxtNama().getText());
            l.setHargaPerKg(Integer.parseInt(view.getTxtHarga().getText()));
            l.setEstimasiHari((int) view.getSpinnerEstimasi().getValue());

            dao.insert(l); // Simpan ke DB

            JOptionPane.showMessageDialog(view, "Berhasil Simpan Layanan!");
            resetForm(); // Bersihkan form
            isiTabel();  // PENTING: Refresh tabel agar data muncul!

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Harga harus berupa ANGKA!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    // 3. UBAH DATA
    public void updateData() {
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih baris data dulu!");
            return;
        }

        try {
            Layanan l = new Layanan();
            l.setIdLayanan(Integer.parseInt(view.getTxtId().getText()));
            l.setNamaLayanan(view.getTxtNama().getText());
            l.setHargaPerKg(Integer.parseInt(view.getTxtHarga().getText()));
            l.setEstimasiHari((int) view.getSpinnerEstimasi().getValue());

            dao.update(l);

            JOptionPane.showMessageDialog(view, "Berhasil Ubah Layanan!");
            resetForm();
            isiTabel(); // Refresh tabel

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    // 4. HAPUS DATA
    public void deleteData() {
        if (view.getTxtId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih baris data dulu!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus layanan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(Integer.parseInt(view.getTxtId().getText()));
            resetForm();
            isiTabel(); // Refresh tabel
        }
    }

    // 5. CARI DATA
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

    // 6. RESET FORM
    public void resetForm() {
        view.getTxtId().setText("");
        view.getTxtNama().setText("");
        view.getTxtHarga().setText("");
        view.getSpinnerEstimasi().setValue(1);
        view.getTxtCari().setText("");
        isiTabel(); // Refresh tabel normal
    }

    // 7. PILIH BARIS (Saat tabel diklik)
    public void pilihBaris() {
        int row = view.getTabelLayanan().getSelectedRow();
        if (row != -1) {
            view.getTxtId().setText(view.getTabelLayanan().getValueAt(row, 0).toString());
            view.getTxtNama().setText(view.getTabelLayanan().getValueAt(row, 1).toString());
            view.getTxtHarga().setText(view.getTabelLayanan().getValueAt(row, 2).toString());
            view.getSpinnerEstimasi().setValue(Integer.parseInt(view.getTabelLayanan().getValueAt(row, 3).toString()));
        }
    }
}