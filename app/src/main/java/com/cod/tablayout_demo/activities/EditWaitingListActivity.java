package com.cod.tablayout_demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.WaitingList;

public class EditWaitingListActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAccountOwner;
    private EditText editTextNoAdults;
    private EditText editTextNoChildren;
    private EditText editTextComments;
    private EditText editTextPhone;

    private Button btnSave;
    private Button btnDelete;

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

        this.btnSave = findViewById(R.id.act_editWaitingList_btn_save);
        this.btnDelete = findViewById(R.id.act_editWaitingList_btn_delete);

        // set Events
        this.btnSave.setOnClickListener(this);
        this.btnDelete.setOnClickListener(this);

        bundle = getIntent().getExtras();

        if(bundle != null){
            waitingList = (WaitingList) bundle.getSerializable("waitinglist");
            this.editTextAccountOwner.setText(waitingList.getAccountOwner());
            this.editTextNoAdults.setText(waitingList.getNoAdults());
            this.editTextNoChildren.setText(waitingList.getNoChildren());
            this.editTextComments.setText(waitingList.getComments());
            this.editTextPhone.setText(waitingList.getPhone());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_editWaitingList_btn_save:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.act_editWaitingList_btn_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}