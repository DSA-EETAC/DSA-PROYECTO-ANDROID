package com.eetac.proyecto_dsa.model;

import java.util.List;

public class AiRequest {
    private String model;
    private String prompt;
    private boolean stream;

    public AiRequest(String model, String userPrompt, String username, List<PreguntaFrecuente> knowledgeBase) {
        this.model = model;
        
        StringBuilder kbText = new StringBuilder();
        for (PreguntaFrecuente faq : knowledgeBase) {
            kbText.append("- Pregunta: ").append(faq.getPregunta()).append("\n");
            kbText.append("  Respuesta: ").append(faq.getRespuesta()).append("\n\n");
        }

        this.prompt = "### ROL\n"
                + "Eres el Gran Sabio de las Ruinas, un asistente místico para los jugadores de 'Dungeon Run'.\n\n"
                + "### CONTEXTO DEL JUGADOR\n"
                + "- Nombre del héroe: " + username + "\n\n"
                + "### CONOCIMIENTO DINÁMICO (Base de Datos)\n"
                + "Utiliza la siguiente información para responder si el jugador pregunta sobre el juego:\n"
                + kbText
                + "### REGLAS DE RESPUESTA\n"
                + "1. Responde ÚNICAMENTE con un objeto JSON válido.\n"
                + "2. No incluyas explicaciones, saludos ni código Markdown fuera del JSON.\n"
                + "3. Estructura obligatoria:\n"
                + "{\n"
                + "  \"respuesta\": \"Texto de tu respuesta mística aquí\",\n"
                + "  \"preguntas_frecuentes\": [\"Pregunta corta 1\", \"Pregunta corta 2\", \"Pregunta corta 3\"]\n"
                + "}\n\n"
                + "### MENSAJE DEL JUGADOR:\n" + userPrompt;
        this.stream = false;
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public boolean isStream() { return stream; }
    public void setStream(boolean stream) { this.stream = stream; }
}
