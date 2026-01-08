package id.ac.unpas.view;

import id.ac.unpas.controller.DashboardController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardView extends JPanel {
    private JLabel lblIncome, lblProses, lblSelesai, lblPelanggan;
    private JTable tableRecent;
    private DefaultTableModel tableModel;
    private DashboardController controller;

    public DashboardView() {
        // PENTING: Gunakan BorderLayout agar Tabel bisa Scroll penuh
        setLayout(new BorderLayout(0, 20)); // Gap vertikal 20px
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        initComponents();

        controller = new DashboardController(this);
        controller.loadData();
    }

    private void initComponents() {
        // --- CONTAINER ATAS (Judul + Kartu) ---
        // Kita bungkus Judul dan Kartu dalam satu panel "topContainer"
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(Color.WHITE);

        // 1. HEADER
        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(50, 50, 50));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        topContainer.add(lblTitle);
        topContainer.add(Box.createVerticalStrut(20)); // Jarak

        // 2. PANEL KARTU STATISTIK
        JPanel cardPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(0, 120));
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblIncome = new JLabel("Rp 0");
        lblProses = new JLabel("0");
        lblSelesai = new JLabel("0");
        lblPelanggan = new JLabel("0");

        cardPanel.add(createCard("Pemasukan Hari Ini", lblIncome, new Color(46, 204, 113)));
        cardPanel.add(createCard("Sedang Diproses", lblProses, new Color(241, 196, 15)));
        cardPanel.add(createCard("Siap Diambil", lblSelesai, new Color(52, 152, 219)));
        cardPanel.add(createCard("Total Pelanggan", lblPelanggan, new Color(155, 89, 182)));

        topContainer.add(cardPanel);

        // Masukkan topContainer ke bagian ATAS (NORTH) layout utama
        add(topContainer, BorderLayout.NORTH);

        // --- CONTAINER BAWAH (TABEL) ---
        // Panel Tabel ditaruh di CENTER agar mengisi sisa ruang & bisa scroll
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel lblTableTitle = new JLabel("Riwayat Semua Transaksi"); // Judul diganti
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTableTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] columns = {"Kode Nota", "Pelanggan", "Total", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ini kuncinya: Mematikan edit untuk semua sel
            }
        };
        tableRecent = new JTable(tableModel);
        tableRecent = new JTable(tableModel);
        tableRecent.setRowHeight(30);
        tableRecent.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(tableRecent);
        scroll.getViewport().setBackground(Color.WHITE);

        tablePanel.add(lblTableTitle, BorderLayout.NORTH);
        tablePanel.add(scroll, BorderLayout.CENTER); // Tabel di tengah panel tabel

        // Masukkan tablePanel ke bagian TENGAH (CENTER) layout utama
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(Color.WHITE);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(lblTitle);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    public JLabel getLblIncome() { return lblIncome; }
    public JLabel getLblProses() { return lblProses; }
    public JLabel getLblSelesai() { return lblSelesai; }
    public JLabel getLblPelanggan() { return lblPelanggan; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
