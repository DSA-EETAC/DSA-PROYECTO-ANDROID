package com.eetac.proyecto_dsa.model.tienda;

public class PeticionCompra {
    private String nombreJugador;
    private String nombreObjeto;
    private int precio;

    public PeticionCompra() {}

    public PeticionCompra(String nombreJugador, String nombreObjeto, int precio) {
        this.nombreJugador = nombreJugador;
        this.nombreObjeto = nombreObjeto;
        this.precio = precio;
    }

    public String getNombreJugador() { return nombreJugador; }
    public void setNombreJugador(String nombreJugador) { this.nombreJugador = nombreJugador; }

    public String getNombreObjeto() { return nombreObjeto; }
    public void setNombreObjeto(String nombreObjeto) { this.nombreObjeto = nombreObjeto; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }
}
