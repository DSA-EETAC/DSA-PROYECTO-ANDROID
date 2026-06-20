package com.eetac.proyecto_dsa.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.usuario.User;

import java.util.List;

public class UsuariosEventoAdapter extends RecyclerView.Adapter<UsuariosEventoAdapter.ViewHolder> {

    private List<User> usuarios;

    public UsuariosEventoAdapter(List<User> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario_evento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User u = usuarios.get(position);
        holder.tvNombre.setText(u.getNombre());
        holder.tvMail.setText(u.getMail());
    }

    @Override
    public int getItemCount() { return usuarios.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreUsuario);
            tvMail   = itemView.findViewById(R.id.tvMailUsuario);
        }
    }
}