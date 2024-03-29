package com.cod.tablayout_demo.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.cod.tablayout_demo.activities.EditWaitingListActivity;
import com.cod.tablayout_demo.entities.WaitingList;
import com.cod.tablayout_demo.utilities.UtilitiesAlertDialog;

import java.util.ArrayList;
import java.util.List;
public class WaitingListAdapter extends RecyclerView.Adapter<WaitingListAdapter.ViewHolder> {

    private List<WaitingList> waitingList;
    private List<WaitingList> waitingListAll;
    private int layout;
    private OnItemClickListener itemClickListener;

    public WaitingListAdapter(List<WaitingList> waitinglistList, int layout, OnItemClickListener itemClickListener) {
        this.waitingList = waitinglistList;
        this.waitingListAll = new ArrayList<>(waitinglistList);
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
    // implements Filterable


    // clase holder
    public class ViewHolder extends RecyclerView.ViewHolder implements  PopupMenu.OnMenuItemClickListener  {

        public WaitingList objWaitingList;

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
            this.txtHour = itemView.findViewById(R.id.frag_waitingList_text_hour);
            this.txtAccountOwner = itemView.findViewById(R.id.frag_waitingList_text_accountOwner);
            this.txtNoAdults = itemView.findViewById(R.id.frag_waitingList_text_noAdults);
            this.txtNoChildren = itemView.findViewById(R.id.frag_waitingList_text_noChildren);
            this.txtStatus = itemView.findViewById(R.id.frag_waitingList_text_status);

            this.imageButton = itemView.findViewById(R.id.frag_waitingList_imgBtn_popup_menu);

        }

        public void bind(final WaitingList waitinglist, final OnItemClickListener listener){
            this.txtHour.setText(waitinglist.getHour().toString());
            this.txtAccountOwner.setText(waitinglist.getAccountOwner().toString());
            this.txtNoAdults.setText(waitinglist.getNoAdults().toString());
            this.txtNoChildren.setText(waitinglist.getNoChildren().toString());
            this.txtStatus.setText(waitinglist.getStatus().toString());

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

            this.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickOptionButton(waitinglist, getAdapterPosition());
                    objWaitingList = waitinglist;
                    showPopupMenu(v);
                }
            });

        }


        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.frag_waitinglist_popup_menu);
            popupMenu.setOnMenuItemClickListener(this);

            popupMenu.show();
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.frag_waitingList_createComand:

                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setMessage(UtilitiesAlertDialog.ALERT_DIALOG_CREATE_COMMAND_MESSAGE  + objWaitingList.getAccountOwner())
                            .setCancelable(false)
                            .setPositiveButton(UtilitiesAlertDialog.ALERT_DIALOG_OPTION_ACCEPT, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(itemView.getContext(), "Creando la comanda", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton(UtilitiesAlertDialog.ALERT_DIALOG_OPTION_CANCEL, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog title = alert.create();
                    title.setTitle(UtilitiesAlertDialog.ALERT_DIALOG_CREATE_COMMAND_TITLE);
                    title.show();

                    return true;
                default:
                    return false;
            }
        }

    }
    // interface
    public interface OnItemClickListener{

        void OnItemClick(WaitingList waitingList, int position);
        void OnLongItemClick(WaitingList waitinglist, int position);
        void OnClickOptionButton(WaitingList waitingList, int position);
    }
}
