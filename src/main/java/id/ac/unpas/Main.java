package id.ac.unpas;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // 1. OPSI: Cek Koneksi Database Dulu (Buat Debugging)
        if (Database.getConnection() != null) {
            System.out.println("Aplikasi Siap: Database Terhubung!");
        } else {
            System.out.println("Aplikasi Error: Database Gagal!");
        }

        // 2. OPSI: Ganti Tampilan Biar Gak Kaku (Pakai Nimbus LookAndFeel)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Kalau gagal, pakai default aja (gak masalah)
        }

        // 3. JALANKAN APLIKASI (Buka LoginView)
        SwingUtilities.invokeLater(() -> {
            // Kita panggil LoginView sebagai pintu masuk
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}