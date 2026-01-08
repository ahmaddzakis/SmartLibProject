/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.view;

/**
 *
 * @author Ahmad Dzaki
 */

import id.ac.unpas.controller.LayananController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LayananView extends JPanel {
private JTextField txtId, txtNama, txtHarga, txtCari;
    private JButton btnSimpan, btnUbah, btnHapus, btnReset, btnCari;
    private JTable tabelLayanan;
    private DefaultTableModel tableModel;
    
    // Variabel Spinner untuk angka
    private JSpinner txtEstimasi; 

    private LayananController controller;

    public LayananView() {
        // Setup Layout Utama & Padding Pinggir (Biar rapi)
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Memberi jarak 20px kiri-kanan
        
        initComponents();
        
        controller = new LayananController(this);
        controller.isiTabel();
    }

    private void initComponents() {
        // --- 1. JUDUL HALAMAN (TOP / NORTH) ---
        JLabel lblJudul = new JLabel("Kelola Layanan Laundry");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblJudul.setForeground(new Color(50, 50, 50));
        lblJudul.setBorder(new EmptyBorder(0, 0, 20, 0)); // Jarak bawah judul
        
        // Pasang Judul di Atas Utama
        add(lblJudul, BorderLayout.NORTH);

        // --- 2. CONTAINER UNTUK ISI (CENTER) ---
        // Panel ini menampung Form (di Atasnya) dan Tabel (di Tengahnya)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // A. PANEL INPUT
        JPanel panelInput = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Data Layanan"));

        panelInput.add(new JLabel("Nama Layanan (ex: Cuci Kering):"));
        txtNama = new JTextField();
        panelInput.add(txtNama);

        panelInput.add(new JLabel("Harga Per Kg (Rp):"));
        txtHarga = new JTextField(); // Input angka
        panelInput.add(txtHarga);

        panelInput.add(new JLabel("Estimasi Selesai (Hari):"));
        
        // Setup JSpinner (Agar input hari pakai tombol naik/turun)
        txtEstimasi = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) txtEstimasi.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        editor.getTextField().setEditable(false);

        panelInput.add(txtEstimasi);
        

        // ID Sembunyi
        txtId = new JTextField();
        txtId.setVisible(false);

        // B. PANEL TOMBOL
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Rata Kanan biar rapi
        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);
        
        // Gabung Input & Tombol dalam satu Wrapper
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(panelInput, BorderLayout.CENTER);
        formWrapper.add(panelTombol, BorderLayout.SOUTH);

        // C. PANEL TABEL & CARI
        JPanel panelBawah = new JPanel(new BorderLayout(0, 10)); // Gap vertical 10
        panelBawah.setBorder(new EmptyBorder(20, 0, 0, 0)); // Jarak dari form ke tabel
        
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCari = new JTextField(20);
        btnCari = new JButton("Cari");
        panelCari.add(new JLabel("Cari Layanan:"));
        panelCari.add(txtCari);
        panelCari.add(btnCari);

        String[] kolom = {"ID", "Nama Layanan", "Harga/Kg", "Estimasi (Hari)"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelLayanan = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelLayanan);

        panelBawah.add(panelCari, BorderLayout.NORTH);
        panelBawah.add(scrollPane, BorderLayout.CENTER);

        // --- GABUNG KE CONTENT PANEL ---
        contentPanel.add(formWrapper, BorderLayout.NORTH); // Form nempel di atas
        contentPanel.add(panelBawah, BorderLayout.CENTER); // Tabel ngisi sisa ruang

        // Pasang Content Panel ke Center Utama
        add(contentPanel, BorderLayout.CENTER);

        // --- ACTION LISTENERS ---
        btnSimpan.addActionListener(e -> controller.insertData());
        btnUbah.addActionListener(e -> controller.updateData());
        btnHapus.addActionListener(e -> controller.deleteData());
        btnReset.addActionListener(e -> controller.resetForm());
        btnCari.addActionListener(e -> controller.cariData());

        tabelLayanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller.pilihBaris();
            }
        });
    }

    // GETTERS
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNama() { return txtNama; }
    public JTextField getTxtHarga() { return txtHarga; }
    
    // Perbaikan: Return JSpinner, bukan JTextField
    public JSpinner getTxtEstimasi() { return txtEstimasi; }
    
    public JTextField getTxtCari() { return txtCari; }
    public JTable getTabelLayanan() { return tabelLayanan; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
