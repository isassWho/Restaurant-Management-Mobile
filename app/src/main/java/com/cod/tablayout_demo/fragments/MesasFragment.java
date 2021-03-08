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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.activities.EditarMesaActivity;
import com.cod.tablayout_demo.activities.NuevaMesaActivity;
import com.cod.tablayout_demo.adapters.MesaAdapter;
import com.cod.tablayout_demo.entities.Mesa;
import com.cod.tablayout_demo.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MesasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MesasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {

    // Variables
    private MesaAdapter mesaAdapter;
    private RecyclerView recyclerMesas;

    private ArrayList<Mesa> arrayMesas;

    private ProgressDialog progreso;

    private RequestQueue requestQueue;

    private JsonObjectRequest  jsonObjectRequest;

    private Vibrator vibrator;

    private FloatingActionButton btn_add_mesa;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MesasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MesasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MesasFragment newInstance(String param1, String param2) {
        MesasFragment fragment = new MesasFragment();
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

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mesas, container, false);

        btn_add_mesa = vista.findViewById(R.id.fab_mesas);
        btn_add_mesa.setOnClickListener(this::onClick);

        arrayMesas = new ArrayList<>();

        recyclerMesas = vista.findViewById(R.id.recyclerMesas);
        recyclerMesas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMesas.setHasFixedSize(true);

        // Web service
        requestQueue = Volley.newRequestQueue(getContext());

        this.cargarWebService();

        return vista;
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage(Utilities.MESSAGE_WS_QUERY);
        progreso.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utilities.URL_CONSULTAR_LISTA_MESAS, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }


    // Implementaciones
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_LONG).show();
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Mesa mesa;

        JSONArray jsonArray = response.optJSONArray(Utilities.TABLA_MESAS);

        try {
            for(int i = 0; i < jsonArray.length(); i++){

                mesa = new Mesa();

                JSONObject jsonObject;
                jsonObject = jsonArray.getJSONObject(i);

                mesa.setId(jsonObject.optInt(Utilities.MESAS_CAMPO_ID));
                mesa.setNombre(jsonObject.optString(Utilities.MESAS_CAMPO_NOMBRE));
                mesa.setPersonas(jsonObject.optString(Utilities.MESAS_CAMPO_PERSONAS));

                arrayMesas.add(mesa);
            }

            mesaAdapter = new MesaAdapter(arrayMesas, R.layout.mesa_item, new MesaAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Mesa mesa, int position) {
                    //Toast.makeText(getContext(), "Click\nMesa: " + mesa + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                    // pasa como parametro el objeto seleccionado
                    Intent intent = new Intent(getContext(), EditarMesaActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("mesa", mesa);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }

                @Override
                public void OnLongItemClick(Mesa mesa, int position) {
                    //Toast.makeText(getContext(), "Long Click\nMesa: " + mesa + "\nPosición: " + position, Toast.LENGTH_LONG).show();
                    vibrator.vibrate(Utilities.VIBRACION_LONG_CLICK);
                }
            });
            recyclerMesas.setAdapter(mesaAdapter);


        }catch (JSONException | NullPointerException e){
            Toast.makeText(getContext(), Utilities.MESSAGE_WS_CONNECTION_FAILED + response, Toast.LENGTH_LONG).show();
        }finally {
            progreso.hide();
        }
    }

    // Evento para el floatingActionButton
    @Override
    public void onClick(View v) {
        Intent i = new Intent(v.getContext(), NuevaMesaActivity.class);

        startActivity(i);

        Snackbar.make(v, "Añadir mesa", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}