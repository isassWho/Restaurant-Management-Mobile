package com.cod.tablayout_demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.utilities.Utilities;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewWaitingListActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_new_waiting_list);

        // Mapping
        this.editTextAccountOwner = findViewById(R.id.act_newWaitingList_edit_accountOwner);
        this.editTextNoAdults = findViewById(R.id.act_newWaitingList_edit_noAdults);
        this.editTextNoChildren = findViewById(R.id.act_newWaitingList_edit_noChildren);
        this.editTextComments = findViewById(R.id.act_newWaitingList_edit_comments);
        this.editTextPhone = findViewById(R.id.act_newWaitingList_edit_phone);

        this.btnSave = findViewById(R.id.act_newWaitingList_btn_save);

        // Init values
        this.initValues();

        // Set Event
        this.btnSave.setOnClickListener(this::onClick);

        requestQueue = Volley.newRequestQueue(this);

    }

    // Init the default values
    private void initValues() {
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
            case R.id.act_newWaitingList_btn_save:
                this.cargarWebServiceRegister();
                break;
        }

    }

    private void cargarWebServiceRegister() {
        progress = new ProgressDialog(this);
        progress.setMessage(Utilities.MESSAGE_WS_REGISTER);
        progress.show();

        String url = Utilities.URL_WS_REGISTER_WAITINGLIST +
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
                    Toast.makeText(NewWaitingListActivity.this, Utilities.MESSAGE_WS_REGISTER_SUCCESSFULLY, Toast.LENGTH_LONG).show();
                    // termina el activity
                    finish();
                }else{
                    Toast.makeText(NewWaitingListActivity.this, Utilities.MESSAGE_WS_REGISTER_FAILED, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                Toast.makeText(NewWaitingListActivity.this, Utilities.MESSAGE_WS_ERROR_RESPONSE + error, Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);


    }
}