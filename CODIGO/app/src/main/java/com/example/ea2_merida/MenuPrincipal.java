package com.example.ea2_merida;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        //agregarEvento("El usuario se logeo", "login ok");
    }

    public void iniciarJuego(View view){
        Bundle extras = getIntent().getExtras();
        String token = extras.getString("token");
        Intent intent = new Intent(MenuPrincipal.this, Juego.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void verActividadSensores(View view){
        Bundle extras = getIntent().getExtras();
        String token = extras.getString("token");
        Intent intent = new Intent(MenuPrincipal.this, ValorSensor.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    private void agregarEvento(String descripcion, String tipoEvento){
        Bundle extras = getIntent().getExtras();
        String token = extras.getString("token");

        JSONObject obj = new JSONObject();
        try{
            obj.put("env", "PROD");
            obj.put("type_events", tipoEvento);
            obj.put("descripcion", descripcion);
            Intent i = new Intent(MenuPrincipal.this, ServiceHTTP.class);
            i.putExtra("uri", "http://so-unlam.net.ar/api/api/event");
            i.putExtra("token", token);
            i.putExtra("datosJson", obj.toString());

            startService(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}