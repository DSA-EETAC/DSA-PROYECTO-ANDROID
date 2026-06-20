package com.eetac.proyecto_dsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.evento.Evento;
import com.eetac.proyecto_dsa.network.ApiService;
import com.eetac.proyecto_dsa.network.RetrofitClient;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventosActivity extends AppCompatActivity implements EventoAdapter.OnVerUsuariosListener {

    private RecyclerView recyclerView;
    private EventoAdapter adapter;
    private LocalUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        userManager = new LocalUserManager(this);
        recyclerView = findViewById(R.id.recyclerEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarEventos();
    }

    private void cargarEventos() {
        ApiService api = RetrofitClient.getService();
        api.getEventos().enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new EventoAdapter(
                            response.body(),
                            userManager.getLoggedUsername(),
                            EventosActivity.this,
                            EventosActivity.this
                    );
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(EventosActivity.this,
                            "Error al cargar eventos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(EventosActivity.this,
                        "Error de xarxa: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVerUsuarios(String idEvento) {
        Intent intent = new Intent(this, UsuariosEventoActivity.class);
        intent.putExtra("idEvento", idEvento);
        startActivity(intent);
    }
}