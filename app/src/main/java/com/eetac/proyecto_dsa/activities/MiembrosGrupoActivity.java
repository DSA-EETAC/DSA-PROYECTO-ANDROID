package com.eetac.proyecto_dsa.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.grupo.RespuestaGrupo;
import com.eetac.proyecto_dsa.network.RetrofitClient;
import com.eetac.proyecto_dsa.network.ApiService;
import com.eetac.proyecto_dsa.utils.LocalUserManager;


import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiembrosGrupoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textTitulo;
    private MiembrosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miembros_equipo);

        // 1. Enlazamos las vistas del XML
        recyclerView = findViewById(R.id.recyclerViewMiembros);
        textTitulo = findViewById(R.id.textTituloEquipo);

        // 2. Configuramos el RecyclerView para que sea una lista vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Llamamos a la API
        obtenerDatosDelServidor();
    }

    private void obtenerDatosDelServidor() {
        // Obtenemos el nombre del usuario logeado desde LocalUserManager
        LocalUserManager userManager = new LocalUserManager(this);
        String nombreUsuarioActual = userManager.getLoggedUsername();

        ApiService apiService = RetrofitClient.getService();
        Call<RespuestaGrupo> call = apiService.obtenerMiembrosEquipo(nombreUsuarioActual);

        call.enqueue(new Callback<RespuestaGrupo>() {
            @Override
            public void onResponse(Call<RespuestaGrupo> call, Response<RespuestaGrupo> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // Extraemos los datos del envoltorio
                    String nombreDelEquipo = response.body().getNombreGrupo();
                    List<String> lista = response.body().getMiembros();

                    // Actualizamos el título
                    textTitulo.setText("Equipo: " + nombreDelEquipo);

                    // Pasamos la lista al adaptador y lo conectamos
                    adapter = new MiembrosAdapter(lista);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(MiembrosGrupoActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaGrupo> call, Throwable t) {
                Toast.makeText(MiembrosGrupoActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
