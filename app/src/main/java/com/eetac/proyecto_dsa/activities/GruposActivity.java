package com.eetac.proyecto_dsa.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import androidx.appcompat.app.AlertDialog;
import com.eetac.proyecto_dsa.model.grupo.Grupo;
import com.eetac.proyecto_dsa.model.grupo.ListaGrupos;
import com.eetac.proyecto_dsa.model.grupo.RespuestaGrupo;
import com.eetac.proyecto_dsa.model.usuario.User;
import com.eetac.proyecto_dsa.network.RetrofitClient;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

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
        btnVolver.setOnClickListener(v -> finish());

        Button btnVerMiEquipo = findViewById(R.id.btnVerMiEquipo);
        btnVerMiEquipo.setOnClickListener(v -> verMiEquipo());

        cargarGrupos();
    }

    private void cargarGrupos() {
        RetrofitClient.getService().getListaGrupos().enqueue(new Callback<ListaGrupos>() {
            @Override
            public void onResponse(Call<ListaGrupos> call, Response<ListaGrupos> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Grupo> grupos = response.body().getGrupos();
                    if (grupos != null) {
                        adapter = new GruposAdapter(grupos, new GruposAdapter.OnItemClickListener() {
                            @Override
                            public void onUnirseClick(Grupo grupo) {
                                unirseAlGrupo(grupo);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(GruposActivity.this, "La lista de grupos está vacía", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GruposActivity.this, "Error al cargar grupos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListaGrupos> call, Throwable t) {
                Toast.makeText(GruposActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unirseAlGrupo(Grupo grupo) {
        User user = new User();
        user.setIdUsuario(userManager.getUserId());
        user.setNombre(userManager.getLoggedUsername());

        RetrofitClient.getService().unirseAlGrupo(grupo.getId(), user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GruposActivity.this, "¡Te has unido a " + grupo.getNombre() + "!", Toast.LENGTH_SHORT).show();
                } else {
                    // Si el servidor permite la unión automática, no deberíamos entrar aquí.
                    // Si sigue dando error, el backend podría requerir una lógica de "switch" interna.
                    Toast.makeText(GruposActivity.this, "Error al cambiar de grupo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(GruposActivity.this, "Fallo de conexión al unirse", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verMiEquipo() {
        String username = userManager.getLoggedUsername();
        RetrofitClient.getService().obtenerMiembrosEquipo(username).enqueue(new Callback<RespuestaGrupo>() {
            @Override
            public void onResponse(Call<RespuestaGrupo> call, Response<RespuestaGrupo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RespuestaGrupo equipo = response.body();
                    mostrarDialogoMiEquipo(equipo);
                } else {
                    Toast.makeText(GruposActivity.this, "Aún no perteneces a ningún equipo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaGrupo> call, Throwable t) {
                Toast.makeText(GruposActivity.this, "Error de red al consultar equipo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogoMiEquipo(RespuestaGrupo equipo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Compañeros:\n\n");
        
        if (equipo.getMiembros() != null) {
            for (String m : equipo.getMiembros()) {
                sb.append("👤 ").append(m).append("\n");
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Tu Equipo: " + equipo.getNombreGrupo())
                .setMessage(sb.toString())
                .setPositiveButton("Genial", null)
                .show();
    }
}
