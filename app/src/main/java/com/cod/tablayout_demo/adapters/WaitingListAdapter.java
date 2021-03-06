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

    private List<WaitingList> waitingList;
    private int layout;
    private OnItemClickListener itemClickListener;

    public WaitingListAdapter(List<WaitingList> waitinglistList, int layout, OnItemClickListener itemClickListener) {
        this.waitingList = waitinglistList;
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
        holder.bind(waitingList.get(position), this.itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.waitingList.size();
    }


    // clase holder
    public class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtId = itemView.findViewById(R.id.frag_waitingList_text_id);
            this.txtDate = itemView.findViewById(R.id.frag_waitingList_text_date);
            this.txtHour = itemView.findViewById(R.id.frag_waitingList_text_hour);
            this.txtAccountOwner = itemView.findViewById(R.id.frag_waitingList_text_accountOwner);
            this.txtNoAdults = itemView.findViewById(R.id.frag_waitingList_text_noAdults);
            this.txtNoChildren = itemView.findViewById(R.id.frag_waitingList_text_noChildren);
            this.txtStatus = itemView.findViewById(R.id.frag_waitingList_text_status);
            this.txtComments = itemView.findViewById(R.id.frag_waitingList_text_comments);
            this.txtIsReservation = itemView.findViewById(R.id.frag_waitingList_text_isReservation);
            this.txtPhone = itemView.findViewById(R.id.frag_waitingList_text_phone);

        }

        public void bind(final WaitingList waitinglist, final OnItemClickListener listener){
            this.txtId.setText(waitinglist.getId().toString());
            this.txtDate.setText(waitinglist.getDate().toString());
            this.txtHour.setText(waitinglist.getHour().toString());
            this.txtAccountOwner.setText(waitinglist.getAccountOwner().toString());
            this.txtNoAdults.setText(waitinglist.getNoAdults().toString());
            this.txtNoChildren.setText(waitinglist.getNoChildren().toString());
            this.txtStatus.setText(waitinglist.getStatus().toString());
            this.txtComments.setText(waitinglist.getComments().toString());
            this.txtIsReservation.setText(String.valueOf(waitinglist.isReservation()));
            this.txtPhone.setText(waitinglist.getPhone());

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
