package com.eetac.proyecto_dsa.model.tienda;

public class PeticionCompra {
    private int idUsuario;
    private String nombreObjeto;
    private int precio;

    // Constructor vacío (OBLIGATORIO para que el JSON no explote)
    public PeticionCompra() {}

    public PeticionCompra(int idUsuario, String nombreObjeto, int precio) {
        this.idUsuario = idUsuario;
        this.nombreObjeto = nombreObjeto;
        this.precio = precio;
    }

    // Getters
    public int getIdUsuario() { return idUsuario; }
    public String getNombreObjeto() { return nombreObjeto; }
    public int getPrecio() { return precio; }

    // Setters
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setNombreObjeto(String nombreObjeto) { this.nombreObjeto = nombreObjeto; }
    public void setPrecio(int precio) { this.precio = precio; }
}
