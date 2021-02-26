package com.cod.tablayout_demo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.Mesa;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.MesaHolder> {

    List<Mesa> listaMesas;

    public MesaAdapter(List<Mesa> listaMesas) {
        this.listaMesas = listaMesas;
    }

    @NonNull
    @Override
    public MesaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesa, parent, false);

        RecyclerView.LayoutParams recyclerView = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(recyclerView);

        return new MesaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaHolder holder, int position) {
        holder.txtId.setText(listaMesas.get(position).getId().toString());
        holder.txtNombre.setText(listaMesas.get(position).getNombre());
        holder.txtPersonas.setText(listaMesas.get(position).getPersonas());
    }

    @Override
    public int getItemCount() {
        return this.listaMesas.size();
    }

    // Clase Holder

    public class MesaHolder extends RecyclerView.ViewHolder {
        TextView txtId;
        TextView txtNombre;
        TextView txtPersonas;

        public MesaHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.frag_mesa_text_id);
            txtNombre = itemView.findViewById(R.id.frag_mesa_text_nombre);
            txtPersonas = itemView.findViewById(R.id.frag_mesa_text_personas);

        }
    }
}
