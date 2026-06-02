package com.eetac.proyecto_dsa.network;

import com.eetac.proyecto_dsa.model.PeticionCompra;
import com.eetac.proyecto_dsa.model.User;
import com.eetac.proyecto_dsa.model.Grupo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // POST /api/juego/login
    @POST("juego/login")
    Call<User> login(@Body User credenciales);

    // POST /api/juego/registro
    @POST("juego/registro")
    Call<User> registro(@Body User nuevoUsuario);

    // POST /api/juego/comprar
    @POST("juego/comprar")
    Call<Void> comprar(@Body PeticionCompra peticion);

    // GET /api/juego/inventario/{nombre}
    @GET("juego/inventario/{nombre}")
    Call<List<String>> getInventario(@Path("nombre") String nombre);

    // T2: Pedir grupos (Apunta a .../api/juego/grupos)
    @GET("juego/grupos")
    Call<List<Grupo>> getListaGrupos();

    // T3: Unirse a un grupo (Apunta a .../api/juego/grupos/{id}/unirse)
    @POST("juego/grupos/{id}/unirse")
    Call<Void> unirseAlGrupo(@Path("id") String idGrupo, @Body User usuario);
}