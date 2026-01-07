/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.Model;

/**
 *
 * @author Fikri Lazuardi
 */
public class Admin {
    private int idAdmin;
    private String nama;
    private String username;
    private String password;

    // Constructor Kosong
    public Admin() {}

    // Constructor Lengkap
    public Admin(int idAdmin, String nama, String username, String password) {
        this.idAdmin = idAdmin;
        this.nama = nama;
        this.username = username;
        this.password = password;
    }

    // Getter & Setter
    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
