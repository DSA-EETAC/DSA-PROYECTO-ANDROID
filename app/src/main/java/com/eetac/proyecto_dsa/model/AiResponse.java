package com.eetac.proyecto_dsa.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AiResponse {
    @SerializedName("respuesta")
    private String response;
    
    @SerializedName("preguntas_frecuentes")
    private List<String> preguntasFrecuentes;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getPreguntasFrecuentes() {
        return preguntasFrecuentes;
    }

    public void setPreguntasFrecuentes(List<String> preguntasFrecuentes) {
        this.preguntasFrecuentes = preguntasFrecuentes;
    }
}
