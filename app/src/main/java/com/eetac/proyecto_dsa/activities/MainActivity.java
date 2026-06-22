package com.eetac.proyecto_dsa.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eetac.proyecto_dsa.R;

import com.eetac.proyecto_dsa.model.AiRequest;
import com.eetac.proyecto_dsa.model.AiResponse;
import com.eetac.proyecto_dsa.model.GameKnowledgeManager;
import com.eetac.proyecto_dsa.model.AiResponse;
import com.eetac.proyecto_dsa.network.RetrofitClient;
import com.eetac.proyecto_dsa.utils.LocalUserManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private LocalUserManager userManager;

    private final ActivityResultLauncher<Intent> unityLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            String input2 = result.getData().getStringExtra("input2");
                            Toast.makeText(this, "Resultado de Unity: " + input2, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Regresaste del juego", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = new LocalUserManager(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        Button btnTienda  = findViewById(R.id.btnTienda);
        Button btnMochila = findViewById(R.id.btnMochila);
        Button btnJugar   = findViewById(R.id.btnJugar);
        Button btnGrupos  = findViewById(R.id.btnGrupos);
        Button btnEventos = findViewById(R.id.btnEventos);
        btnLogout         = findViewById(R.id.btnLogout);

        FloatingActionButton fab = findViewById(R.id.fabMensaje);
        LinearLayout containerMensaje = findViewById(R.id.containerMensaje);
        EditText etMensaje = findViewById(R.id.etMensajeKingdom);
        Button btnEnviar = findViewById(R.id.btnEnviarMensaje);

        tvWelcome.setText("¡Bienvenido, " + userManager.getLoggedUsername() + "!");
        actualizarFAQ(Arrays.asList("Hola"));

        btnTienda.setOnClickListener(v ->
                startActivity(new Intent(this, TiendaActivity.class))
        );

        btnMochila.setOnClickListener(v ->
                startActivity(new Intent(this, MochilaActivity.class))
        );

        btnGrupos.setOnClickListener(v ->
                startActivity(new Intent(this, GruposActivity.class))
        );

        btnEventos.setOnClickListener(v ->
                startActivity(new Intent(this, EventosActivity.class))
        );

        btnJugar.setOnClickListener(v ->
                startActivity(new Intent(this, PreJuegoActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        fab.setOnClickListener(v -> {
            if (containerMensaje.getVisibility() == View.GONE) {
                containerMensaje.setVisibility(View.VISIBLE);
            } else {
                containerMensaje.setVisibility(View.GONE);
            }
        });

        btnEnviar.setOnClickListener(v -> {
            String msg = etMensaje.getText().toString().trim();
            if (!msg.isEmpty()) {
                enviarMensajeIA(msg);
            } else {
                Toast.makeText(this, "Escribe algo primero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarMensajeIA(String msg) {
        EditText etMensaje = findViewById(R.id.etMensajeKingdom);

        limpiarSugerencias();
        añadirMensajeAlChat(msg, true);
        etMensaje.setText("");

        AiRequest aiReq = new AiRequest(
                "qwen2.5:14b",
                msg,
                userManager.getLoggedUsername(),
                GameKnowledgeManager.getKnowledgeBase()
        );
        String urlIA = "http://10.4.119.50:8080/api/generate";

        RetrofitClient.getService().preguntarIA(urlIA, aiReq).enqueue(new Callback<AiResponse>() {
            @Override
            public void onResponse(@NonNull Call<AiResponse> call, @NonNull retrofit2.Response<AiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String resultText = response.body().getResponse();
                    runOnUiThread(() -> {
                        try {
                            String innerText = resultText.trim();
                            if (innerText.contains("{")) {
                                innerText = innerText.substring(innerText.indexOf("{"), innerText.lastIndexOf("}") + 1);
                            }

                            AiResponse parsed = new Gson().fromJson(innerText, AiResponse.class);

                            if (parsed != null && parsed.getResponse() != null) {
                                añadirMensajeAlChat(parsed.getResponse(), false);
                                actualizarFAQ(parsed.getPreguntasFrecuentes());
                            } else {
                                añadirMensajeAlChat(resultText, false);
                                actualizarFAQ(Arrays.asList("Hola"));
                            }
                        } catch (Exception e) {
                            añadirMensajeAlChat(resultText, false);
                            actualizarFAQ(Arrays.asList("Hola"));
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        añadirMensajeAlChat("Error del servidor: " + response.code(), false);
                        actualizarFAQ(Arrays.asList("Hola"));
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<AiResponse> call, @NonNull Throwable t) {
                runOnUiThread(() -> {
                    añadirMensajeAlChat("Error de red: " + t.getMessage(), false);
                    actualizarFAQ(Arrays.asList("Hola"));
                });
            }
        });
    }

    private void limpiarSugerencias() {
        LinearLayout layoutHistorial = findViewById(R.id.layoutHistorialMensajes);
        for (int i = layoutHistorial.getChildCount() - 1; i >= 0; i--) {
            View child = layoutHistorial.getChildAt(i);
            if ("suggestion".equals(child.getTag())) {
                layoutHistorial.removeViewAt(i);
            }
        }
    }

    private void actualizarFAQ(List<String> faqs) {
        LinearLayout layoutHistorial = findViewById(R.id.layoutHistorialMensajes);
        ScrollView scrollChat = findViewById(R.id.scrollChat);

        if (faqs == null || faqs.isEmpty()) return;

        int count = 0;
        for (String q : faqs) {
            if (count >= 3) break;

            Button btnFaq = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 10);
            params.gravity = android.view.Gravity.CENTER_HORIZONTAL;

            btnFaq.setLayoutParams(params);
            btnFaq.setBackgroundResource(R.drawable.bg_message_suggestion);
            btnFaq.setText("¿" + q + "?");
            btnFaq.setTextColor(getResources().getColor(R.color.dungeon_text_label));
            btnFaq.setTextSize(11);
            btnFaq.setAllCaps(false);
            btnFaq.setPadding(30, 15, 30, 15);
            btnFaq.setTag("suggestion");

            btnFaq.setOnClickListener(v -> enviarMensajeIA(q));
            layoutHistorial.addView(btnFaq);
            count++;
        }
        scrollChat.post(() -> scrollChat.fullScroll(View.FOCUS_DOWN));
    }

    private void añadirMensajeAlChat(String texto, boolean esMio) {
        LinearLayout layoutHistorial = findViewById(R.id.layoutHistorialMensajes);
        ScrollView scrollChat = findViewById(R.id.scrollChat);
        View messageView = getLayoutInflater().inflate(R.layout.item_message, null);
        TextView tvText = messageView.findViewById(R.id.tvMessageText);
        LinearLayout bubble = messageView.findViewById(R.id.message_bubble);

        tvText.setText(texto);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (esMio) {
            params.gravity = android.view.Gravity.END;
            bubble.setBackgroundResource(R.drawable.bg_message_me);
            tvText.setTextColor(getResources().getColor(R.color.dungeon_bg));
        } else {
            params.gravity = android.view.Gravity.START;
            bubble.setBackgroundResource(R.drawable.bg_message_other);
            tvText.setTextColor(getResources().getColor(R.color.dungeon_text_light));
        }

        bubble.setLayoutParams(params);
        layoutHistorial.addView(messageView);
        scrollChat.post(() -> scrollChat.fullScroll(View.FOCUS_DOWN));
    }
}
