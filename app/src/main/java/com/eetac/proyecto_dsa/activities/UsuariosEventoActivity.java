package com.eetac.proyecto_dsa.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.usuario.User;
import com.eetac.proyecto_dsa.network.ApiService;
import com.eetac.proyecto_dsa.network.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosEventoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuariosEventoAdapter adapter;
    private String idEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_evento);

        idEvento = getIntent().getStringExtra("idEvento");

        recyclerView = findViewById(R.id.recyclerUsuariosEvento);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        ApiService api = RetrofitClient.getService();
        api.getUsuariosEvento(idEvento).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new UsuariosEventoAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(UsuariosEventoActivity.this,
                            "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(UsuariosEventoActivity.this,
                        "Error de xarxa: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}