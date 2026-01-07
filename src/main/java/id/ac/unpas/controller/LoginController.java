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
import id.ac.unpas.model.Admin;
import id.ac.unpas.view.LoginView;
import id.ac.unpas.view.MainFrame;

import javax.swing.*;

public class LoginController {
    private LoginView view;
    private AdminDAO dao;

    public LoginController(LoginView view) {
        this.view = view;
        this.dao = new AdminDAO();
    }

    public void prosesLogin() {
        String user = view.getTxtUsername().getText();
        String pass = new String(view.getTxtPassword().getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username dan Password tidak boleh kosong!");
            return;
        }

        // Cek ke Database
        Admin admin = dao.login(user, pass);

        if (admin != null) {
            // LOGIN SUKSES
            JOptionPane.showMessageDialog(view, "Berhasil Masuk!");
            
            // Buka Menu Utama
            new MainFrame().setVisible(true);
            
            // Tutup Login
            view.dispose();
        } else {
            // LOGIN GAGAL
            JOptionPane.showMessageDialog(view, "Username atau Password Salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}
