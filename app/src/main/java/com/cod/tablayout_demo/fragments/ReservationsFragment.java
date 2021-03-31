package com.cod.tablayout_demo.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.activities.EditReservationActivity;
import com.cod.tablayout_demo.activities.NewReservationActivity;
import com.cod.tablayout_demo.adapters.ReservationAdapter;
import com.cod.tablayout_demo.entities.Reservation;
import com.cod.tablayout_demo.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationsFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    // Variables
    private ReservationAdapter reservationAdapter;
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private List<Reservation> arrayReservations;
    private List<Reservation> arrayWaitingListFilterActivas;
    private List<Reservation> arrayWaitingListFilterCanceladas;

    private ProgressDialog progress;

    private RequestQueue requestQueue;

    private JsonObjectRequest  jsonObjectRequest;

    private Vibrator vibrator;

    private FloatingActionButton btn_add_reservation;

    private RadioButton radioButtonCanceled;
    private RadioButton radioButtonActive;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationsFragment newInstance(String param1, String param2) {
        ReservationsFragment fragment = new ReservationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.init();

        View vista = inflater.inflate(R.layout.fragment_reservations, container, false);

        this.mapping(vista);

        this.setProperties();

        this.setEvents();

        return vista;
    }

    private void setProperties() {
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
    }

    private void setEvents() {
        this.btn_add_reservation.setOnClickListener(this);
        this.radioButtonCanceled.setOnCheckedChangeListener(this);
        this.radioButtonActive.setOnCheckedChangeListener(this);

    }

    private void mapping(View vista) {
        this.progress = new ProgressDialog(getContext());
        this.btn_add_reservation = vista.findViewById(R.id.fab_Reservations);
        this.recyclerView = vista.findViewById(R.id.recyclerReservations);

        this.radioButtonActive = vista.findViewById(R.id.frag_reservation_check_activa);
        this.radioButtonCanceled = vista.findViewById(R.id.frag_reservation_check_cancelada);
    }

    private void init() {
        this.vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        this.arrayReservations = new ArrayList<>();
        this.arrayWaitingListFilterActivas = new ArrayList<>();
        this.arrayWaitingListFilterCanceladas = new ArrayList<>();
        this.linearLayoutManager = new LinearLayoutManager(getContext());

    }

    private void loadWebService() {

        // Web service
        this.requestQueue = Volley.newRequestQueue(getContext());

        this.progress.setMessage(Utilities.MESSAGE_WS_QUERY);
        this.progress.show();

        this.jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utilities.URL_WS_QUERY_RESERVATIONS, null, this, this);
        this.requestQueue.add(jsonObjectRequest);

    }
    @Override
    public void onStart() {
        super.onStart();
        clearArrayList();
        loadWebService();
        this.validateCheckBox();
    }

    private void clearArrayList() {
        this.arrayReservations.clear();
        this.arrayWaitingListFilterCanceladas.clear();
        this.arrayWaitingListFilterActivas.clear();
    }

    private void validateCheckBox() {

        // Tuve que poner un temporizador para que los arrays de los check se rellenen
        if (this.radioButtonActive.isChecked()){
            this.timer(arrayReservations, arrayWaitingListFilterActivas);
        }else if(this.radioButtonCanceled.isChecked()){
            this.timer(arrayReservations, arrayWaitingListFilterCanceladas);
        }


    }


    // Implementaciones
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_LONG).show();
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

        Reservation reservation;

        JSONArray jsonArray = response.optJSONArray("tt_reservations");

        try {
            for(int i = 0; i < jsonArray.length(); i++){

                reservation = new Reservation();

                JSONObject jsonObject;
                jsonObject = jsonArray.getJSONObject(i);

                reservation.setId(jsonObject.optInt("id"));
                reservation.setDate(jsonObject.optString("date"));
                reservation.setHour(jsonObject.optString("hour"));
                reservation.setAccountOwner(jsonObject.optString("account_owner"));
                reservation.setNoAdults(jsonObject.optString("no_adults"));
                reservation.setNoChildren(jsonObject.optString("no_children"));
                reservation.setStatus(jsonObject.optString("status"));
                reservation.setComments(jsonObject.optString("comments"));

                // no carga adecuadamente el boolean, por lo que se tuvo que usar esta solucion
                boolean isReservation;
                isReservation = jsonObject.optInt("is_reservation") == 1;
                reservation.setReservation(isReservation);

                reservation.setPhone(jsonObject.optString("phone"));
                // Rellena los arrays que van ligados con los checks.
                switch (reservation.getStatus()){
                    case "ACTIVA":
                        arrayWaitingListFilterActivas.add(reservation);
                        break;
                    case "CANCELADA":
                        arrayWaitingListFilterCanceladas.add(reservation);
                        break;
                }

            }

            reservationAdapter = new ReservationAdapter(arrayReservations, R.layout.reservations_item, new ReservationAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Reservation reservation, int position) {
                    //Toast.makeText(getContext(), "Click\nMesa: " + reservation + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                    // pasa como parametro el objeto seleccionado
                    Intent intent = new Intent(getContext(), EditReservationActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("reservation", reservation);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void OnLongItemClick(Reservation reservation, int position) {
                    //Toast.makeText(getContext(), "Long Click\nMesa: " + reservation + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                    vibrator.vibrate(Utilities.VIBRACION_LONG_CLICK);
                }

                @Override
                public void OnClickOptionButton(Reservation reservation, int position) {

                }

            });

            recyclerView.setAdapter(reservationAdapter);


        }catch (JSONException | NullPointerException e){
            Toast.makeText(getContext(), Utilities.MESSAGE_WS_CONNECTION_FAILED + response, Toast.LENGTH_LONG).show();
        }finally {
            progress.hide();
        }
    }

    // Evento para el floatingActionButton
    @Override
    public void onClick(View v) {
        Intent i = new Intent(v.getContext(), NewReservationActivity.class);

        startActivity(i);

    }

    // chekbox, FILTROS
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.frag_reservation_check_activa:
                if (isChecked){
                    addToArrayParentDeleteArrayChildren(arrayReservations, arrayWaitingListFilterActivas);
                }else{
                    addToArrayChildrenDeleteArrayParent(arrayReservations, arrayWaitingListFilterActivas, Utilities.STATUS_ACTIVE);
                }
                break;

            case R.id.frag_reservation_check_cancelada:
                if (isChecked){
                    addToArrayParentDeleteArrayChildren(arrayReservations, arrayWaitingListFilterCanceladas);
                }else{
                    addToArrayChildrenDeleteArrayParent(arrayReservations, arrayWaitingListFilterCanceladas, Utilities.STATUS_CANCELED);
                }
                break;
        }

    }

    private void addToArrayChildrenDeleteArrayParent(List<Reservation> parent, List<Reservation> child, String status) {

        // añade los datos del array padre al hijo
        for (Reservation w: parent) {
            if(w.getStatus().equals(status)){
                child.add(w);
            }
        }

        //Borra los datos del array padre
        for (Reservation w: child) {
            parent.remove(w);
            //this.waitingListAdapter.notifyItemRemoved(parent.size());
        }
        this.reservationAdapter.notifyDataSetChanged();
        this.linearLayoutManager.scrollToPosition(parent.size());

    }


    private void addToArrayParentDeleteArrayChildren(List<Reservation> parent, List<Reservation> child) {

        for (int i = 0; i < child.size(); i++) {
            parent.add(child.get(i));
            this.reservationAdapter.notifyDataSetChanged();
        }
        this.linearLayoutManager.scrollToPosition(parent.size()-1);
        child.clear();
    }

    private void timer(List<Reservation> parent, List<Reservation> child){
        new CountDownTimer(2000, 2000){

            @Override
            public void onTick(long millisUntilFinished) {
                // vacio
            }

            @Override
            public void onFinish() {
                addToArrayParentDeleteArrayChildren(parent, child);
            }
        }.start();
    }
}