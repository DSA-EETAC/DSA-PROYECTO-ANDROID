package com.eetac.proyecto_dsa.model;

public class Grupo {
    // variables para el grupo
    private String id;
    private String nombre;

    // constructor vacio
    public Grupo() {
    }

    public Grupo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}