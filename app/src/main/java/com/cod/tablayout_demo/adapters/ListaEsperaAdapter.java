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

public class ListaEsperaAdapter extends RecyclerView.Adapter<ListaEsperaAdapter.ListaEsperaHolder> implements View.OnClickListener {

    // variables
    List<ListaEspera> listaDeEspera;

    // propiedad para usar el click del recycler view
    private View.OnClickListener listenerClick;

    // constructor
    public ListaEsperaAdapter(List<ListaEspera> listaDeEspera){
        this.listaDeEspera = listaDeEspera;
    }

    @NonNull
    @Override
    public ListaEsperaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_espera_item, parent, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        // ponemos al escuchador a escuchar
        vista.setOnClickListener(this);

        return new ListaEsperaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaEsperaHolder holder, int position) {
        holder.txtId.setText(listaDeEspera.get(position).getId().toString());
        holder.txtNombre.setText(listaDeEspera.get(position).getNombre());
        holder.txtProfesion.setText(listaDeEspera.get(position).getProfesion());
    }

    @Override
    public int getItemCount() {
        return this.listaDeEspera.size();
    }

    // se encarga de escuhcar el evento
    public void setOnClickListener(View.OnClickListener listenerClick){
        this.listenerClick = listenerClick;
    }


    // Para manejar los click de los items del recyclerView
    @Override
    public void onClick(View v) {
        //
        if(listenerClick != null){
            listenerClick.onClick(v);
        }
    }

    // Clase Holder

    public class ListaEsperaHolder extends RecyclerView.ViewHolder {

        TextView txtId;
        TextView txtNombre;
        TextView txtProfesion;

        public ListaEsperaHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.frag_listaEspera_text_id);
            txtNombre = itemView.findViewById(R.id.frag_listaEspera_text_nombre);
            txtProfesion = itemView.findViewById(R.id.frag_listaEspera_text_profesion);
        }
    }
}
