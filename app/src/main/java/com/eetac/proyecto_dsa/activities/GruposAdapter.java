package com.eetac.proyecto_dsa.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.grupo.Grupo;

import java.util.List;

public class GruposAdapter extends RecyclerView.Adapter<GruposAdapter.ViewHolder> {

    private List<Grupo> listaGrupos;
    private OnItemClickListener listener;

    // Interfaz para detectar cuando clickan el boton de unirse
    public interface OnItemClickListener {
        void onUnirseClick(Grupo grupo);
    }

    // Constructor del adapter
    public GruposAdapter(List<Grupo> listaGrupos, OnItemClickListener listener) {
        this.listaGrupos = listaGrupos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el xml de la fila (item_grupo)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Sacamos el grupo actual de la lista
        Grupo grupo = listaGrupos.get(position);

        // Le ponemos el nombre al TextView
        holder.txtNombreGrupo.setText(grupo.getNombre());

        // Configuramos el click del boton
        holder.btnUnirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUnirseClick(grupo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaGrupos != null ? listaGrupos.size() : 0;
    }

    // Clase interna que busca los elementos del layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreGrupo;
        Button btnUnirse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreGrupo = itemView.findViewById(R.id.txtNombreGrupo);
            btnUnirse = itemView.findViewById(R.id.btnUnirse);
        }
    }
}
