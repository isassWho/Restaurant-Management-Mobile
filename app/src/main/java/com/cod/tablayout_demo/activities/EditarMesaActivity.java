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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cod.tablayout_demo.R;
import com.cod.tablayout_demo.entities.Mesa;
import com.cod.tablayout_demo.utilities.Utilities;

import org.json.JSONObject;

import java.io.Serializable;

public class EditarMesaActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextPropietario;
    private EditText editTextPersonas;
    private EditText editTextId;

    private Button btnEliminar;
    private Button btnActualizar;

    private Bundle bundle;

    private Mesa mesa;

    private ProgressDialog progreso;

    private RequestQueue requestQueue;

    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mesa);

        // mapeo
        this.editTextId = findViewById(R.id.act_editarMesa_edit_id);
        this.editTextPropietario = findViewById(R.id.act_editarMesa_edit_propietario);
        this.editTextPersonas = findViewById(R.id.act_editarMesa_edit_personas);

        this.btnActualizar = findViewById(R.id.act_editarMesa_btn_eliminar);
        this.btnEliminar = findViewById(R.id.act_editarMesa_btn_actualizar);

        // set de eventos
        this.btnEliminar.setOnClickListener(this::onClick);
        this.btnActualizar.setOnClickListener(this::onClick);

        // se obtiene el objeto pasado desde otro activity
        bundle = getIntent().getExtras();

        // si el bundle contiene algo
        if(bundle!=null){
            mesa = (Mesa) bundle.getSerializable("mesa");
            this.editTextId.setText(mesa.getId().toString());
            this.editTextPropietario.setText(mesa.getNombre());
            this.editTextPersonas.setText(mesa.getPersonas());
        }

        requestQueue = Volley.newRequestQueue(this);

    }

    // Eventos
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_editarMesa_btn_eliminar:
                this.cargarWebServiceEliminar();
                break;
            case R.id.act_editarMesa_btn_actualizar:
                this.cargarWebServiceActualizar();
                break;
            default:

        }
    }

    // Creditos complementario preguntar con Militza

    private void cargarWebServiceActualizar() {
        progreso = new ProgressDialog(this);
        progreso.setMessage(Utilities.MENSAJE_WS_ACTUALIZACION);
        progreso.show();

        String id = this.editTextId.getText().toString();
        String nombre = this.editTextPropietario.getText().toString();
        String personas = this.editTextPersonas.getText().toString();

        String url = Utilities.URL_ACTUALIZAR_MESA +
                Utilities.MESAS_CAMPO_ID + "=" + id + "&" +
                Utilities.MESAS_CAMPO_NOMBRE + "=" + nombre + "&" +
                Utilities.MESAS_CAMPO_PERSONAS + "=" + personas;

        // nos permite registrar con espacios
        url = url.replace(" ", "&20");

        // se envia la informacion a Volley
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if(response.trim().equalsIgnoreCase("actualiza")){
                    Toast.makeText(EditarMesaActivity.this, Utilities.MENSAJE_WS_ACTUALIZACION_EXITOSA, Toast.LENGTH_LONG).show();
                    // termina el activity
                    finish();
                }else{
                    Toast.makeText(EditarMesaActivity.this, Utilities.MENSAJE_WS_ACTUALIZACION_FALLIDA, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(EditarMesaActivity.this, Utilities.MENSAJE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        // permite la comunicacion con los metodos que se implementaron
        requestQueue.add(stringRequest);
    }


    private void cargarWebServiceEliminar() {
        progreso = new ProgressDialog(this);
        progreso.setMessage(Utilities.MENSAJE_WS_ELIMINACION);
        progreso.show();

        String id = this.editTextId.getText().toString();

        String url = Utilities.URL_WS_ELIMINAR_MESA + "id="+id;

        // nos permite registrar con espacios
        url = url.replace(" ", "&20");

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if(response.trim().equalsIgnoreCase("elimina")){
                    Toast.makeText(EditarMesaActivity.this, Utilities.MENSAJE_WS_ELIMINACION_EXITOSA, Toast.LENGTH_SHORT).show();
                    // termina el activity
                    finish();
                }else{
                    Toast.makeText(EditarMesaActivity.this, Utilities.MENSAJE_WS_ELIMINACION_FALLIDA, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(EditarMesaActivity.this, Utilities.MENSAJE_WS_ERROR_RESPONSE + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        // permite la comunicacion con los metodos que se implementaron
        requestQueue.add(stringRequest);
    }

}