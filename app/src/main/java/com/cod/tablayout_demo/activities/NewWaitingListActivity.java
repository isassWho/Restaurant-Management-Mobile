package com.cod.tablayout_demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cod.tablayout_demo.R;

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

    // valores predefinidos

    private DateFormat dateFormat;
    private DateFormat hourFormat;
    private String status;
    private boolean is_reservation;

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


    }

    // Init the default values
    private void initValues() {
        Date date = new Date();

        // date
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // time
        this.hourFormat = new SimpleDateFormat("HH:mm:ss");
        // status
        this.status = "active";
        // is_reservation
        this.is_reservation = true;

        Toast.makeText(NewWaitingListActivity.this, "Fecha: " + dateFormat.format(date) +
                "\nHora: " + hourFormat.format(date)
                +"\nstatus: " + status
                +"\nreservation: " + is_reservation, Toast.LENGTH_LONG).show();
    }

    // Events
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.act_newWaitingList_btn_save:
                Toast.makeText(NewWaitingListActivity.this, "editTextAccountOwner: " + editTextAccountOwner.getText().toString()
                        + "\neditTextNoAdults: " + editTextNoAdults.getText().toString()
                        +"\neditTextNoChildren: " + editTextNoChildren.getText().toString()
                        +"\neditTextComments: " + editTextComments.getText().toString()
                        +"\neditTextPhone: " + editTextPhone.getText().toString()
                        +"\n", Toast.LENGTH_LONG).show();
                break;
        }

    }
}