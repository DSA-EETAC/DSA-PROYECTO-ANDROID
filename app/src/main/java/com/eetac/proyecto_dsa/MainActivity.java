package com.eetac.proyecto_dsa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private LocalUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = new LocalUserManager(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText("¡Bienvenido, " + userManager.getLoggedUsername() + "!");

        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}