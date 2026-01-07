/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.model;

/**
 *
 * @author Fikri Lazuardi
 */

import java.util.Date; // Gunakan util.Date untuk umum

public class Pembayaran {
    private int idPembayaran;
    private int idTransaksi; // Foreign Key ke tabel Transaksi
    private String metode;   // 'cash', 'transfer', 'e-wallet'
    private int jumlahBayar;
    private String statusBayar; // 'belum', 'lunas'
    private Date tglBayar;

    // Helper Field (Tidak ada di tabel pembayaran, tapi berguna buat tampil di GUI)
    private String kodeNota; 

    // Constructor Kosong
    public Pembayaran() {}

    // Constructor Lengkap
    public Pembayaran(int idPembayaran, int idTransaksi, String metode, int jumlahBayar, String statusBayar, Date tglBayar) {
        this.idPembayaran = idPembayaran;
        this.idTransaksi = idTransaksi;
        this.metode = metode;
        this.jumlahBayar = jumlahBayar;
        this.statusBayar = statusBayar;
        this.tglBayar = tglBayar;
    }

    // --- Getter & Setter ---

    public int getIdPembayaran() { return idPembayaran; }
    public void setIdPembayaran(int idPembayaran) { this.idPembayaran = idPembayaran; }

    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public String getMetode() { return metode; }
    public void setMetode(String metode) { this.metode = metode; }

    public int getJumlahBayar() { return jumlahBayar; }
    public void setJumlahBayar(int jumlahBayar) { this.jumlahBayar = jumlahBayar; }

    public String getStatusBayar() { return statusBayar; }
    public void setStatusBayar(String statusBayar) { this.statusBayar = statusBayar; }

    public Date getTglBayar() { return tglBayar; }
    public void setTglBayar(Date tglBayar) { this.tglBayar = tglBayar; }

    // Getter Setter Helper
    public String getKodeNota() { return kodeNota; }
    public void setKodeNota(String kodeNota) { this.kodeNota = kodeNota; }
}
