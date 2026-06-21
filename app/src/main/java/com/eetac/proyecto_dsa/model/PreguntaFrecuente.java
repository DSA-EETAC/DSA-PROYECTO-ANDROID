package com.eetac.proyecto_dsa.model;

public class PreguntaFrecuente {
    private String pregunta;
    private String respuesta;

    public PreguntaFrecuente(String pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getPregunta() { return pregunta; }
    public String getRespuesta() { return respuesta; }
}
