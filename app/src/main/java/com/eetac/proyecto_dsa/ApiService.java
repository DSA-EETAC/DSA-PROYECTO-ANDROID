package com.eetac.proyecto_dsa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/comprar")
    Call<Void> comprarObjeto(@Body CompraRequest request);
}

// Clase para el JSON de envío
class   CompraRequest {
    String nombreJugador;
    String nombreObjeto;
    int precio;

    public CompraRequest(String nombreJugador, String nombreObjeto, int precio) {
        this.nombreJugador = nombreJugador;
        this.nombreObjeto = nombreObjeto;
        this.precio = precio;
    }
}
