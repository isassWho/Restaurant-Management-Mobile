package com.cod.tablayout_demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.cod.tablayout_demo.utilities.Utilities;
import com.cod.tablayout_demo.utilities.UtilitiesAlertDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewReservationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAccountOwner;
    private EditText editTextNoAdults;
    private EditText editTextNoChildren;
    private EditText editTextComments;
    private EditText editTextPhone;

    private Button btnSave;

    // WS
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    private ProgressDialog progress;

    // valores predefinidos

    private DateFormat dateFormat;
    private DateFormat hourFormat;
    private String status;
    private boolean is_reservation;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);

        this.mapping();

        // Init values
        this.initValuesDefaults();

        this.setEvents();

        this.loadWebServiceQuery();

    }

    private void loadWebServiceQuery() {
        requestQueue = Volley.newRequestQueue(this);
    }

    private void setEvents() {
        this.btnSave.setOnClickListener(this);
    }

    private void mapping() {
        this.editTextAccountOwner = findViewById(R.id.act_newReservation_edit_accountOwner);
        this.editTextNoAdults = findViewById(R.id.act_newReservation_edit_noAdults);
        this.editTextNoChildren = findViewById(R.id.act_newReservation_edit_noChildren);
        this.editTextComments = findViewById(R.id.act_newReservation_edit_comments);
        this.editTextPhone = findViewById(R.id.act_newReservation_edit_phone);

        this.btnSave = findViewById(R.id.act_newReservation_btn_save);
    }

    // Init the default values
    @SuppressLint("SimpleDateFormat")
    private void initValuesDefaults() {
        date = new Date();

        // date
        this.dateFormat = new SimpleDateFormat(Utilities.FORMAT_DATE);
        // time
        this.hourFormat = new SimpleDateFormat(Utilities.FORMAT_TIME);
        // status
        this.status = "ACTIVA";
        // is_reservation
        this.is_reservation = false;

    }

    // Events
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_newReservation_btn_save:
                if(this.validateFields()){
                    this.cargarWebServiceRegister();
                }
                break;
        }
    }

    private void cargarWebServiceRegister() {
        progress = new ProgressDialog(this);
        progress.setMessage(Utilities.MESSAGE_WS_REGISTER);
        progress.show();

        String url = Utilities.URL_WS_REGISTER_RESERVATION +
                "date=" + dateFormat.format(date) +
                "&hour=" + hourFormat.format(date) +
                "&account_owner=" + this.editTextAccountOwner.getText().toString() +
                "&no_adults=" + this.editTextNoAdults.getText().toString() +
                "&no_children=" + this.editTextNoChildren.getText().toString() +
                "&status=" + this.status +
                "&comments=" + this.editTextComments.getText().toString() +
                "&is_reservation=" + this.is_reservation +
                "&phone=" + this.editTextPhone.getText().toString();

        url = url.replace(" ", "%20");

        this.stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.hide();
                if(response.trim().equalsIgnoreCase("registra")){
                    Toast.makeText(NewReservationActivity.this, Utilities.MESSAGE_WS_REGISTER_SUCCESSFULLY, Toast.LENGTH_LONG).show();
                    // termina el activity
                    finish();
                }else{
                    Toast.makeText(NewReservationActivity.this, Utilities.MESSAGE_WS_REGISTER_FAILED, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                Toast.makeText(NewReservationActivity.this, Utilities.MESSAGE_WS_ERROR_RESPONSE + error, Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }
    /**
     * Valida si todos que todos los campos estén rellenos.
     * @return
     */
    private boolean validateFields() {
        List<EditText> array = new ArrayList<>();

        if(editTextAccountOwner.getText().toString().trim().equals("") || editTextAccountOwner.getText().toString().trim() == null){
            array.add(editTextAccountOwner);
        }
        if(editTextNoAdults.getText().toString().trim().equals("") || editTextAccountOwner.getText().toString().trim() == null){
            array.add(editTextNoAdults);
        }
        if(editTextNoChildren.getText().toString().trim().equals("") || editTextAccountOwner.getText().toString().trim() == null){
            array.add(editTextNoChildren);
        }
        if(editTextComments.getText().toString().trim().equals("") || editTextAccountOwner.getText().toString().trim() == null){
            array.add(editTextComments);
        }
        if(editTextPhone.getText().toString().trim().equals("") || editTextAccountOwner.getText().toString().trim() == null){
            array.add(editTextPhone);
        }

        if(array.size() == 0){
            return true;
        }else{
            // Pone el foco en el primer editText que esté vacío
            array.get(0).requestFocus();
            Toast.makeText(NewReservationActivity.this, UtilitiesAlertDialog.TOAST_FIELD_REQUIRED, Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}