package com.example.ea2_merida;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText email, password;
    private Button button_login;
    public TextView txtResp;
    public IntentFilter filtro;
    public String token ="";

    static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGED";
    static final String ACTION2 = "android.net.wifi.WIFI_STATE_CHANGED";

    private BroadcastReceiver receiver = new ReceptorOperacion();

    private static final String URI_LOGIN = "http://so-unlam.net.ar/api/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password =(EditText) findViewById(R.id.idcontraseña);
        email= (EditText) findViewById(R.id.idEmail);
        txtResp = (TextView) findViewById(R.id.textrespuesta);
        button_login = (Button) findViewById(R.id.button_login);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        filter.addAction(ACTION2);
        getApplicationContext().registerReceiver(new ConexionInternet(), filter);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if(esValido()){
                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("email", email.getText().toString());
                        obj.put("password", password.getText().toString());
                        Intent i = new Intent(Login.this, ServiceHTTP.class);
                        i.putExtra("uri", URI_LOGIN);
                        i.putExtra("datosJson", obj.toString());
                        startService(i);

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    configurarBroadcastReceiver();
                }
            }
        });

    }

    /*public void ComprobarConexionLogin(View v){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        //Consulto el estado del internet

        if(ni != null && ni.isConnected()){
            logearse();
        }else{
            Toast.makeText(getApplicationContext(),"No se puedo realizar el login, " +
                    "Su Internet esta desconectada", Toast.LENGTH_SHORT).show();
        }
    }*/

    @SuppressLint("LongLogTag")
    public void enviarIntent(){
        Log.i ("El resultado de token es:",token);
        if(token != ""){
            Intent i = new Intent(Login.this, MenuPrincipal.class);
            i.putExtra("token", token);
            startActivity(i);
        }else{
            txtResp.setText("ATENCION! Fallo la conexion con el servidor. Puede que alguno de los campos ingresados no sean valido");
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtResp.setText("");
                }
            }, 5000);
        }
    }

    class ReceptorOperacion extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            try{
                String datosJsonString = intent.getStringExtra("datosJson");
                token = intent.getStringExtra("token");
                Log.i("token para",token);
                JSONObject datosJson = new JSONObject(datosJsonString);
                if(datosJson.toString() == null) return;
                if(token != "" && intent.getStringExtra("uri").equals(URI_LOGIN)){
                    Intent i = new Intent(Login.this, MenuPrincipal.class);
                    i.putExtra("token", token);
                    startActivity(i);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtResp.setText(datosJsonString);
                        }
                    },2000);
                }else{
                    txtResp.setText("ATENCION! Fallo la conexion con el servidor. Puede que alguno de los campos ingresados no sea valido");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtResp.setText("");
                        }
                    }, 2000);
                }
                txtResp.setText(datosJsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void ComprobarConexionRegistro(View v1){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        //compruebo si hay internet antes de pasar a la activity de resgistro
        if(ni != null && ni.isConnected()){
            irRegistro(v1);
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo ingresar al Registro, " +
                    "su internet esta desconectada", Toast.LENGTH_SHORT).show();
        }
    }


    public void irRegistro(View v){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
    }

    private void configurarBroadcastReceiver(){
        filtro = new IntentFilter("android.intent.action.MAIN");
        filtro.addCategory("android.intent.category.LAUNCHER");
        registerReceiver(receiver, filtro);
    }

    protected void onPause(){ super.onPause();}

    public boolean esValido(){
        String campEmail = email.getText().toString();
        String campPass = password.getText().toString();
        boolean valido = true;

        if(campEmail.isEmpty()){
            email.setError("Debe ingresar su E-mail para registrarse");
            valido = false;
        }
        if(campPass.isEmpty() || campPass.length()<8){
            password.setError("Campo password INCORRECTO, recuerde debe ingresar 8 caracteres o mas");
            valido = false;
        }
        return valido;
    }
}