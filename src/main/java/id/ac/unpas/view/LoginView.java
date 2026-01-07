/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.laundry.view;

/**
 *
 * @author Ilona Aqila Zahra
 */

import id.ac.unpas.controller.LoginController;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    
    // Warna Tema (Sesuai Dashboard)
    private final Color PRIMARY_COLOR = new Color(52, 73, 94);   // Dark Blue
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BG_COLOR = Color.WHITE;
    
    private LoginController controller;

    public LoginView() {
        setTitle("Masuk ke SILAU");
        setSize(1200, 700); // Ukuran Compact
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        
        controller = new LoginController(this);
        
        // Enter = Klik Login
        getRootPane().setDefaultButton(btnLogin);
    }

    private void initComponents() {
        // Kita bagi layar jadi 2: Kiri (Branding) & Kanan (Input)
        setLayout(new GridLayout(1, 2));

        // --- 1. PANEL KIRI (BRANDING) ---
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(PRIMARY_COLOR);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        // Judul di Kiri
        JLabel lblBrand = new JLabel("SILAU");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBrand.setForeground(TEXT_COLOR);
        lblBrand.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSlogan = new JLabel("Sistem Manajemen Laundry");
        lblSlogan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSlogan.setForeground(new Color(200, 200, 200));
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Spacer buat nengahin teks secara vertikal
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(lblBrand);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(lblSlogan);
        leftPanel.add(Box.createVerticalGlue());

        // --- 2. PANEL KANAN (FORM LOGIN) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(BG_COLOR);
        rightPanel.setLayout(new GridBagLayout()); // Biar form ada di tengah
        
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(BG_COLOR);
        formContainer.setBorder(new EmptyBorder(20, 40, 20, 40)); // Padding dalam
        
        // Header Form
        JLabel lblLogin = new JLabel("Silakan Login");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogin.setForeground(PRIMARY_COLOR);
        lblLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label & Input Username
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setForeground(Color.GRAY);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(250, 35));
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label & Input Password
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPass.setForeground(Color.GRAY);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(250, 35));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Tombol Login
        btnLogin = new JButton("MASUK");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(PRIMARY_COLOR);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Trik biar tombol lebar
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 

        // Susun Komponen ke Form Container
        formContainer.add(lblLogin);
        formContainer.add(Box.createRigidArea(new Dimension(0, 30))); // Jarak
        
        formContainer.add(lblUser);
        formContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        formContainer.add(txtUsername);
        formContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        
        formContainer.add(lblPass);
        formContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        formContainer.add(txtPassword);
        formContainer.add(Box.createRigidArea(new Dimension(0, 30)));
        
        formContainer.add(btnLogin);

        // Masukkan Container ke Panel Kanan (Tengah)
        rightPanel.add(formContainer);

        // --- GABUNGKAN ---
        add(leftPanel);
        add(rightPanel);
        
        // --- ACTION LISTENER ---
        btnLogin.addActionListener(e -> controller.prosesLogin());
        
        // Efek Hover Tombol
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(44, 62, 80)); // Lebih gelap
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(PRIMARY_COLOR);
            }
        });
    }

    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
}
