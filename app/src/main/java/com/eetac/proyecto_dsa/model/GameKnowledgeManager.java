package com.eetac.proyecto_dsa.model;

import java.util.ArrayList;
import java.util.List;

public class GameKnowledgeManager {
    public static List<PreguntaFrecuente> getKnowledgeBase() {
        List<PreguntaFrecuente> lista = new ArrayList<>();
        
        lista.add(new PreguntaFrecuente(
            "¿De qué trata el juego Dungeon Run?", 
            "Consiste en explorar ruinas antiguas atravesando tres fases principales: un corredor infinito, un lobby de descanso y una mazmorra final."
        ));
        
        lista.add(new PreguntaFrecuente(
            "¿Cómo puedo conseguir dinero rápido?", 
            "En la Fase 1 (Infinite Runner), el dinero siempre aparece en el carril seguro. No es infinito, tiene un final que te lleva al Lobby."
        ));
        
        lista.add(new PreguntaFrecuente(
            "¿Qué se puede hacer en el Lobby?", 
            "Es una zona de descanso donde te enteras de lo que les pasa a otros corredores NPCs. Además, en la recepción de 'Objetos Perdidos' puedes comprar equipo."
        ));
        
        lista.add(new PreguntaFrecuente(
            "¿Qué peligros y secretos hay en la Mazmorra?", 
            "Hay enemigos y NPCs con los que puedes aliarte o enemistarte. Debes romper empalizadas para avanzar y buscar tesoros en salas y pasillos donde NO haya escaleras."
        ));
        
        lista.add(new PreguntaFrecuente(
            "¿Cómo uso los botones del menú?", 
            "Usa 'Jugar' para empezar, 'Ir a la Tienda' para comprar, 'Ver Mochila' para tu inventario y 'Cerrar sesión' para salir. Yo soy un informante, no puedo pulsar los botones por ti."
        ));
        
        return lista;
    }
}
