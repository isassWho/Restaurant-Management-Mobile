package com.cod.tablayout_demo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.Comanda;

import java.util.List;

public class ComandaAdapter extends RecyclerView.Adapter<ComandaAdapter.ComandaHolder> {

    // Variables
    List<Comanda> listaComandas;

    // Constructor
    public ComandaAdapter(List<Comanda> listaComandas) {
        this.listaComandas = listaComandas;
    }

    @NonNull
    @Override
    public ComandaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.comanda, parent, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        return new ComandaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ComandaHolder holder, int position) {
        holder.txtId.setText(listaComandas.get(position).getId().toString());
        holder.txtPropietario.setText(listaComandas.get(position).getPropietario());
        holder.txtPersonas.setText(listaComandas.get(position).getPersonas());
    }

    @Override
    public int getItemCount() {
        return this.listaComandas.size();
    }

    // Clase Holder

    public class ComandaHolder extends RecyclerView.ViewHolder {

        TextView txtId;
        TextView txtPropietario;
        TextView txtPersonas;

        public ComandaHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.frag_comanda_text_id);
            txtPropietario = itemView.findViewById(R.id.frag_comanda_text_propietario);
            txtPersonas = itemView.findViewById(R.id.frag_comanda_text_personas);
        }
    }
}
