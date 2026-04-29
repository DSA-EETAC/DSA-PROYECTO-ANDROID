package com.eetac.proyecto_dsa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;
    private LocalUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userManager = new LocalUserManager(this);

        if (userManager.isLoggedIn()) {
            goToMain();
            return;
        }

        etEmail        = findViewById(R.id.etEmail);
        etPassword     = findViewById(R.id.etPassword);
        btnLogin       = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        btnLogin.setOnClickListener(v -> {
            String email    = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "⚠ Rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // ← Aquí luego va la llamada a la API
            if (userManager.login(email, password)) {
                userManager.saveSession(email);
                Toast.makeText(this, "¡Bienvenido, héroe!", Toast.LENGTH_SHORT).show();
                goToMain();
            } else {
                Toast.makeText(this, "⚠ Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        tvGoToRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}