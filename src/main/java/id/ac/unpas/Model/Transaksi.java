/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.Model;

/**
 *
 * @author Fikri Lazuardi
 */

import java.util.Date; // Gunakan util.Date untuk umum

public class Transaksi {
    private int idTransaksi;
    private String kodeNota;
    private int idPelanggan; // Kita simpan ID-nya saja untuk database
    private int idLayanan;
    private int idAdmin;
    private double berat;
    private int totalHarga;
    private String status; // 'antri', 'proses', 'selesai', 'diambil'
    private Date tglMasuk;
    private Date tglSelesai;
    
    // Helper fields (Optional: untuk ditampilkan di Tabel GUI biar gampang)
    private String namaPelanggan;
    private String namaLayanan;

    public Transaksi() {}

    // Getter Setter Standar
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public String getKodeNota() { return kodeNota; }
    public void setKodeNota(String kodeNota) { this.kodeNota = kodeNota; }

    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }

    public int getIdLayanan() { return idLayanan; }
    public void setIdLayanan(int idLayanan) { this.idLayanan = idLayanan; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public double getBerat() { return berat; }
    public void setBerat(double berat) { this.berat = berat; }

    public int getTotalHarga() { return totalHarga; }
    public void setTotalHarga(int totalHarga) { this.totalHarga = totalHarga; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getTglMasuk() { return tglMasuk; }
    public void setTglMasuk(Date tglMasuk) { this.tglMasuk = tglMasuk; }

    public Date getTglSelesai() { return tglSelesai; }
    public void setTglSelesai(Date tglSelesai) { this.tglSelesai = tglSelesai; }

    // Getter Setter Helper
    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }

    public String getNamaLayanan() { return namaLayanan; }
    public void setNamaLayanan(String namaLayanan) { this.namaLayanan = namaLayanan; }
}
