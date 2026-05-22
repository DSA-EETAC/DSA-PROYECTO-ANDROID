package com.eetac.proyecto_dsa.model;

import com.google.gson.annotations.SerializedName;

public class PeticionCompra {

    @SerializedName("nombreJugador")
    private String nombreJugador;

    @SerializedName("nombreObjeto")
    private String nombreObjeto;

    public PeticionCompra(String nombreJugador, String nombreObjeto) {
        this.nombreJugador = nombreJugador;
        this.nombreObjeto  = nombreObjeto;
    }

    public String getNombreJugador() { return nombreJugador; }
    public String getNombreObjeto()  { return nombreObjeto; }
}