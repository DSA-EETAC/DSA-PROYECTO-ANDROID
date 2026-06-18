package com.eetac.proyecto_dsa.model.grupo;

public class MiembroGrupo {

    private String nombre;
    private String mail;
    private int monedas;

    // 2. Constructor vacío
    public MiembroGrupo() {
    }

    // 3. Constructor con parámetros
    public MiembroGrupo(String nombre, String mail, int monedas) {
        this.nombre = nombre;
        this.mail = mail;
        this.monedas = monedas;
    }

    // 4. Getters y Setters para poder leer y modificar los datos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }
}
