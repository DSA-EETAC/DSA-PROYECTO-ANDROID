package com.eetac.proyecto_dsa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.eetac.proyecto_dsa.utils.LocalUserManager;

public class TiendaActivity extends AppCompatActivity {

    private LocalUserManager userManager;
    private TextView tvMonedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        userManager = new LocalUserManager(this);
        tvMonedas = findViewById(R.id.tvMonedasTienda);

        // Mostrar las monedas iniciales al cargar la pantalla
        if (tvMonedas != null) {
            tvMonedas.setText("Monedas: " + userManager.getCoins());
        }

        // Configurar el botón de las botas
        Button btnComprarBota = findViewById(R.id.btnComprarBota);
        if (btnComprarBota != null) {
            btnComprarBota.setOnClickListener(v -> realizarCompra("Botas Ligeras", 150));
        }

        // Configurar el botón de la espada
        Button btnComprarEspada = findViewById(R.id.btnComprarEspada);
        if (btnComprarEspada != null) {
            btnComprarEspada.setOnClickListener(v -> realizarCompra("Espada de Acero", 300));
        }

        Button btnVolver = findViewById(R.id.btnVolverTienda);
        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> finish());
        }
    }

    // LÓGICA DE COMPRA 100% LOCAL (Sin POST ni GET)
    public void realizarCompra(String nombreObjeto, int precio) {
        // 1. Miramos cuánto dinero tiene el usuario guardado en el móvil
        int monedasActuales = userManager.getCoins();

        // 2. Comprobamos si tiene saldo suficiente
        if (monedasActuales >= precio) {

            // 3. Le cobramos (restamos las monedas) y guardamos el nuevo saldo
            int nuevasMonedas = monedasActuales - precio;
            userManager.updateCoins(nuevasMonedas);

            // 4. Metemos el objeto en su mochila local
            // (OJO: Asegúrate de haber puesto este mwtodo en LocalUserManager como vimos antes)
            userManager.añadirAlInventario(nombreObjeto);

            // 5. Actualizamos las monedas en la pantalla
            if (tvMonedas != null) {
                tvMonedas.setText("Monedas: " + nuevasMonedas);
            }

            // 6. Mensaje de éxito
            Toast.makeText(this, "¡Comprado: " + nombreObjeto + "!", Toast.LENGTH_SHORT).show();

        } else {
            // No tiene dinero suficiente
            Toast.makeText(this, "❌ Monedas insuficientes", Toast.LENGTH_LONG).show();
        }
    }
}