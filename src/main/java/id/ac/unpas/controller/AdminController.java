/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.controller;

/**
 *
 * @author Mustika Weni
 */

import id.ac.unpas.dao.AdminDAO;
import id.ac.unpas.view.AdminView;
import id.ac.unpas.model.Admin;

import javax.swing.*;
import java.util.List;

public class AdminController {
    private AdminView view;
    private AdminDAO dao;

    public AdminController(AdminView view) {
        this.view = view;
        this.dao = new AdminDAO();
    }

    public void isiTabel() {
        List<Admin> list = dao.getAll();
        view.getTableModel().setRowCount(0);
        for (Admin a : list) {
            view.getTableModel().addRow(new Object[]{
                a.getIdAdmin(), a.getNama(), a.getUsername(), a.getPassword()
            });
        }
    }

    public void insertData() {
        String pass = new String(view.getTxtPassword().getPassword()); // Ambil text password
        
        if (view.getTxtUsername().getText().isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username & Password wajib diisi!");
            return;
        }

        Admin a = new Admin();
        a.setNama(view.getTxtNama().getText());
        a.setUsername(view.getTxtUsername().getText());
        a.setPassword(pass);

        dao.insert(a);
        JOptionPane.showMessageDialog(view, "Admin Baru Ditambahkan");
        resetForm();
        isiTabel();
    }

    public void updateData() {
        if (view.getTxtId().getText().isEmpty()) return;
        
        Admin a = new Admin();
        a.setIdAdmin(Integer.parseInt(view.getTxtId().getText()));
        a.setNama(view.getTxtNama().getText());
        a.setUsername(view.getTxtUsername().getText());
        a.setPassword(new String(view.getTxtPassword().getPassword()));

        dao.update(a);
        JOptionPane.showMessageDialog(view, "Data Admin Diubah");
        resetForm();
        isiTabel();
    }

    public void deleteData() {
        if (view.getTxtId().getText().isEmpty()) return;
        
        int confirm = JOptionPane.showConfirmDialog(view, "Hapus Admin ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(Integer.parseInt(view.getTxtId().getText()));
            resetForm();
            isiTabel();
        }
    }

    public void resetForm() {
        view.getTxtId().setText("");
        view.getTxtNama().setText("");
        view.getTxtUsername().setText("");
        view.getTxtPassword().setText("");
    }

    public void pilihBaris() {
        int row = view.getTabelAdmin().getSelectedRow();
        if (row != -1) {
            view.getTxtId().setText(view.getTabelAdmin().getValueAt(row, 0).toString());
            view.getTxtNama().setText(view.getTabelAdmin().getValueAt(row, 1).toString());
            view.getTxtUsername().setText(view.getTabelAdmin().getValueAt(row, 2).toString());
            view.getTxtPassword().setText(view.getTabelAdmin().getValueAt(row, 3).toString());
        }
    }
}
