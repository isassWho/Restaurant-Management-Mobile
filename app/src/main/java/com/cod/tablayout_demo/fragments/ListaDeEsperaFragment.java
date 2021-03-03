package com.cod.tablayout_demo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.adapters.ListaEsperaAdapter;
import com.cod.tablayout_demo.entities.ListaEspera;
import com.cod.tablayout_demo.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaDeEsperaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaDeEsperaFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>, View.OnClickListener {

    // variables
    private ListaEsperaAdapter listaEsperaAdapter;
    private RecyclerView recyclerListaEspera;

    private ArrayList<ListaEspera> arrayListaEspera;

    private ProgressDialog progreso;

    private RequestQueue requestQueue;

    private JsonObjectRequest jsonObjectRequest;

    private Vibrator vibrator;

    private FloatingActionButton btn_add_listaEspera;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaDeEsperaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaDeEsperaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaDeEsperaFragment newInstance(String param1, String param2) {
        ListaDeEsperaFragment fragment = new ListaDeEsperaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(getContext(), "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        View vista = inflater.inflate(R.layout.fragment_lista_de_espera, container, false);

        btn_add_listaEspera = vista.findViewById(R.id.fab_listaEspera);
        btn_add_listaEspera.setOnClickListener(this::onClick);
        
        arrayListaEspera = new ArrayList<>();
        
        recyclerListaEspera = vista.findViewById(R.id.recyclerListaEspera);
        recyclerListaEspera.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerListaEspera.setHasFixedSize(true);
        
        // Web Service
        requestQueue = Volley.newRequestQueue(getContext());
        
        this.cargarWebService();

        // Inflate the layout for this fragment
        return vista;
    }

    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage(Utilities.MENSAJE_WS_CONSULTA);
        progreso.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utilities.URL_CONSULTAR_LISTA_DE_ESPERA, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }

    // Implementaciones
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), Utilities.MENSAJE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_LONG).show();
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

        ListaEspera espera;

        JSONArray jsonArray = response.optJSONArray(Utilities.TABLA_LISTA_DE_ESPERA);

        try {
            for (int i = 0; i < jsonArray.length(); i++){

                espera = new ListaEspera();

                JSONObject jsonObject;
                jsonObject = jsonArray.getJSONObject(i);

                espera.setId(jsonObject.optInt(Utilities.LISTA_DE_ESPERA_CAMPO_ID));
                espera.setNombre(jsonObject.optString(Utilities.LISTA_DE_ESPERA_CAMPO_NOMBRE));
                espera.setProfesion(jsonObject.optString(Utilities.LISTA_DE_ESPERA_CAMPO_PROFESION));

                arrayListaEspera.add(espera);
            }// fin for

            listaEsperaAdapter = new ListaEsperaAdapter(arrayListaEspera, R.layout.lista_espera_item, new ListaEsperaAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(ListaEspera listaEspera, int position) {
                    Toast.makeText(getContext(), "Click\nLista de Espera: " + listaEspera + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                }

                @Override
                public void OnLongItemClick(ListaEspera listaEspera, int position) {
                    Toast.makeText(getContext(), "LongClick\nLista de Espera: " + listaEspera + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                    vibrator.vibrate(Utilities.VIBRACION_LONG_CLICK);
                }
            });

            recyclerListaEspera.setAdapter(listaEsperaAdapter);

        } catch (JSONException | NullPointerException e) {
            Toast.makeText(getContext(), Utilities.MENSAJE_WS_CONNECTION_FAILED + response, Toast.LENGTH_LONG).show();
        }finally {
            progreso.hide();
        }
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Añadir en Lista de Espera", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}