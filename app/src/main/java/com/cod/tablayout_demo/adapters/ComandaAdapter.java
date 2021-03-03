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

public class ComandaAdapter extends RecyclerView.Adapter<ComandaAdapter.ViewHolder> {

    // Variables
    private List<Comanda> listaComandas;
    private int layout;
    private OnItemClickListener listener;

    public ComandaAdapter(List<Comanda> listaComandas, int layout, OnItemClickListener itemClickListener) {
        this.listaComandas = listaComandas;
        this.layout = layout;
        this.listener = itemClickListener;
    }
    /**
     * Inflamos los datos
     * */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaComandas.get(position), this.listener);
    }

    @Override
    public int getItemCount() {
        return this.listaComandas.size();
    }

    // interface
    public interface OnItemClickListener{
        void OnItemClick(Comanda comanda, int position);
        void OnLongItemClick(Comanda comanda, int position);
    }

    // clase holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId;
        public TextView txtPropietario;
        public TextView txtPersonas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtId = itemView.findViewById(R.id.frag_comanda_text_id);
            this.txtPropietario = itemView.findViewById(R.id.frag_comanda_text_propietario);
            this.txtPersonas = itemView.findViewById(R.id.frag_comanda_text_personas);
        }

        public void bind(Comanda comanda, OnItemClickListener itemClickListener){
            this.txtId.setText(comanda.getId().toString());
            this.txtPropietario.setText(comanda.getPropietario());
            this.txtPersonas.setText(comanda.getPersonas());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(comanda, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.OnLongItemClick(comanda, getAdapterPosition());
                    return true;
                }
            });

        }
    }
}
