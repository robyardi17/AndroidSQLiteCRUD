package com.example.loginform.Model;

public class ModelUser {

    private String Username;
    private String Password;
    private String Nomor;
    private String Alamat;
    private Integer id;

    public ModelUser(String Username, String Password, String Alamat, String Nomor, Integer ID){
        this.Username = Username;
        this.Password = Password;
        this.Alamat = Alamat;
        this.Nomor = Nomor;
        this.id = ID;
    }

    public ModelUser() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNomor() {
        return Nomor;
    }

    public void setNomor(String nomor) {
        Nomor = nomor;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

}
