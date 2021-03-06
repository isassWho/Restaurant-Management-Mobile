package com.cod.tablayout_demo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.WaitingList;

import java.util.List;
public class WaitingListAdapter extends RecyclerView.Adapter<WaitingListAdapter.ViewHolder>{

    private List<WaitingList> listaEsperaList;
    private int layout;
    private OnItemClickListener itemClickListener;

    public WaitingListAdapter(List<WaitingList> waitinglistList, int layout, OnItemClickListener itemClickListener) {
        this.listaEsperaList = waitinglistList;
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

        public void bind(final WaitingList waitinglist, final OnItemClickListener listener){
            this.txtId.setText(waitinglist.getId().toString());
            this.txtNombre.setText(waitinglist.getNombre());
            this.txtProfesion.setText(waitinglist.getProfesion());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnItemClick(waitinglist, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnLongItemClick(waitinglist, getAdapterPosition());
                    return true;
                }
            });

        }

    }

    // interface
    public interface OnItemClickListener{

        void OnItemClick(WaitingList waitinglist, int position);
        void OnLongItemClick(WaitingList waitinglist, int position);

    }
}
