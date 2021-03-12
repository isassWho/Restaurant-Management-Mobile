package com.cod.tablayout_demo.adapters;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.Reservation;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder>{

    private List<Reservation> listReservations;
    private int layout;
    private OnItemClickListener itemClickListener;

    public ReservationAdapter(List<Reservation> listReservations, int layout, OnItemClickListener itemClickListener) {
        this.listReservations = listReservations;
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
        holder.bind(listReservations.get(position), this.itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.listReservations.size();
    }


    // clase holder
    public class ViewHolder extends RecyclerView.ViewHolder implements  PopupMenu.OnMenuItemClickListener {

        private static final String TAG = "ViewHolder";

        public Reservation objReservation;

        public TextView txtId;
        public TextView txtDate;
        public TextView txtHour;
        public TextView txtAccountOwner;
        public TextView txtNoAdults;
        public TextView txtNoChildren;
        public TextView txtStatus;
        public TextView txtComments;
        public TextView txtIsReservation;
        public TextView txtPhone;

        public ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtId = itemView.findViewById(R.id.frag_reservation_text_id);
            this.txtDate = itemView.findViewById(R.id.frag_reservation_text_date);
            this.txtHour = itemView.findViewById(R.id.frag_reservation_text_hour);
            this.txtAccountOwner = itemView.findViewById(R.id.frag_reservation_text_accountOwner);
            this.txtNoAdults = itemView.findViewById(R.id.frag_reservation_text_noAdults);
            this.txtNoChildren = itemView.findViewById(R.id.frag_reservation_text_noChildren);
            this.txtStatus = itemView.findViewById(R.id.frag_reservation_text_status);
            this.txtComments = itemView.findViewById(R.id.frag_reservation_text_comments);
            this.txtIsReservation = itemView.findViewById(R.id.frag_reservation_text_isReservation);
            this.txtPhone = itemView.findViewById(R.id.frag_reservation_text_phone);

            this.imageButton = itemView.findViewById(R.id.frag_reservation_imgBtn_popup_menu);
        }

        public void bind(final Reservation reservation, final OnItemClickListener listener){
            this.txtId.setText(reservation.getId().toString());
            this.txtDate.setText(reservation.getDate().toString());
            this.txtHour.setText(reservation.getHour().toString());
            this.txtAccountOwner.setText(reservation.getAccountOwner().toString());
            this.txtNoAdults.setText(reservation.getNoAdults().toString());
            this.txtNoChildren.setText(reservation.getNoChildren().toString());
            this.txtStatus.setText(reservation.getStatus().toString());
            this.txtComments.setText(reservation.getComments().toString());
            this.txtIsReservation.setText(String.valueOf(reservation.isReservation()));
            this.txtPhone.setText(reservation.getPhone());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnItemClick(reservation, getAdapterPosition());

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // le pasamos modelo y posicion
                    listener.OnLongItemClick(reservation, getAdapterPosition());
                    return true;
                }
            });

            this.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickOptionButton(reservation, getAdapterPosition());
                    objReservation = reservation;
                    showPopupMenu(v);
                }
            });

// here good
        }


        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);

            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_createComand:
                    Toast.makeText(itemView.getContext(), "Crear comanda para:  " + objReservation.getAccountOwner() +" - " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                    return true;
                case R.id.menu_cancelComand:
                    Toast.makeText(itemView.getContext(), "Cancelar: para:  " + objReservation.getAccountOwner() + " - " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return false;
            }
        }
    }

    // interface
    public interface OnItemClickListener{

        void OnItemClick(Reservation mesa, int position);
        void OnLongItemClick(Reservation mesa, int position);
        void OnClickOptionButton(Reservation mesa, int position);

    }
}
