package com.eetac.proyecto_dsa.utils;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.Grupo;
import com.eetac.proyecto_dsa.model.User;
import com.eetac.proyecto_dsa.network.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GruposActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GruposAdapter adapter;

    private LocalUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        userManager = new LocalUserManager(this);

        recyclerView = findViewById(R.id.recyclerViewGrupos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnVolver = findViewById(R.id.btnVolverMenu);
        btnVolver.setOnClickListener(v -> {
            // finish() cierra esta pantalla y te devuelve automaticamente a la MainActivity
            finish();
        });

        cargarGrupos();
    }

    private void cargarGrupos() {
        Call<List<Grupo>> call = RetrofitClient.getService().getListaGrupos();
        call.enqueue(new Callback<List<Grupo>>() {
            @Override
            public void onResponse(Call<List<Grupo>> call, Response<List<Grupo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Grupo> lista = response.body();

                    adapter = new GruposAdapter(lista, new GruposAdapter.OnItemClickListener() {
                        @Override
                        public void onUnirseClick(Grupo grupo) {
                            unirseAlGrupo(grupo);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(GruposActivity.this, "Error al cargar grupos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Grupo>> call, Throwable t) {
                Toast.makeText(GruposActivity.this, "Fallo de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unirseAlGrupo(Grupo grupo) {

        String username = userManager.getLoggedUsername();
        User usuario = new User();
        usuario.setNombre(username);

        Call<Void> call = RetrofitClient.getService().unirseAlGrupo(grupo.getId(), usuario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GruposActivity.this, "¡Te has unido a " + grupo.getNombre() + "!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GruposActivity.this, "Error al unirte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(GruposActivity.this, "Fallo de conexion al unirse", Toast.LENGTH_SHORT).show();
            }
        });
    }
}