package com.ista.apivolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ista.apivolley.model.Clientes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> datos= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lstClientes);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(arrayAdapter);
        //Recibir datos desde api.
        getDatos();


    }

    private void getDatos(){
        String url="http://192.168.18.5:8081/api/clientes";//endpoint.
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Recibir el Json y pasar a Publicacion.
                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //MANEJAMOS EL ERROR.
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(error.getMessage());
            }
        }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);//hacemos la peticion al API

        }

    private void pasarJson( JSONArray array){

        for(int i=0;i<array.length();i++){
            JSONObject json=null;
            Clientes cliente=new Clientes();

            try {
                json=array.getJSONObject(i);
                cliente.setId(json.getInt("id"));//como viene del API
                cliente.setNombre(json.getString("nombre"));
                cliente.setApellido(json.getString("apellido"));
                cliente.setEmail(json.getString("email"));
                datos.add("Cliente:"+" "+cliente.getNombre()+" "+cliente.getApellido());
                datos.add("Email"+" "+cliente.getEmail());
                //datos.add(cliente.getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        arrayAdapter.notifyDataSetChanged();
    }


}