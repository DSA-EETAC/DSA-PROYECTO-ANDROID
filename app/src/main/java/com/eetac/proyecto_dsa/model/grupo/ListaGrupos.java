package com.eetac.proyecto_dsa.model.grupo;

import java.util.List;

public class ListaGrupos {
    private List<Grupo> grupos;

    public ListaGrupos() {}

    public ListaGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
}
