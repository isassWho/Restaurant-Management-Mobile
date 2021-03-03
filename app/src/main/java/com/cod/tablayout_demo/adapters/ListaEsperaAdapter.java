package com.cod.tablayout_demo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.ListaEspera;

import java.util.List;
public class ListaEsperaAdapter extends RecyclerView.Adapter<ListaEsperaAdapter.ViewHolder>{

    private List<ListaEspera> listaEsperaList;
    private int layout;
    private OnItemClickListener itemClickListener;

    public ListaEsperaAdapter(List<ListaEspera> listaEsperaList, int layout, OnItemClickListener itemClickListener) {
        this.listaEsperaList = listaEsperaList;
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
        holder.bind(listaEsperaList.get(position), this.itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.listaEsperaList.size();
    }


    // clase holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId;
        public TextView txtNombre;
        public TextView txtProfesion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtId = itemView.findViewById(R.id.frag_listaEspera_text_id);
            this.txtNombre = itemView.findViewById(R.id.frag_listaEspera_text_nombre);
            this.txtProfesion = itemView.findViewById(R.id.frag_listaEspera_text_profesion);
        }

        public void bind(final ListaEspera listaEspera, final OnItemClickListener listener){
            this.txtId.setText(listaEspera.getId().toString());
            this.txtNombre.setText(listaEspera.getNombre());
            this.txtProfesion.setText(listaEspera.getProfesion());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnItemClick(listaEspera, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnLongItemClick(listaEspera, getAdapterPosition());
                    return true;
                }
            });

        }

    }

    // interface
    public interface OnItemClickListener{

        void OnItemClick(ListaEspera listaEspera, int position);
        void OnLongItemClick(ListaEspera listaEspera, int position);

    }
}
