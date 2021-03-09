package com.cod.tablayout_demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.Reservation;
import com.cod.tablayout_demo.entities.WaitingList;
import com.cod.tablayout_demo.utilities.Utilities;

public class EditReservationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextAccountOwner;
    private EditText editTextNoAdults;
    private EditText editTextNoChildren;
    private EditText editTextComments;
    private EditText editTextPhone;

    private Button btnSave;
    private Button btnCancel;
    private Button btnCreateCommand;

    private Bundle bundle;

    private Reservation reservation;

    private ProgressDialog progreso;

    private RequestQueue requestQueue;

    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);

        // mapeo
        this.editTextAccountOwner = findViewById(R.id.act_editReservation_edit_accountOwner);
        this.editTextNoAdults = findViewById(R.id.act_editReservation_edit_noAdults);
        this.editTextNoChildren = findViewById(R.id.act_editReservation_edit_noChildren);
        this.editTextComments = findViewById(R.id.act_editReservation_edit_comments);
        this.editTextPhone = findViewById(R.id.act_editReservation_edit_phone);

        this.btnSave = findViewById(R.id.act_editReservation_btn_save);
        this.btnCancel = findViewById(R.id.act_editReservation_btn_cancel);
        this.btnCreateCommand = findViewById(R.id.act_editReservation_btn_createCommand);


        // set de eventos
        this.btnSave.setOnClickListener(this::onClick);
        this.btnCancel.setOnClickListener(this::onClick);
        this.btnCreateCommand.setOnClickListener(this::onClick);

        // se obtiene el objeto pasado desde otro activity
        bundle = getIntent().getExtras();

        // si el bundle contiene algo
        if(bundle!=null){
            reservation = (Reservation) bundle.getSerializable("reservation");
            this.editTextAccountOwner.setText(reservation.getAccountOwner());
            this.editTextNoAdults.setText(reservation.getNoAdults());
            this.editTextNoChildren.setText(reservation.getNoChildren());
            this.editTextComments.setText(reservation.getComments());
            this.editTextPhone.setText(reservation.getPhone());
        }

        requestQueue = Volley.newRequestQueue(this);

    }

    // Eventos
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_editReservation_btn_save:
                this.cargarWebServiceActualizar();
                break;
            case R.id.act_editReservation_btn_cancel:
                Toast.makeText(EditReservationActivity.this, "Cancelar comanda", Toast.LENGTH_SHORT).show();
                break;
            case R.id.act_editReservation_btn_createCommand:
                Toast.makeText(this, "Crear comanda", Toast.LENGTH_SHORT).show();
                break;

        }


    }

    // Creditos complementario preguntar con Militza

    private void cargarWebServiceActualizar() {
        progreso = new ProgressDialog(this);
        progreso.setMessage(Utilities.MESSAGE_WS_UPDATE);
        progreso.show();

        String id = reservation.getId().toString();
        String account_owner = this.editTextAccountOwner.getText().toString();
        String no_adults = this.editTextNoAdults.getText().toString();
        String no_children = this.editTextNoChildren.getText().toString();
        String comments = this.editTextComments.getText().toString();
        String phone = this.editTextPhone.getText().toString();

        String url = Utilities.URL_WS_UPDATE_RESERVATION +
                "account_owner=" + account_owner +
                "&no_adults=" + no_adults +
                "&no_children=" + no_children +
                "&comments=" + comments +
                "&phone=" + phone +
                "&id=" + id;

        // nos permite registrar con espacios
        url = url.replace(" ", "%20");

        // se envia la informacion a Volley
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if(response.trim().equalsIgnoreCase("actualiza")){
                    Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_UPDATE_SUCCESSFULLY, Toast.LENGTH_LONG).show();
                    // termina el activity
                    finish();
                }else{
                    Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_UPDATE_FAILED, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        // permite la comunicacion con los metodos que se implementaron
        requestQueue.add(stringRequest);
    }


    private void cargarWebServiceEliminar() {
        progreso = new ProgressDialog(this);
        progreso.setMessage(Utilities.MESSAGE_WS_DELETE);
        progreso.show();

        String id = reservation.getId().toString();
        // corregir esta ruta
        String url = Utilities.URL_WS_ELIMINAR_MESA + "id="+id;

        // nos permite registrar con espacios
        url = url.replace(" ", "&20");

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if(response.trim().equalsIgnoreCase("elimina")){
                    Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_DELETE_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                    // termina el activity
                    finish();
                }else{
                    Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_DELETE_FAILED, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        // permite la comunicacion con los metodos que se implementaron
        requestQueue.add(stringRequest);
    }

}