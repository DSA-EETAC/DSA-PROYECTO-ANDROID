package com.eetac.proyecto_dsa;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

public class MainActivity extends AppCompatActivity {

    private static final int UNITY_REQUEST_CODE = 1;
    private TextView tvWelcome;
    private Button btnLogout;
    private LocalUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = new LocalUserManager(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        Button btnTienda = findViewById(R.id.btnTienda);
        Button btnMochila = findViewById(R.id.btnMochila);
        Button btnJugar = findViewById(R.id.btnJugar);
        btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText("¡Bienvenido, " + userManager.getLoggedUsername() + "!");

        btnTienda.setOnClickListener(v -> {
            startActivity(new Intent(this, TiendaActivity.class));
        });

        btnMochila.setOnClickListener(v -> {
            startActivity(new Intent(this, MochilaActivity.class));
        });

        btnJugar.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setComponent(new ComponentName("dsa.JuegoMazmorras", "com.unity3d.player.UnityPlayerActivity"));
            String data = userManager.getLoggedUsername();
            i.putExtra("input", data);
            startActivityForResult(i, UNITY_REQUEST_CODE);
        });

        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UNITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String input2 = data.getStringExtra("input2");
                Toast.makeText(this, "Resultado de Unity: " + input2, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Regresaste del juego", Toast.LENGTH_SHORT).show();
            }
        }
    }
}