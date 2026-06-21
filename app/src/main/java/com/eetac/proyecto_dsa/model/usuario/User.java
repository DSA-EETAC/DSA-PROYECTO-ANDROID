package com.eetac.proyecto_dsa.model.usuario;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName(value = "idUsuario", alternate = {"id"})
    private int idUsuario;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("password")
    private String password;

    @SerializedName("mail")
    private String mail;

    @SerializedName("monedas")
    private int monedas;

    public User() {}

    // Para registro (sin ID ni monedas todavía)
    public User(String nombre, String password, String mail) {
        this.nombre = nombre;
        this.password = password;
        this.mail = mail;
        this.monedas = 0;
    }

    // Constructor completo (con ID y monedas)
    public User(int idUsuario, String nombre, String password, String mail, int monedas) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.password = password;
        this.mail = mail;
        this.monedas = monedas;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public int getMonedas() { return monedas; }
    public void setMonedas(int monedas) { this.monedas = monedas; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}
