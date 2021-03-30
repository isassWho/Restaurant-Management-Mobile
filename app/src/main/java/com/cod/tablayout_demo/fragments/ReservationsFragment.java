package com.cod.tablayout_demo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationsFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {

    // Variables
    private ReservationAdapter reservationAdapter;
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private ArrayList<Reservation> arrayReservations;

    private ProgressDialog progress;

    private RequestQueue requestQueue;

    private JsonObjectRequest  jsonObjectRequest;

    private Vibrator vibrator;

    private FloatingActionButton btn_add_reservation;


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

        this.loadWebService();

        return vista;
    }

    private void setProperties() {
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
    }

    private void setEvents() {
        this.btn_add_reservation.setOnClickListener(this);

    }

    private void mapping(View vista) {
        this.progress = new ProgressDialog(getContext());
        this.btn_add_reservation = vista.findViewById(R.id.fab_Reservations);
        this.recyclerView = vista.findViewById(R.id.recyclerReservations);
    }

    private void init() {
        this.vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        this.arrayReservations = new ArrayList<>();
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

                arrayReservations.add(reservation);
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

        Snackbar.make(v, "Añadir mesa", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}