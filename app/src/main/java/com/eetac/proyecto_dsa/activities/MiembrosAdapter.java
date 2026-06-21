package com.eetac.proyecto_dsa.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eetac.proyecto_dsa.R;
import java.util.List;

public class MiembrosAdapter extends RecyclerView.Adapter<MiembrosAdapter.ViewHolder> {

    private List<String> listaMiembros;

    // Constructor: recibe la lista de datos
    public MiembrosAdapter(List<String> listaMiembros) {
        this.listaMiembros = listaMiembros;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // "Inflamos" el diseño XML de la fila
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_miembro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtenemos el miembro actual según su posición en la lista
        String miembro = listaMiembros.get(position);

        // Rellenamos los datos en la vista usando los Getters con los nombres que me dijiste
        holder.textNombre.setText(miembro);
        holder.textMonedas.setVisibility(View.GONE);
        holder.textMail.setVisibility(View.GONE);

        // NOTA: Si 'mail' es realmente un texto y no una URL de imagen, el ImageView 'imageAvatar'
        // se quedará con el fondo gris. Si usáis Glide para imágenes, iría aquí.
    }

    @Override
    public int getItemCount() {
        return listaMiembros != null ? listaMiembros.size() : 0;
    }

    // Clase interna que conecta las variables de Java con los IDs del XML
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textMonedas, textMail;
        ImageView imageAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombreMiembro);
            textMonedas = itemView.findViewById(R.id.textMonedasMiembro);
            textMail = itemView.findViewById(R.id.textMailMiembro);
            imageAvatar = itemView.findViewById(R.id.imageAvatar);
        }
    }
}