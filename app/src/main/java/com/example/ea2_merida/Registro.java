package com.example.ea2_merida;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Retrofit.ComunicacionApiRest;
import Retrofit.PostRegistroLogin;

public class Registro extends AppCompatActivity {
    private EditText nombre, apellido, contraseña, dni, emali, comision;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = (EditText) findViewById(R.id.etNom);
        apellido = (EditText) findViewById(R.id.eTApe);
        contraseña = (EditText) findViewById(R.id.eTContraseña);
        dni = (EditText) findViewById(R.id.eTDni);
        emali = (EditText) findViewById(R.id.idEmail);
        comision = (EditText) findViewById(R.id.eTcomision);
        Intent intent = new Intent();
        context = this;
    }

    public void ComprobarConexionRegistro(View v){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if(ni != null && ni.isConnected()){
            registrar();
        }else
            Toast.makeText(getApplicationContext(), "No se pudo realizar registro " +
                    "su Internet esta desconectada", Toast.LENGTH_SHORT).show();
    }

    public void registrar(){
        PostRegistroLogin postRegistroLogin = new PostRegistroLogin();
        if(comision.getText().toString().isEmpty()){
            Toast.makeText(context, "Debe ingresar el numero de Comision", Toast.LENGTH_LONG ).show();
            return;
        }
        if(dni.getText().toString().isEmpty()){
            Toast.makeText(context, "Debe ingresar el numero de DNI", Toast.LENGTH_LONG).show();
            return;
        }
        String emailString, passwordString, lastnameString, nameString;
        nameString = nombre.getText().toString();
        lastnameString = apellido.getText().toString();
        int dniInt = Integer.parseInt(dni.getText().toString());
        emailString = emali.getText().toString();
        passwordString = contraseña.getText().toString();
        int comisionInt = Integer.parseInt(comision.getText().toString());

        postRegistroLogin.setName(nameString);
        postRegistroLogin.setLastname(lastnameString);
        postRegistroLogin.setDni(dniInt);
        postRegistroLogin.setEmail(emailString);
        postRegistroLogin.setPassword(passwordString);
        postRegistroLogin.setComission(comisionInt);


        ComunicacionApiRest comunicacionApiRest = new ComunicacionApiRest(this);
        comunicacionApiRest.enviarPostRegistro(postRegistroLogin);
    }

    public void irLogin(View v){
        Intent intent = new Intent(Registro.this, Login.class);
        startActivity(intent);
    }
}