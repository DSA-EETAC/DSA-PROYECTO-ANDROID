package com.eetac.proyecto_dsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.usuario.InventarioJugador;
import com.eetac.proyecto_dsa.model.usuario.PartidaFin;
import com.eetac.proyecto_dsa.model.tienda.Item;
import com.eetac.proyecto_dsa.model.tienda.TiendaJuego;
import com.eetac.proyecto_dsa.network.RetrofitClient;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreJuegoActivity extends AppCompatActivity {

    private LocalUserManager userManager;
    private LinearLayout containerSeleccion;
    private List<String> objetosSeleccionados = new ArrayList<>();
    
    // MEMORIA: Mapa para guardar los IDs reales que vienen de la tienda
    private static Map<String, Integer> mapaIdsObjetos = new HashMap<>();
    private static boolean tiendaCargada = false;

    private final int MAX_OBJETOS = 4;
    private final int CODIGO_JUEGO = 100;
    private Button btnEntrar;
    private int monedasPendientesAlCerrar = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_juego);

        userManager = new LocalUserManager(this);
        containerSeleccion = findViewById(R.id.containerSeleccion);
        btnEntrar = findViewById(R.id.btnEntrarMazmorra);
        Button btnVolver = findViewById(R.id.btnVolverPreJuego);

        btnEntrar.setEnabled(false);
        btnEntrar.setText("SINCRONIZANDO...");

        if (savedInstanceState != null) {
            objetosSeleccionados = savedInstanceState.getStringArrayList("backup_objetos");
        }

        cargarTienda();

        btnVolver.setOnClickListener(v -> finish());
        btnEntrar.setOnClickListener(v -> entrarALaMazmorra());
    }

    private void cargarTienda() {
        tiendaCargada = false;
        RetrofitClient.getService().getTienda().enqueue(new Callback<TiendaJuego>() {
            @Override
            public void onResponse(Call<TiendaJuego> call, Response<TiendaJuego> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mapaIdsObjetos.clear();
                    for (Item item : response.body().getItems()) {
                        // USAMOS EL ID QUE VIENE EN EL JSON
                        mapaIdsObjetos.put(item.getNombre(), item.getId());
                        android.util.Log.d("DUNGEON_DEBUG", "Objeto tienda: " + item.getNombre() + " -> ID: " + item.getId());
                    }
                    tiendaCargada = true;
                }
                
                if (monedasPendientesAlCerrar != -1) {
                    enviarFinDePartida(monedasPendientesAlCerrar);
                    monedasPendientesAlCerrar = -1;
                }
                cargarInventario();
            }

            @Override
            public void onFailure(Call<TiendaJuego> call, Throwable t) {
                tiendaCargada = true; 
                cargarInventario();
            }
        });
    }

    private void cargarInventario() {
        btnEntrar.setText("CARGANDO MOCHILA...");
        String username = userManager.getLoggedUsername();
        RetrofitClient.getService().getInventario(username).enqueue(new Callback<InventarioJugador>() {
            @Override
            public void onResponse(Call<InventarioJugador> call, Response<InventarioJugador> response) {
                btnEntrar.setEnabled(true);
                btnEntrar.setText("ENTRAR A LA MAZMORRA");
                if (response.isSuccessful() && response.body() != null) {
                    mostrarObjetosParaSeleccion(response.body().getObjetos());
                } else {
                    Toast.makeText(PreJuegoActivity.this, "Error al cargar mochila", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<InventarioJugador> call, Throwable t) {
                btnEntrar.setEnabled(true);
                btnEntrar.setText("ENTRAR A LA MAZMORRA");
                Toast.makeText(PreJuegoActivity.this, "Sin conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarObjetosParaSeleccion(List<String> objetos) {
        containerSeleccion.removeAllViews();
        if (objetos == null || objetos.isEmpty()) {
            TextView tvVacio = new TextView(this);
            tvVacio.setText("Mochila vacía.");
            tvVacio.setTextColor(getResources().getColor(R.color.dungeon_text_label));
            tvVacio.setGravity(android.view.Gravity.CENTER);
            containerSeleccion.addView(tvVacio);
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        for (String objeto : objetos) {
            View itemView = inflater.inflate(R.layout.item_seleccion_mochila, containerSeleccion, false);
            CardView card = itemView.findViewById(R.id.cardItemSeleccion);
            TextView tvNombre = itemView.findViewById(R.id.tvNombreItemSeleccion);
            TextView tvEstado = itemView.findViewById(R.id.tvEstadoSeleccion);

            tvNombre.setText("⚔️ " + objeto);
            if (objetosSeleccionados.contains(objeto)) {
                tvEstado.setText("[X]");
                card.setCardBackgroundColor(getResources().getColor(R.color.dungeon_gold_dark));
            }

            itemView.setOnClickListener(v -> {
                if (objetosSeleccionados.contains(objeto)) {
                    objetosSeleccionados.remove(objeto);
                    tvEstado.setText("[ ]");
                    card.setCardBackgroundColor(getResources().getColor(R.color.dungeon_card));
                } else if (objetosSeleccionados.size() < MAX_OBJETOS) {
                    objetosSeleccionados.add(objeto);
                    tvEstado.setText("[X]");
                    card.setCardBackgroundColor(getResources().getColor(R.color.dungeon_gold_dark));
                } else {
                    Toast.makeText(this, "Máximo 4 objetos", Toast.LENGTH_SHORT).show();
                }
            });
            containerSeleccion.addView(itemView);
        }
    }

    private void entrarALaMazmorra() {
        String packageJuego = "dsa.JuegoMazmorras";
        Intent i = getPackageManager().getLaunchIntentForPackage(packageJuego);
        if (i == null) {
            Toast.makeText(this, "⚠ Juego no instalado", Toast.LENGTH_LONG).show();
            return;
        }
        i.setFlags(0); 
        i.putExtra("USERNAME", userManager.getLoggedUsername());
        i.putExtra("MONEY", 0); 
        i.putExtra("ITEMS", TextUtils.join(",", objetosSeleccionados));
        startActivityForResult(i, CODIGO_JUEGO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_JUEGO) {
            int monedas = 0;
            if (data != null) {
                monedas = data.getIntExtra("MONEY", 0);
            }
            if (!tiendaCargada) {
                monedasPendientesAlCerrar = monedas;
            } else {
                enviarFinDePartida(monedas);
            }
        }
    }

    private void enviarFinDePartida(int monedasNuevas) {
        List<Integer> idsConsumidos = new ArrayList<>();
        for (String nombre : objetosSeleccionados) {
            Integer id = mapaIdsObjetos.get(nombre);
            if (id != null) {
                idsConsumidos.add(id);
            }
        }

        android.util.Log.d("DUNGEON_DEBUG", "Finalizando Partida -> Monedas: " + monedasNuevas + " | IDs consumidos: " + idsConsumidos);

        PartidaFin partida = new PartidaFin(userManager.getLoggedUsername(), monedasNuevas, idsConsumidos);

        RetrofitClient.getService().finalizarPartida(partida).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    int total = userManager.getCoins() + monedasNuevas;
                    userManager.updateCoins(total);
                    Toast.makeText(PreJuegoActivity.this, "¡Victoria! Ganaste " + monedasNuevas + " monedas.", Toast.LENGTH_LONG).show();
                    finish(); 
                } else {
                    Toast.makeText(PreJuegoActivity.this, "Error al sincronizar partida", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PreJuegoActivity.this, "Error de red al finalizar", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("backup_objetos", new ArrayList<>(objetosSeleccionados));
    }
}
