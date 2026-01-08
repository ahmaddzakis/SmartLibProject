package id.ac.unpas.controller;

import id.ac.unpas.dao.DashboardDAO;
import id.ac.unpas.view.DashboardView;

import java.util.List;

public class DashboardController {
    private DashboardView view;
    private DashboardDAO dao;

    public DashboardController(DashboardView view) {
        this.view = view;
        this.dao = new DashboardDAO();
    }

    public void loadData() {
        // 1. Load Statistik Kartu
        int income = dao.getPemasukanHarian();
        int proses = dao.getCountStatus("proses");
        int selesai = dao.getCountStatus("selesai"); // Ambil status 'selesai'
        int diambil = dao.getCountStatus("diambil"); // Opsional: kalau mau total yg udah beres
        int pelanggan = dao.getTotalPelanggan();

        view.getLblIncome().setText("Rp " + income);
        view.getLblProses().setText(proses + " Cucian");

        // Disini kita tampilkan total yang siap diambil (selesai)
        view.getLblSelesai().setText(selesai + " Siap Ambil");

        view.getLblPelanggan().setText(pelanggan + " Orang");

        // 2. Load Tabel SEMUA DATA
        List<String[]> allTrx = dao.getAllTransactions();
        view.getTableModel().setRowCount(0); // Reset tabel
        for (String[] row : allTrx) {
            view.getTableModel().addRow(row);
        }
    }
}
