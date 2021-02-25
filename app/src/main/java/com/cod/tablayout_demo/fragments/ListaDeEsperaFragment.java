package com.cod.tablayout_demo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.adapters.ListaEsperaAdapter;
import com.cod.tablayout_demo.entities.ListaEspera;
import com.cod.tablayout_demo.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaDeEsperaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaDeEsperaFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>{

    // variables
    RecyclerView recyclerListaEspera;
    ArrayList<ListaEspera> arrayListaEspera;

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
    public void onAttach(@NonNull Context context) {
        Toast.makeText(getContext(), "onAttach", Toast.LENGTH_SHORT).show();
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getContext(), "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "onActivityCreated", Toast.LENGTH_SHORT).show();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Toast.makeText(getContext(), "onStart", Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    public void onResume() {
        Toast.makeText(getContext(), "onResume", Toast.LENGTH_SHORT).show();
        super.onResume();
    }


    @Override
    public void onPause() {
        Toast.makeText(getContext(), "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    public void onStop() {
        Toast.makeText(getContext(), "onStop", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Toast.makeText(getContext(), "onDestroyView", Toast.LENGTH_SHORT).show();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getContext(), "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Toast.makeText(getContext(), "onDetach", Toast.LENGTH_SHORT).show();
        super.onDetach();
    }

    // Implementaciones
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getContext(), "onCreateView", Toast.LENGTH_LONG).show();

        View vista = inflater.inflate(R.layout.fragment_lista_de_espera, container, false);
        
        arrayListaEspera = new ArrayList<>();
        
        recyclerListaEspera = vista.findViewById(R.id.recyclerListaEspera);
        recyclerListaEspera.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerListaEspera.setHasFixedSize(true);
        
        // Web Service
        requestQueue = Volley.newRequestQueue(getContext());
        
        this.cargarWebService();

        // Inflate the layout for this fragment
        return vista;
    }

    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();



        String url = Utilities.IP_SERVER + ":" + Utilities.PORT + "/proyectos/Adobes%20Android/wsJSONConsultarLista.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar. " + error.toString(), Toast.LENGTH_LONG).show();
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        ListaEspera espera;

        JSONArray jsonArray = response.optJSONArray("usuario");
        try {
            for (int i = 0; i< jsonArray.length(); i++){

                espera = new ListaEspera();

                JSONObject jsonObject;
                jsonObject = jsonArray.getJSONObject(i);

                espera.setId(jsonObject.optInt("id"));
                espera.setNombre(jsonObject.optString("nombre"));
                espera.setProfesion(jsonObject.optString("profesion"));
                arrayListaEspera.add(espera);
            }// fin for
            progreso.hide();

            ListaEsperaAdapter listaEsperaAdapter = new ListaEsperaAdapter(arrayListaEspera);
            recyclerListaEspera.setAdapter(listaEsperaAdapter);

        } catch (JSONException e) {
            Toast.makeText(getContext(), "No se ha podido establecer la conexión con el servidor. " + response, Toast.LENGTH_LONG).show();
            progreso.hide();
        }
    }

}