package com.eetac.proyecto_dsa.model.usuario;

import java.util.List;

public class PartidaFin {
    private String username;
    private int monedasGanadas;
    private List<Integer> objetosConsumidos;

    public PartidaFin(String username, int monedasGanadas, List<Integer> objetosConsumidos) {
        this.username = username;
        this.monedasGanadas = monedasGanadas;
        this.objetosConsumidos = objetosConsumidos;
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getMonedasGanadas() { return monedasGanadas; }
    public void setMonedasGanadas(int monedasGanadas) { this.monedasGanadas = monedasGanadas; }

    public List<Integer> getObjetosConsumidos() { return objetosConsumidos; }
    public void setObjetosConsumidos(List<Integer> objetosConsumidos) { this.objetosConsumidos = objetosConsumidos; }
}
