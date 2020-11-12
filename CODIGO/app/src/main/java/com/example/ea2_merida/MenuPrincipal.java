package com.example.ea2_merida;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
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
}