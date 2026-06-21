package com.eetac.proyecto_dsa.network;

import com.eetac.proyecto_dsa.model.grupo.Grupo;
import com.eetac.proyecto_dsa.model.grupo.ListaGrupos;
import com.eetac.proyecto_dsa.model.grupo.RespuestaGrupo;
import com.eetac.proyecto_dsa.model.usuario.InventarioJugador;
import com.eetac.proyecto_dsa.model.tienda.PeticionCompra;
import com.eetac.proyecto_dsa.model.tienda.TiendaJuego;
import com.eetac.proyecto_dsa.model.usuario.User;
import com.eetac.proyecto_dsa.model.evento.Evento;
import com.eetac.proyecto_dsa.model.evento.InscripcionRequest;

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
    Call<InventarioJugador> getInventario(@Path("nombre") String nombreUsuario);

    // GET /api/juego/tienda
    @GET("juego/tienda")
    Call<TiendaJuego> getTienda();

    // GET /api/juego/grupos
    @GET("juego/grupos")
    Call<ListaGrupos> getListaGrupos();

    // POST /api/juego/grupos/{idGrupo}/unirse
    @POST("juego/grupos/{idGrupo}/unirse")
    Call<Void> unirseAlGrupo(@Path("idGrupo") int idGrupo, @Body User usuario);

    // Petición para obtener los miembros del equipo al que pertenece el usuario
    @GET("juego/usuarios/{nombre}/grupo")
    Call<RespuestaGrupo> obtenerMiembrosEquipo(@Path("nombre") String nombreUsuario);

    // GET /api/juego/eventos
    @GET("juego/eventos")
    Call<List<Evento>> getEventos();

    // POST /api/juego/eventos/inscripcion
    @POST("juego/eventos/inscripcion")
    Call<Void> inscribirseEvento(@Body InscripcionRequest request);

    // GET /api/juego/eventos/{idEvento}/usuarios (EJ5 - Izan)
    @GET("juego/eventos/{idEvento}/usuarios")
    Call<List<User>> getUsuariosEvento(@Path("idEvento") String idEvento);
}
