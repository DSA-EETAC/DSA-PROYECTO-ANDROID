package com.eetac.proyecto_dsa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.eetac.proyecto_dsa.utils.LocalUserManager;
import java.util.Set;

public class MochilaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mochila);

        LocalUserManager userManager = new LocalUserManager(this);
        TextView tvLista = findViewById(R.id.tvListaObjetos);
        Button btnVolver = findViewById(R.id.btnVolverMochila);

        // Recuperamos los objetos guardados localmente
        Set<String> inventario = userManager.obtenerInventario();

        if (inventario.isEmpty()) {
            tvLista.setText("Tu mochila está vacía.\n¡Ve a la tienda!");
        } else {
            // Construimos un texto con todos los objetos uno debajo de otro
            StringBuilder sb = new StringBuilder();
            sb.append("Objetos obtenidos:\n\n");
            for (String objeto : inventario) {
                sb.append("⚔️ ").append(objeto).append("\n");
            }
            tvLista.setText(sb.toString());
        }

        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> finish());
        }
    }
}
