package com.cod.tablayout_demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.cod.tablayout_demo.entities.WaitingList;
import com.cod.tablayout_demo.utilities.Utilities;

public class EditWaitingListActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAccountOwner;
    private EditText editTextNoAdults;
    private EditText editTextNoChildren;
    private EditText editTextComments;
    private EditText editTextPhone;

    private Button btnSave;
    private Button btnCancel;
    private Button btnCreateCommand;

    private Switch switchEnable;
    private Boolean flagEnableComponents;

    private Bundle bundle;

    private WaitingList waitingList;

    // WS
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_waiting_list);

        // mapping
        this.editTextAccountOwner = findViewById(R.id.act_editWaitingList_edit_accountOwner);
        this.editTextNoAdults = findViewById(R.id.act_editWaitingList_edit_noAdults);
        this.editTextNoChildren = findViewById(R.id.act_editWaitingList_edit_noChildren);
        this.editTextComments = findViewById(R.id.act_editWaitingList_edit_comments);
        this.editTextPhone = findViewById(R.id.act_editWaitingList_edit_phone);
        this.switchEnable = findViewById(R.id.act_editWaitingList_switch_enable);

        this.btnSave = findViewById(R.id.act_editWaitingList_btn_save);
        this.btnCancel = findViewById(R.id.act_editWaitingList_btn_cancel);
        this.btnCreateCommand = findViewById(R.id.act_editWaitingList_btn_createCommand);

        this.disableComponents();

        // set Events
        this.btnSave.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        this.btnCreateCommand.setOnClickListener(this);

        this.switchEnable.setOnClickListener(this::onClick);

        bundle = getIntent().getExtras();

        if(bundle != null){
            waitingList = (WaitingList) bundle.getSerializable("waitinglist");
            this.editTextAccountOwner.setText(waitingList.getAccountOwner());
            this.editTextNoAdults.setText(waitingList.getNoAdults());
            this.editTextNoChildren.setText(waitingList.getNoChildren());
            this.editTextComments.setText(waitingList.getComments());
            this.editTextPhone.setText(waitingList.getPhone());
        }

        requestQueue = Volley.newRequestQueue(this);

    }

    private void disableComponents() {
        this.editTextAccountOwner.setEnabled(false);
        this.editTextNoAdults.setEnabled(false);
        this.editTextNoChildren.setEnabled(false);
        this.editTextComments.setEnabled(false);
        this.editTextPhone.setEnabled(false);
        this.btnSave.setEnabled(false);

        this.flagEnableComponents = false;
    }
    private void enableComponents() {
        this.editTextAccountOwner.setEnabled(true);
        this.editTextNoAdults.setEnabled(true);
        this.editTextNoChildren.setEnabled(true);
        this.editTextComments.setEnabled(true);
        this.editTextPhone.setEnabled(true);
        this.btnSave.setEnabled(true);

        this.flagEnableComponents = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_editWaitingList_btn_save:
                this.cargarWebServiceUpdate();
                break;
            case R.id.act_editWaitingList_btn_cancel:
                Toast.makeText(this, "Cancelar comanda", Toast.LENGTH_SHORT).show();
                break;
                case R.id.act_editWaitingList_btn_createCommand:
                Toast.makeText(this, "Crear comanda", Toast.LENGTH_SHORT).show();
                break;
            case R.id.act_editWaitingList_switch_enable:
                if(flagEnableComponents){
                    this.disableComponents();
                }else{
                    this.enableComponents();
                }
                break;
        }
    }

    private void cargarWebServiceUpdate() {
        progress = new ProgressDialog(this);
        progress.setMessage(Utilities.MESSAGE_WS_UPDATE);
        progress.show();

        String id = waitingList.getId().toString();
        String account_owner = this.editTextAccountOwner.getText().toString();
        String no_adults = this.editTextNoAdults.getText().toString();
        String no_children = this.editTextNoChildren.getText().toString();
        String comments = this.editTextComments.getText().toString();
        String phone = this.editTextPhone.getText().toString();

        String url = Utilities.URL_WS_UPDATE_WAITINGLIST +
                "account_owner=" + account_owner +
                "&no_adults=" + no_adults +
                "&no_children=" + no_children +
                "&comments=" + comments +
                "&phone=" + phone +
                "&id=" + id;

        // nos permite registrar con espacios
        url = url.replace(" ", "%20");

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.hide();

                if(response.trim().equalsIgnoreCase("actualiza")){
                    Toast.makeText(EditWaitingListActivity.this, Utilities.MESSAGE_WS_UPDATE_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(EditWaitingListActivity.this, Utilities.MESSAGE_WS_UPDATE_FAILED, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                Toast.makeText(EditWaitingListActivity.this, Utilities.MESSAGE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        // permite la comunicacion con los metodos que se implementaron
        requestQueue.add(stringRequest);

    }
}