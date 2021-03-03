package com.cod.tablayout_demo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.adapters.ComandaAdapter;
import com.cod.tablayout_demo.entities.Comanda;
import com.cod.tablayout_demo.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComandasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComandasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {

    // Variables
    private ComandaAdapter comandaAdapter;
    private RecyclerView recyclerComandas;

    private ArrayList<Comanda> arrayComandas;

    private ProgressDialog progreso;

    private RequestQueue requestQueue;

    private JsonObjectRequest jsonObjectRequest;

    private Vibrator vibrator;

    private FloatingActionButton btn_add_comanda;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComandasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComandasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComandasFragment newInstance(String param1, String param2) {
        ComandasFragment fragment = new ComandasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
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

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // infla la vista para el fragment
        View vista = inflater.inflate(R.layout.fragment_comandas, container, false);

        btn_add_comanda = vista.findViewById(R.id.fab_comandas);
        btn_add_comanda.setOnClickListener(this::onClick);

        arrayComandas = new ArrayList<>();

        recyclerComandas = vista.findViewById(R.id.recyclerComandas);
        recyclerComandas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerComandas.setHasFixedSize(true);

        // Web Service
        requestQueue = Volley.newRequestQueue(getContext());

        this.cargarWebService();

        return vista;
    }

    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage(Utilities.MENSAJE_WS_CONSULTA);
        progreso.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utilities.URL_CONSULTAR_LISTA_COMANDAS, null, this, this);
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

        Comanda comanda;

        JSONArray jsonArray = response.optJSONArray(Utilities.TABLA_COMANDAS);

        try {
            for (int i = 0; i < jsonArray.length(); i++){

                comanda = new Comanda();

                JSONObject jsonObject;
                jsonObject = jsonArray.getJSONObject(i);

                comanda.setId(jsonObject.optInt(Utilities.COMANDAS_CAMPO_ID));
                comanda.setPropietario(jsonObject.getString(Utilities.COMANDAS_CAMPO_PROPIETARIO));
                comanda.setPersonas(jsonObject.getString(Utilities.COMANDAS_CAMPO_PERSONAS));
                arrayComandas.add(comanda);
            } // fin for
            comandaAdapter = new ComandaAdapter(arrayComandas, R.layout.comanda_item, new ComandaAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Comanda comanda, int position) {
                    Toast.makeText(getContext(), "Click\nComanda: " + comanda + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                }

                @Override
                public void OnLongItemClick(Comanda comanda, int position) {
                    Toast.makeText(getContext(), "LongClick\nComanda: " + comanda + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                }
            });
            recyclerComandas.setAdapter(comandaAdapter);

        }catch (JSONException | NullPointerException e){
            Toast.makeText(getContext(), Utilities.MENSAJE_WS_CONNECTION_FAILED + response, Toast.LENGTH_LONG).show();
        }finally {
            progreso.hide();
        }

    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Añadir comanda", Snackbar.LENGTH_LONG)
                .setAction("Deshacer", null).show();
    }
}