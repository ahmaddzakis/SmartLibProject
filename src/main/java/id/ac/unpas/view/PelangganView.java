/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.view;

/**
 *
 * @author Ahmad Dzaki
 */

import id.ac.unpas.controller.PelangganController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PelangganView extends JPanel {
   // Komponen UI
    private JTextField txtId, txtNama, txtHp, txtCari;
    private JTextArea txtAlamat;
    private JButton btnSimpan, btnUbah, btnHapus, btnReset, btnCari;
    private JTable tabelPelanggan;
    private DefaultTableModel tableModel;
    
    private PelangganController controller;

    public PelangganView() {
        setLayout(new BorderLayout()); // Layout Utama
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Beri jarak pinggir biar gak nempel
        
        initComponents();
        
        controller = new PelangganController(this);
        controller.isiTabel();
    }

    private void initComponents() {
        // --- 1. JUDUL HALAMAN (TOP) ---
        JLabel lblJudul = new JLabel("Kelola Data Pelanggan");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblJudul.setForeground(new Color(50, 50, 50));
        lblJudul.setBorder(new EmptyBorder(0, 0, 20, 0)); // Jarak bawah judul
        add(lblJudul, BorderLayout.NORTH);

        // --- 2. CONTAINER TENGAH (FORM + TOMBOL) ---
        // Kita bungkus Form dan Tombol dalam satu panel di bagian ATAS dari Center
        JPanel panelFormContainer = new JPanel(new BorderLayout());
        
        // A. FORM INPUT (GridBagLayout biar rapi & tidak melar)
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Data Pelanggan"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Jarak antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponen melebar horizontal
        gbc.anchor = GridBagConstraints.WEST; // Rata kiri
        
        // Baris 1: Nama
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelInput.add(new JLabel("Nama Pelanggan:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; // Field boleh melebar
        txtNama = new JTextField();
        panelInput.add(txtNama, gbc);

        // Baris 2: No HP
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelInput.add(new JLabel("No. HP:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtHp = new JTextField();
        panelInput.add(txtHp, gbc);

        // Baris 3: Alamat
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Label alamat di atas
        panelInput.add(new JLabel("Alamat:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtAlamat = new JTextArea(3, 20); // Tinggi 3 baris
        JScrollPane scrollAlamat = new JScrollPane(txtAlamat);
        panelInput.add(scrollAlamat, gbc);

        // ID Tersembunyi
        txtId = new JTextField(); 

        // B. TOMBOL ACTION
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Tombol rata kanan
        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);

        // Gabung Form & Tombol
        panelFormContainer.add(panelInput, BorderLayout.CENTER);
        panelFormContainer.add(panelTombol, BorderLayout.SOUTH);

        // --- 3. CONTAINER BAWAH (TABEL & SEARCH) ---
        JPanel panelBawah = new JPanel(new BorderLayout(0, 10));
        panelBawah.setBorder(new EmptyBorder(20, 0, 0, 0)); // Jarak dari form ke tabel
        
        // Panel Cari
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCari = new JTextField(20);
        btnCari = new JButton("Cari");
        panelCari.add(new JLabel("Cari Data:"));
        panelCari.add(txtCari);
        panelCari.add(btnCari);
        
        // Tabel
        String[] kolom = {"ID", "Nama", "No HP", "Alamat"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPelanggan = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelPelanggan);
        
        panelBawah.add(panelCari, BorderLayout.NORTH);
        panelBawah.add(scrollPane, BorderLayout.CENTER);

        // --- FINAL ASSEMBLY ---
        // Kita pakai Panel baru untuk menampung FormContainer agar dia tidak memenuhi layar vertikal
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.add(panelFormContainer, BorderLayout.NORTH);
        contentWrapper.add(panelBawah, BorderLayout.CENTER);
        
        add(contentWrapper, BorderLayout.CENTER);
        
        // --- EVENTS ---
        btnSimpan.addActionListener(e -> controller.insertData());
        btnUbah.addActionListener(e -> controller.updateData());
        btnHapus.addActionListener(e -> controller.deleteData());
        btnReset.addActionListener(e -> controller.resetForm());
        btnCari.addActionListener(e -> controller.cariData());
        
        tabelPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller.pilihBaris();
            }
        });
    }

    // GETTERS
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNama() { return txtNama; }
    public JTextField getTxtHp() { return txtHp; }
    public JTextArea getTxtAlamat() { return txtAlamat; }
    public JTextField getTxtCari() { return txtCari; }
    public JTable getTabelPelanggan() { return tabelPelanggan; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
