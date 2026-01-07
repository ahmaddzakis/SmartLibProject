/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.laundry.view;

/**
 *
 * @author Ilona Aqila Zahra
 */

import id.ac.unpas.controller.AdminController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminView extends JPanel {
    private JTextField txtId, txtNama, txtUsername;
    private JPasswordField txtPassword; // Pakai PasswordField biar bintang-bintang (*)
    private JButton btnSimpan, btnUbah, btnHapus, btnReset;
    private JTable tabelAdmin;
    private DefaultTableModel tableModel;
    
    private AdminController controller;

    public AdminView() {
//        setTitle("Kelola Data Admin");
//        setSize(700, 500);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
        
        initComponents();
        
        controller = new AdminController(this);
        controller.isiTabel();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // --- PANEL INPUT ---
        JPanel panelInput = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelInput.add(new JLabel("Nama Lengkap:"));
        txtNama = new JTextField();
        panelInput.add(txtNama);

        panelInput.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panelInput.add(txtUsername);

        panelInput.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(); // Inputan password tertutup
        panelInput.add(txtPassword);

        // ID Sembunyi
        txtId = new JTextField();
        txtId.setVisible(false);

        // --- PANEL TOMBOL ---
        JPanel panelTombol = new JPanel(new FlowLayout());
        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);

        // --- TABEL ---
        String[] kolom = {"ID", "Nama", "Username", "Password"}; // Password ditampilkan untuk demo (di real project jangan)
        tableModel = new DefaultTableModel(kolom, 0);
        tabelAdmin = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelAdmin);

        // Gabung UI
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- EVENTS ---
        btnSimpan.addActionListener(e -> controller.insertData());
        btnUbah.addActionListener(e -> controller.updateData());
        btnHapus.addActionListener(e -> controller.deleteData());
        btnReset.addActionListener(e -> controller.resetForm());
        
        tabelAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller.pilihBaris();
            }
        });
    }

    // Getter Components
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNama() { return txtNama; }
    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; } // Return tipe JPasswordField
    public JTable getTabelAdmin() { return tabelAdmin; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
