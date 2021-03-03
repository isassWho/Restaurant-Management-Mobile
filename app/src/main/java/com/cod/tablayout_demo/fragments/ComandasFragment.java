package com.cod.tablayout_demo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComandasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComandasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    // Variables
    RecyclerView recyclerComandas;
    ArrayList<Comanda> arrayComandas;

    ProgressDialog progreso;

    RequestQueue requestQueue;

    JsonObjectRequest jsonObjectRequest;

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
        View vista = inflater.inflate(R.layout.fragment_comandas, container, false);

        arrayComandas = new ArrayList<>();

        recyclerComandas = vista.findViewById(R.id.recyclerComandas);
        recyclerComandas.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerComandas.setHasFixedSize(true);

        // WebService
        requestQueue = Volley.newRequestQueue(getContext());

        this.cargarWebService();

        // Inflate the layout for this fragment
        return vista;
    }

    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando");
        progreso.show();

        String url = Utilities.IP_SERVIDOR + ":" + Utilities.PUERTO + "/proyectos/Adobes%20Android/wsJSONConsultarListaComandas.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }

    // Implementaciones
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar. " + error.toString(), Toast.LENGTH_LONG).show();
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

            ComandaAdapter comandaAdapter = new ComandaAdapter(arrayComandas);
            recyclerComandas.setAdapter(comandaAdapter);

        }catch (Exception e){
            Toast.makeText(getContext(), "No se ha podido establecer la conexión con el servidor. " + response, Toast.LENGTH_LONG).show();

        }finally {
            progreso.hide();
        }

    }
}