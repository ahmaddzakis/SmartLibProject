/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.view;

/**
 *
 * @author Ahmad Dzaki
 */

import id.ac.unpas.controller.PembayaranController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PembayaranView extends JPanel {
   // Komponen UI
    private JTextField txtCariNota;
    private JButton btnCekNota;
    
    // Info Transaksi
    private JTextField txtIdTransaksi, txtNamaPelanggan, txtTotalTagihan;
    
    // Input Pembayaran
    private JComboBox<String> comboMetode;
    private JTextField txtUangBayar;
    private JLabel lblKembalian;
    
    // Tombol Action
    private JButton btnBayar, btnReset, btnCetak; // Tambah btnCetak
    
    // Tabel Riwayat
    private JTable tabelPembayaran;
    private DefaultTableModel tableModel;
    
    private PembayaranController controller;

    public PembayaranView() {
        // Setup Layout & Padding Konsisten
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding 20px
        
        initComponents();
        
        controller = new PembayaranController(this);
        controller.isiTabelRiwayat();
    }

    private void initComponents() {
        // --- 1. JUDUL HALAMAN ---
        JLabel lblJudul = new JLabel("Laporan & Pembayaran");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblJudul.setForeground(new Color(50, 50, 50));
        lblJudul.setBorder(new EmptyBorder(0, 0, 20, 0));

        // --- 2. CONTAINER ATAS (Cari + Form + Tombol) ---
        JPanel panelContainerAtas = new JPanel(new BorderLayout());
        
        // A. PANEL CARI
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCari.setBorder(BorderFactory.createTitledBorder("Cari Transaksi"));
        
        txtCariNota = new JTextField(20);
        btnCekNota = new JButton("Cek Nota");
        
        panelCari.add(new JLabel("Kode Nota:"));
        panelCari.add(txtCariNota);
        panelCari.add(btnCekNota);
        
        panelContainerAtas.add(panelCari, BorderLayout.NORTH);

        // B. PANEL FORM (GridBagLayout)
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Proses Pembayaran"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Baris 1: Nama Pelanggan
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(new JLabel("Nama Pelanggan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtNamaPelanggan = new JTextField();
        txtNamaPelanggan.setEditable(false);
        panelForm.add(txtNamaPelanggan, gbc);
        
        // Baris 2: Total Tagihan
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("Total Tagihan (Rp):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtTotalTagihan = new JTextField();
        txtTotalTagihan.setEditable(false);
        txtTotalTagihan.setFont(new Font("Arial", Font.BOLD, 14));
        panelForm.add(txtTotalTagihan, gbc);

        // Baris 3: Metode
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Metode Pembayaran:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        String[] metode = {"cash", "transfer", "e-wallet"};
        comboMetode = new JComboBox<>(metode);
        panelForm.add(comboMetode, gbc);
        
        // Baris 4: Uang Dibayar
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelForm.add(new JLabel("Uang Dibayar (Rp):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        txtUangBayar = new JTextField();
        panelForm.add(txtUangBayar, gbc);
        
        // Baris 5: Kembalian
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panelForm.add(new JLabel("Kembalian:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        lblKembalian = new JLabel("Rp 0");
        lblKembalian.setFont(new Font("Arial", Font.BOLD, 18));
        lblKembalian.setForeground(new Color(0, 150, 0));
        panelForm.add(lblKembalian, gbc);

        txtIdTransaksi = new JTextField(); // Hidden
        
        panelContainerAtas.add(panelForm, BorderLayout.CENTER);

        // C. PANEL TOMBOL
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Gap 10px
        
        btnBayar = new JButton("PROSES BAYAR (LUNAS)");
        btnBayar.setPreferredSize(new Dimension(200, 40));
        btnBayar.setBackground(new Color(40, 167, 69)); // Hijau
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnReset = new JButton("Reset");
        btnReset.setPreferredSize(new Dimension(100, 40));
        
        // --- TOMBOL CETAK BARU ---
        btnCetak = new JButton("Cetak PDF");
        btnCetak.setPreferredSize(new Dimension(120, 40));
        btnCetak.setBackground(new Color(0, 123, 255)); // Biru
        btnCetak.setForeground(Color.WHITE);
        btnCetak.setFont(new Font("Arial", Font.BOLD, 12));
        
        panelTombol.add(btnBayar);
        panelTombol.add(btnReset);
        panelTombol.add(btnCetak); // Posisi setelah Reset
        
        panelContainerAtas.add(panelTombol, BorderLayout.SOUTH);

        // --- 3. GABUNGKAN JUDUL + CONTAINER ATAS ---
        JPanel headerWrapper = new JPanel(new BorderLayout());
        headerWrapper.add(lblJudul, BorderLayout.NORTH);
        headerWrapper.add(panelContainerAtas, BorderLayout.CENTER);

        // --- 4. TABEL RIWAYAT ---
        String[] kolom = {"ID Bayar", "Kode Nota", "Metode", "Jml Bayar", "Status", "Tgl Bayar"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPembayaran = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelPembayaran);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Riwayat Pembayaran"));

        // Add to Main Panel
        add(headerWrapper, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- EVENTS ---
        btnCekNota.addActionListener(e -> controller.cekNota());
        btnBayar.addActionListener(e -> controller.prosesBayar());
        btnReset.addActionListener(e -> controller.resetForm());
        
        // Event Cetak
        btnCetak.addActionListener(e -> controller.cetakLaporan());
        
        txtUangBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                controller.hitungKembalianRealtime();
            }
        });
    }

    // Getters
    public JTextField getTxtCariNota() { return txtCariNota; }
    public JTextField getTxtIdTransaksi() { return txtIdTransaksi; }
    public JTextField getTxtNamaPelanggan() { return txtNamaPelanggan; }
    public JTextField getTxtTotalTagihan() { return txtTotalTagihan; }
    public JTextField getTxtUangBayar() { return txtUangBayar; }
    public JComboBox<String> getComboMetode() { return comboMetode; }
    public JLabel getLblKembalian() { return lblKembalian; }
    public JTable getTabelPembayaran() { return tabelPembayaran; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
