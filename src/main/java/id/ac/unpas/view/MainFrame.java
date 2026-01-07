/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.view;

/**
 *
 * @author Ilona Aqila Zahra
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
   private final Color SIDEBAR_COLOR = new Color(52, 73, 94);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color HOVER_COLOR = new Color(44, 62, 80);

    // Panel Tengah (Tempat gonta-ganti isi)
    private JPanel contentPanel;

    public MainFrame() {
        setTitle("Sistem Informasi Manajemen Laundry - SILAU APP");
        setSize(1200, 700); // Lebih lebar biar muat tabel
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- VALIDASI SAAT TOMBOL 'X' DITEKAN ---
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Jangan langsung tutup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                konfirmasiKeluar();
            }
        });

        // --- 1. SIDEBAR (KIRI) ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 0)); // Lebar 250, Tinggi ngikutin
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Judul Sidebar
        JLabel lblTitle = new JLabel("SILAU");
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Rapih, Bersih, Murah");
        lblSubtitle.setForeground(new Color(189, 195, 199));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(200, 10));
        separator.setForeground(new Color(100, 100, 100));

        // Tombol Sidebar
        JButton btnPelanggan = createMenuButton("Kelola Pelanggan");
        JButton btnLayanan   = createMenuButton("Kelola Layanan");
        JButton btnTransaksi = createMenuButton("Transaksi");
        JButton btnLaporan   = createMenuButton("Laporan & Pembayaran");
        JButton btnLogout    = createMenuButton("Keluar");

        // --- 2. CONTENT AREA (TENGAH) ---
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout()); // Wajib BorderLayout biar panel isinya full
        contentPanel.setBackground(Color.WHITE);
        
        // Tampilan Awal (Dashboard / Welcome)
        showDashboard(); 

        // --- 3. LOGIKA GANTI HALAMAN ---
        btnPelanggan.addActionListener(e -> setContent(new PelangganView()));
        btnLayanan.addActionListener(e -> setContent(new LayananView()));
        btnTransaksi.addActionListener(e -> setContent(new TransaksiView()));
        btnLaporan.addActionListener(e -> setContent(new PembayaranView()));
        
        // LOGIKA LOGOUT DENGAN VALIDASI
        btnLogout.addActionListener(e -> {
            konfirmasiKeluar();
        });

        // Susun Sidebar
        sidebar.add(lblTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(lblSubtitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(separator);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnPelanggan);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnLayanan);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnTransaksi);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnLaporan);
        sidebar.add(Box.createVerticalGlue()); 
        sidebar.add(btnLogout);

        // Gabung ke Frame
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    // --- HELPER: Method Konfirmasi Keluar ---
    private void konfirmasiKeluar() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin keluar?", 
            "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            new id.ac.unpas.laundry.view.LoginView().setVisible(true);
            this.dispose(); // Tutup MainFrame
        }
    }

    // --- METHOD UNTUK GANTI ISI PANEL TENGAH ---
    private void setContent(JPanel panelBaru) {
        contentPanel.removeAll(); // 1. Buang isi lama
        contentPanel.add(panelBaru, BorderLayout.CENTER); // 2. Tempel isi baru
        contentPanel.revalidate(); // 3. Hitung ulang layout
        contentPanel.repaint(); // 4. Gambar ulang layar
    }

    // Method untuk menampilkan dashboard awal
    private void showDashboard() {
        JPanel dashboard = new JPanel(new GridBagLayout());
        dashboard.setBackground(Color.WHITE);
        JLabel lbl = new JLabel("Selamat Datang, Admin!");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lbl.setForeground(new Color(50, 50, 50));
        dashboard.add(lbl);
        
        setContent(dashboard);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBackground(SIDEBAR_COLOR);
        btn.setForeground(TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(SIDEBAR_COLOR);
            }
        });
        return btn;
    }
}
