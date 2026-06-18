package com.eetac.proyecto_dsa.model.grupo;

import java.util.List;

public class RespuestaGrupo {

    private String grupo;
    private List<MiembroGrupo> miembros;

    // Constructor vacío
    public RespuestaGrupo() {
    }

    public RespuestaGrupo(String grupo, List<MiembroGrupo> miembros) {
        this.grupo = grupo;
        this.miembros = miembros;
    }

    // Getters y Setters
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<MiembroGrupo> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroGrupo> miembros) {
        this.miembros = miembros;
    }
}
