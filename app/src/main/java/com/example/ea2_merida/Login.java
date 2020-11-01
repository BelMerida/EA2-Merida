package com.example.ea2_merida;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import Retrofit.ComunicacionApiRest;
import Retrofit.PostRegistroLogin;

import javax.sql.ConnectionEvent;


public class Login extends AppCompatActivity {
    private EditText email, password;
    Context context;
    static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGED";
    static final String ACTION2 = "android.net.wifi.WIFI_STATE_CHANGED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        context =this;
        password =(EditText) findViewById(R.id.idcontraseña);
        email= (EditText) findViewById(R.id.idEmail);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        filter.addAction(ACTION2);
        getApplicationContext().registerReceiver(new ConexionInternet(), filter);
    }

    public void ComprobarConexionLogin(View v){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        //Consulto el estado del internet

        if(ni != null && ni.isConnected()){
            logearse();
        }else{
            Toast.makeText(getApplicationContext(),"No se puedo realizar el login, " +
                    "Su Internet esta desconectada", Toast.LENGTH_SHORT).show();
        }
    }

    public void logearse(){
        PostRegistroLogin postRegistroLogin = new PostRegistroLogin();
        postRegistroLogin.setEmail(email.getText().toString());
        postRegistroLogin.setPassword(password.getText().toString());

        ComunicacionApiRest comunicacionApiRest = new ComunicacionApiRest();
        comunicacionApiRest.enviarLogin(postRegistroLogin, Login.this);
    }

    public void ComprobarConexionRegistro(View v){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        //compruebo si hay internet antes de pasar a la activity de resgistro
        if(ni != null && ni.isConnected()){
            irRegistro(v);
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo ingresar al Registro, " +
                    "su internet esta desconectada", Toast.LENGTH_SHORT).show();
        }
    }

    public void irMenu(){
        Intent intent = new Intent(Login.this, MenuPrincipal.class );
        startActivity(intent);
    }

    public void irRegistro(View v){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
        finish();
    }
}