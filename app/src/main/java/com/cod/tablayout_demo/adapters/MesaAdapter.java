package com.cod.tablayout_demo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.Comanda;
import com.cod.tablayout_demo.entities.Mesa;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.ViewHolder>{

    private List<Mesa> listaMesas;
    private int layout;
    private OnItemClickListener itemClickListener;

    public MesaAdapter(List<Mesa> listaMesas, int layout, OnItemClickListener itemClickListener) {
        this.listaMesas = listaMesas;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    /**
     * Inflamos los datos
     * */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(this.layout, parent, false);
        return new ViewHolder(v);
    }

    /***
     * Hace lo que es cambiar los datos.
     * Tomamos nuestro modelo y lo volcamos en el textView
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaMesas.get(position), this.itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.listaMesas.size();
    }


    // clase holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId;
        public TextView txtPropietario;
        public TextView txtPersonas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtId = itemView.findViewById(R.id.frag_mesa_text_id);
            this.txtPropietario = itemView.findViewById(R.id.frag_mesa_text_propietario);
            this.txtPersonas = itemView.findViewById(R.id.frag_mesa_text_personas);
        }

        public void bind(final Mesa mesa, final OnItemClickListener listener){
            this.txtId.setText(mesa.getId().toString());
            this.txtPropietario.setText(mesa.getNombre());
            this.txtPersonas.setText(mesa.getPersonas());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnItemClick(mesa, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnLongItemClick(mesa, getAdapterPosition());
                    return true;
                }
            });

        }

    }

    // interface
    public interface OnItemClickListener{

        void OnItemClick(Mesa mesa, int position);
        void OnLongItemClick(Mesa mesa, int position);

    }
}
