/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.model;

/**
 *
 * @author Fikri Lazuardi
 */
public class Layanan {
    private int idLayanan;
    private String namaLayanan;
    private int hargaPerKg;
    private int estimasiHari;

    public Layanan() {}

    public Layanan(int idLayanan, String namaLayanan, int hargaPerKg, int estimasiHari) {
        this.idLayanan = idLayanan;
        this.namaLayanan = namaLayanan;
        this.hargaPerKg = hargaPerKg;
        this.estimasiHari = estimasiHari;
    }
    
    // Override toString agar saat masuk ComboBox yang muncul Namanya, bukan alamat memori
    @Override
    public String toString() {
        return namaLayanan; 
    }

    public int getIdLayanan() { return idLayanan; }
    public void setIdLayanan(int idLayanan) { this.idLayanan = idLayanan; }

    public String getNamaLayanan() { return namaLayanan; }
    public void setNamaLayanan(String namaLayanan) { this.namaLayanan = namaLayanan; }

    public int getHargaPerKg() { return hargaPerKg; }
    public void setHargaPerKg(int hargaPerKg) { this.hargaPerKg = hargaPerKg; }

    public int getEstimasiHari() { return estimasiHari; }
    public void setEstimasiHari(int estimasiHari) { this.estimasiHari = estimasiHari; }
}
