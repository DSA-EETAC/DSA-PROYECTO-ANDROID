package com.eetac.proyecto_dsa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.eetac.proyecto_dsa.model.User;
import com.eetac.proyecto_dsa.network.RetrofitClient;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            String nombre   = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();


            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "⚠ Rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamada a la API con Retrofit
            User credenciales = new User(nombre, password, null);

            RetrofitClient.getService().login(credenciales).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        userManager.saveSession(response.body().getNombre());
                        Toast.makeText(LoginActivity.this,
                                "¡Bienvenido, " + response.body().getNombre() + "!",
                                Toast.LENGTH_SHORT).show();
                        goToMain();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "⚠ Usuario o contraseña incorrectos",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,
                            "⚠ Sin conexión con el servidor",
                            Toast.LENGTH_SHORT).show();
                }
            });

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