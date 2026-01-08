/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.view;

/**
 *
 * @author Ahmad Dzaki
 */

import id.ac.unpas.controller.TransaksiController;
import id.ac.unpas.model.Layanan;
import id.ac.unpas.model.Pelanggan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransaksiView extends JPanel {
    // Komponen
    private JTextField txtId, txtNota, txtBerat, txtTotal, txtCari;
    private JComboBox<Pelanggan> comboPelanggan;
    private JComboBox<Layanan> comboLayanan;
    private JComboBox<String> comboStatus;
    private JButton btnSimpan, btnUbahStatus, btnHapus, btnReset, btnCari, btnHitung;
    private JTable tabelTransaksi;
    private DefaultTableModel tableModel;

    private TransaksiController controller;

    public TransaksiView() {
        // Setup Layout & Padding Konsisten
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Jarak pinggir 20px

        initComponents();

        controller = new TransaksiController(this);
        controller.isiComboData();
        controller.isiTabel();
    }

    private void initComponents() {
        // --- 1. JUDUL HALAMAN ---
        JLabel lblJudul = new JLabel("Transaksi Laundry");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblJudul.setForeground(new Color(50, 50, 50));
        lblJudul.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(lblJudul, BorderLayout.NORTH);

        // --- 2. WRAPPER UTAMA (Form + Tabel) ---
        JPanel contentPanel = new JPanel(new BorderLayout());

        // A. PANEL INPUT (FORM)
        JPanel panelInput = new JPanel(new GridLayout(6, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Transaksi Baru"));

        // 1. Kode Nota
        panelInput.add(new JLabel("Kode Nota (Auto):"));
        txtNota = new JTextField();
        txtNota.setEditable(false);
        panelInput.add(txtNota);

        // 2. Pilih Pelanggan
        panelInput.add(new JLabel("Pilih Pelanggan:"));
        comboPelanggan = new JComboBox<>();
        panelInput.add(comboPelanggan);

        // 3. Pilih Layanan
        panelInput.add(new JLabel("Pilih Layanan:"));
        comboLayanan = new JComboBox<>();
        panelInput.add(comboLayanan);

        // 4. Berat
        panelInput.add(new JLabel("Berat (Kg):"));
        JPanel panelBerat = new JPanel(new BorderLayout(5, 0));
        txtBerat = new JTextField();
        btnHitung = new JButton("Hitung");
        panelBerat.add(txtBerat, BorderLayout.CENTER);
        panelBerat.add(btnHitung, BorderLayout.EAST);
        panelInput.add(panelBerat);

        // 5. Total Harga
        panelInput.add(new JLabel("Total Harga (Rp):"));
        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setBackground(new Color(240, 240, 240));
        txtTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelInput.add(txtTotal);

        // 6. Status
        panelInput.add(new JLabel("Status Laundry:"));
        // Status 'antri' dihapus, start dari 'proses'
        String[] statusList = {"proses", "selesai", "diambil"};
        comboStatus = new JComboBox<>(statusList);
        comboStatus.setEnabled(false);
        panelInput.add(comboStatus);

        // ID Tersembunyi
        txtId = new JTextField();

        // B. PANEL TOMBOL
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSimpan = new JButton("Simpan Transaksi Baru");
        btnUbahStatus = new JButton("Ubah Data"); // Ganti teksnya
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbahStatus);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);

        // Gabung Form & Tombol (Taruh di Utara contentPanel)
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(panelInput, BorderLayout.CENTER);
        formWrapper.add(panelTombol, BorderLayout.SOUTH);

        contentPanel.add(formWrapper, BorderLayout.NORTH);

        // C. PANEL TABEL & CARI
        JPanel panelBawah = new JPanel(new BorderLayout(0, 10));
        panelBawah.setBorder(new EmptyBorder(20, 0, 0, 0)); // Jarak dari form

        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // --- BAGIAN YANG DIUBAH ---
        panelCari.add(new JLabel("Cari Nota / Pelanggan:")); // 1. Tambah Label

        txtCari = new JTextField(20);
        panelCari.add(txtCari);

        btnCari = new JButton("Cari"); // 2. Tombol jadi lebih pendek
        panelCari.add(btnCari);
        // --------------------------

        String[] kolom = {"ID", "Nota", "Pelanggan", "Layanan", "Berat", "Total", "Status", "Tgl Masuk"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelTransaksi = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelTransaksi);

        panelBawah.add(panelCari, BorderLayout.NORTH);
        panelBawah.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(panelBawah, BorderLayout.CENTER);

        // Add Content Panel ke Center Utama
        add(contentPanel, BorderLayout.CENTER);

        // --- EVENTS ---
        btnHitung.addActionListener(e -> controller.hitungHarga());
        btnSimpan.addActionListener(e -> controller.insertData());
        btnUbahStatus.addActionListener(e -> controller.updateData());
        btnHapus.addActionListener(e -> controller.deleteData());
        btnReset.addActionListener(e -> controller.resetForm());
        btnCari.addActionListener(e -> controller.cariData());

        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller.pilihBaris();
            }
        });
    }

    // Getters
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNota() { return txtNota; }
    public JTextField getTxtBerat() { return txtBerat; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JTextField getTxtCari() { return txtCari; }
    public JComboBox<Pelanggan> getComboPelanggan() { return comboPelanggan; }
    public JComboBox<Layanan> getComboLayanan() { return comboLayanan; }
    public JComboBox<String> getComboStatus() { return comboStatus; }
    public JTable getTabelTransaksi() { return tabelTransaksi; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
