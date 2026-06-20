package com.eetac.proyecto_dsa.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.proyecto_dsa.R;
import com.eetac.proyecto_dsa.model.evento.Evento;
import com.eetac.proyecto_dsa.model.evento.InscripcionRequest;
import com.eetac.proyecto_dsa.network.ApiService;
import com.eetac.proyecto_dsa.network.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    public interface OnVerUsuariosListener {
        void onVerUsuarios(String idEvento);
    }

    private List<Evento> eventos;
    private String username;
    private Context context;
    private OnVerUsuariosListener listener;

    public EventoAdapter(List<Evento> eventos, String username, Context context, OnVerUsuariosListener listener) {
        this.eventos = eventos;
        this.username = username;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evento e = eventos.get(position);
        holder.tvNombre.setText(e.getNombre());
        holder.tvDescripcion.setText(e.getDescripcion());
        holder.tvFechas.setText(e.getFecha_inicio() + " → " + e.getFecha_fin());

        holder.btnInscribirse.setOnClickListener(v -> {
            InscripcionRequest req = new InscripcionRequest(username, e.getId());
            ApiService api = RetrofitClient.getService();
            api.inscribirseEvento(req).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Inscrit a " + e.getNombre(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error: ja inscrit?",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Error de xarxa", Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.btnVerUsuarios.setOnClickListener(v -> {
            listener.onVerUsuarios(e.getId());
        });
    }

    @Override
    public int getItemCount() { return eventos.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion, tvFechas;
        Button btnInscribirse, btnVerUsuarios;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre       = itemView.findViewById(R.id.tvNombreEvento);
            tvDescripcion  = itemView.findViewById(R.id.tvDescripcionEvento);
            tvFechas       = itemView.findViewById(R.id.tvFechasEvento);
            btnInscribirse = itemView.findViewById(R.id.btnInscribirse);
            btnVerUsuarios = itemView.findViewById(R.id.btnVerUsuarios);
        }
    }
}