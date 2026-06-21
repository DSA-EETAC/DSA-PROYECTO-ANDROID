package com.eetac.proyecto_dsa.model.grupo;

import java.util.List;

public class RespuestaGrupo {

    private String nombreGrupo;
    private List<String> miembros;
    private boolean tieneGrupo;

    // Constructor vacío
    public RespuestaGrupo() {
    }

    public RespuestaGrupo(String nombreGrupo, List<String> miembros, boolean tieneGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.miembros = miembros;
        this.tieneGrupo = tieneGrupo;
    }

    // Getters y Setters
    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public List<String> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<String> miembros) {
        this.miembros = miembros;
    }

    public boolean isTieneGrupo() {
        return tieneGrupo;
    }

    public void setTieneGrupo(boolean tieneGrupo) {
        this.tieneGrupo = tieneGrupo;
    }
}
