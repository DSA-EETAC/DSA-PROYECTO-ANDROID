package com.eetac.proyecto_dsa.model.grupo;

public class Grupo {
    // variables para el grupo
    private int id;
    private String nombre;

    // constructor vacio
    public Grupo() {
    }

    public Grupo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}