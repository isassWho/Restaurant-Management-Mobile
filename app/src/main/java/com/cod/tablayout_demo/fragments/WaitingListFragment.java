package com.cod.tablayout_demo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.activities.EditWaitingListActivity;
import com.cod.tablayout_demo.activities.NewWaitingListActivity;
import com.cod.tablayout_demo.adapters.WaitingListAdapter;
import com.cod.tablayout_demo.entities.WaitingList;
import com.cod.tablayout_demo.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingListFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>, View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    // variables
    private WaitingListAdapter waitingListAdapter;
    private RecyclerView recyclerViewWaitingList;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<WaitingList> arrayWaitingList;
    private ArrayList<WaitingList> arrayWaitingListFilterActivas;
    private ArrayList<WaitingList> arrayWaitingListFilterCanceladas;

    private ProgressDialog progress;

    private RequestQueue requestQueue;

    private JsonObjectRequest jsonObjectRequest;

    private Vibrator vibrator;

    private FloatingActionButton btn_add_waitingList;

    private RadioGroup radioGroup;

    private CheckBox checkBoxCanceladas;
    private CheckBox checkBoxActivas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WaitingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingListFragment newInstance(String param1, String param2) {
        WaitingListFragment fragment = new WaitingListFragment();
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

        init();

        View vista = inflater.inflate(R.layout.fragment_waiting_list, container, false);

        mapping(vista);

        setProperties();

        setEvents();

        loadWebService();

        // Inflate the layout for this fragment
        return vista;
    }

    private void loadWebService() {
        // Web Service
        requestQueue = Volley.newRequestQueue(getContext());

        this.cargarWebService();
    }

    private void init() {
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        progress = new ProgressDialog(getContext());
        arrayWaitingList = new ArrayList<>();
        arrayWaitingListFilterActivas = new ArrayList<>();
        arrayWaitingListFilterCanceladas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    private void setProperties() {
        recyclerViewWaitingList.setLayoutManager(linearLayoutManager);
        recyclerViewWaitingList.setHasFixedSize(true);
    }

    private void mapping(View vista) {
        this.recyclerViewWaitingList = vista.findViewById(R.id.recyclerListaEspera);
        this.radioGroup = vista.findViewById(R.id.frag_waitingList_radioGroup_estatus);
        this.checkBoxActivas = vista.findViewById(R.id.frag_waitingList_check_activa);
        this.checkBoxCanceladas = vista.findViewById(R.id.frag_waitingList_check_cancelada);
        this.btn_add_waitingList = vista.findViewById(R.id.fab_listaEspera);
    }

    private void setEvents() {
        this.checkBoxCanceladas.setOnCheckedChangeListener(this::onCheckedChanged);
        this.checkBoxActivas.setOnCheckedChangeListener(this::onCheckedChanged);
        btn_add_waitingList.setOnClickListener(this::onClick);
    }


    private void cargarWebService() {

        progress.setMessage(Utilities.MESSAGE_WS_QUERY);
        progress.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utilities.URL_WS_QUERY_WAITINGLIST, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }

    // Implementaciones
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_LONG).show();
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

        WaitingList waitingList;

        JSONArray jsonArray = response.optJSONArray("tt_waiting_list");

        try {
            for (int i = 0; i < jsonArray.length(); i++){

                waitingList = new WaitingList();

                JSONObject jsonObject;
                jsonObject = jsonArray.getJSONObject(i);

                waitingList.setId(jsonObject.optInt("id"));
                waitingList.setDate(jsonObject.optString("date"));
                waitingList.setHour(jsonObject.optString("hour"));
                waitingList.setAccountOwner(jsonObject.optString("account_owner"));
                waitingList.setNoAdults(jsonObject.optString("no_adults"));
                waitingList.setNoChildren(jsonObject.optString("no_children"));
                waitingList.setStatus(jsonObject.optString("status"));
                waitingList.setComments(jsonObject.optString("comments"));

                // no carga adecuadamente el boolean, por lo que se tuvo que usar esta solucion
                boolean reservation;
                reservation = jsonObject.optInt("is_reservation") == 1? true: false;
                waitingList.setReservation(reservation);

                waitingList.setPhone(jsonObject.optString("phone"));

                arrayWaitingList.add(waitingList);

            }// fin for

            waitingListAdapter = new WaitingListAdapter(arrayWaitingList, R.layout.waiting_list_item, new WaitingListAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(WaitingList waitinglist, int position) {
                    Intent intent = new Intent(getContext(), EditWaitingListActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("waitinglist", waitinglist);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void OnLongItemClick(WaitingList waitinglist, int position) {
                    Toast.makeText(getContext(), "LongClick\nWaiting List: " + waitinglist + "\nPosition: " + position, Toast.LENGTH_LONG).show();
                    vibrator.vibrate(Utilities.VIBRACION_LONG_CLICK);
                }

                @Override
                public void OnClickOptionButton(WaitingList waitingList, int position) {

                }
            });

            recyclerViewWaitingList.setAdapter(waitingListAdapter);

        } catch (JSONException | NullPointerException e) {
            Toast.makeText(getContext(), Utilities.MESSAGE_WS_CONNECTION_FAILED + response, Toast.LENGTH_LONG).show();
        }finally {
            progress.hide();
        }
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Añadir en Waiting List", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Intent intent = new Intent(getContext(), NewWaitingListActivity.class);
        startActivity(intent);
    }
    // chekbox, FILTROS
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.frag_waitingList_check_activa:
                if (isChecked){
                    addToArrayParentDeleteArrayChildren(arrayWaitingList, arrayWaitingListFilterActivas);
                }else{
                    addToArrayChildrenDeleteArrayParent(arrayWaitingList, arrayWaitingListFilterActivas, "ACTIVA");
                }
                //this.waitingListAdapter.notifyDataSetChanged();
                break;

            case R.id.frag_waitingList_check_cancelada:
                if (isChecked){
                    addToArrayParentDeleteArrayChildren(arrayWaitingList, arrayWaitingListFilterCanceladas);
                }else{
                    addToArrayChildrenDeleteArrayParent(arrayWaitingList, arrayWaitingListFilterCanceladas, "CANCELADA");
                }
                //this.waitingListAdapter.notifyDataSetChanged();
                break;
        }

    }

    private void addToArrayChildrenDeleteArrayParent(ArrayList<WaitingList> parent,ArrayList<WaitingList> child, String status) {
        // añade los datos del array padre al hijo
        for (int i = 0; i < parent.size(); i++) {
            if(parent.get(i).getStatus().equals(status)){
                child.add(parent.get(i));
            }
        }// fin for
        //Borra los datos del array padre
        for (int i = 0; i < child.size(); i++) {
            parent.remove(child.get(i));
            this.waitingListAdapter.notifyItemRemoved(parent.size());
            this.linearLayoutManager.scrollToPosition(parent.size());
        }

    }


    private void addToArrayParentDeleteArrayChildren(ArrayList<WaitingList> parent,ArrayList<WaitingList> child) {

        for (int i = 0; i < child.size(); i++) {
            parent.add(child.get(i));
            this.waitingListAdapter.notifyItemInserted(parent.size()-1);
            this.linearLayoutManager.scrollToPosition(parent.size()-1);
        }

        child.clear();
    }



}