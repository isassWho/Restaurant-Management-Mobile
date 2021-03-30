package com.cod.tablayout_demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.Reservation;
import com.cod.tablayout_demo.utilities.Utilities;
import com.cod.tablayout_demo.utilities.UtilitiesAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class EditReservationActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText editTextAccountOwner;
    private EditText editTextNoAdults;
    private EditText editTextNoChildren;
    private EditText editTextComments;
    private EditText editTextPhone;

    private Button btnSave;
    private Button btnCancel;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchEnable;

    private Bundle bundle;

    private Reservation reservation;

    private ProgressDialog progress;

    private RequestQueue requestQueue;

    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);

        this.mapping();

        this.disableComponents();

        this.refillFields();

        this.setEvents();

        requestQueue = Volley.newRequestQueue(this);

    }

    private void refillFields() {
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
    }

    private void setEvents(){
        this.btnSave.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);

        this.switchEnable.setOnCheckedChangeListener(this);
    }

    private void mapping() {
        this.editTextAccountOwner = findViewById(R.id.act_editReservation_edit_accountOwner);
        this.editTextNoAdults = findViewById(R.id.act_editReservation_edit_noAdults);
        this.editTextNoChildren = findViewById(R.id.act_editReservation_edit_noChildren);
        this.editTextComments = findViewById(R.id.act_editReservation_edit_comments);
        this.editTextPhone = findViewById(R.id.act_editReservation_edit_phone);
        this.switchEnable = findViewById(R.id.act_editReservation_switch_enable);

        this.btnSave = findViewById(R.id.act_editReservation_btn_save);
        this.btnCancel = findViewById(R.id.act_editReservation_btn_cancel);
    }

    private void disableComponents() {
        this.editTextAccountOwner.setEnabled(false);
        this.editTextNoAdults.setEnabled(false);
        this.editTextNoChildren.setEnabled(false);
        this.editTextComments.setEnabled(false);
        this.editTextPhone.setEnabled(false);
        this.btnSave.setEnabled(false);

        this.btnCancel.setEnabled(true);

    }
    private void enableComponents() {
        this.editTextAccountOwner.setEnabled(true);
        this.editTextNoAdults.setEnabled(true);
        this.editTextNoChildren.setEnabled(true);
        this.editTextComments.setEnabled(true);
        this.editTextPhone.setEnabled(true);
        this.btnSave.setEnabled(true);

        this.btnCancel.setEnabled(false);

    }

    // Eventos
    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = null;
        AlertDialog title = null;

        switch (v.getId()){
            case R.id.act_editReservation_btn_save:
                if(validateFields()){
                    this.cargarWebServiceActualizar();
                }

                break;
            case R.id.act_editReservation_btn_cancel:

                // Mensaje de confirmacion
                alert = new AlertDialog.Builder(EditReservationActivity.this);
                alert.setMessage(UtilitiesAlertDialog.ALERT_DIALOG_CANCEL_WAITINGLIST_MESSAGE + "\n" + reservation.getAccountOwner())
                        .setCancelable(false)
                        .setPositiveButton(UtilitiesAlertDialog.ALERT_DIALOG_OPTION_ACCEPT, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // codigo para cancelar la comanda
                                finish();
                                loadWebServiceCancel();
                            }
                        })
                        .setNegativeButton(UtilitiesAlertDialog.ALERT_DIALOG_OPTION_CANCEL, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                title = alert.create();
                title.setTitle(UtilitiesAlertDialog.ALERT_DIALOG_CANCEL_WAITINGLIST_TITLE);
                title.show();

                break;

        }

    }

    // Creditos complementario preguntar con Militza

    private void cargarWebServiceActualizar() {
        progress = new ProgressDialog(this);
        progress.setMessage(Utilities.MESSAGE_WS_UPDATE);
        progress.show();

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
                progress.hide();

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
                progress.hide();
                Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        // permite la comunicacion con los metodos que se implementaron
        requestQueue.add(stringRequest);
    }

    private void loadWebServiceCancel() {
        progress = new ProgressDialog(this);
        progress.setMessage(Utilities.MESSAGE_WS_CANCEL);
        progress.show();

        String id = reservation.getId().toString();
        String status = Utilities.STATUS_CANCELED;

        String url = Utilities.URL_WS_CANCEL_RESERVATION +
                "id=" + id +
                "&status=" + status;

        // nos permite registrar con espacios
        url = url.replace(" ", "%20");

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.hide();

                if(response.trim().equalsIgnoreCase("actualiza")){
                    Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_CANCEL_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_CANCEL_FAILED, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                Toast.makeText(EditReservationActivity.this, Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        // permite la comunicacion con los metodos que se implementaron
        requestQueue.add(stringRequest);

    }

    // Switch habilidar/deshabilitar campos
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            enableComponents();
        }else{
            disableComponents();
        }
    }

    /**
     * Valida si todos que todos los campos estén rellenos.
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
            Toast.makeText(EditReservationActivity.this, UtilitiesAlertDialog.TOAST_FIELD_REQUIRED, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}