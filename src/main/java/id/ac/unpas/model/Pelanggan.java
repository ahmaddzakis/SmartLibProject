/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.model;

/**
 *
 * @author Fikri Lazuardi
 */
public class Pelanggan {
    private int idPelanggan;
    private String nama;
    private String noHp;
    private String alamat;

    public Pelanggan() {}

    public Pelanggan(int idPelanggan, String nama, String noHp, String alamat) {
        this.idPelanggan = idPelanggan;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    
    @Override
    public String toString() {
    return nama; // Ini agar yang muncul di ComboBox adalah Namanya
}
}
