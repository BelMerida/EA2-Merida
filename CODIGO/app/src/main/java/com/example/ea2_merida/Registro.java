package com.example.ea2_merida;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {
    private EditText nombre, apellido, password, dni, email, comision;
    private Context context;
    private TextView txtResp;
    private Button btnRegistrar;
    public String token = "";
    public IntentFilter filtro;
    private BroadcastReceiver receiverRegistro = new ReceptorOperacionRegistro();

    protected  void onDestroy(){ super.onDestroy();}

    protected void onStop(){ super.onStop();}

    private BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();
            onNetworkChange(ni, context);
        }

        private void onNetworkChange(NetworkInfo networkInfo, Context context){
            if(networkInfo != null && networkInfo.isConnected()){
                Log.d("MenuActivity", "CONNECTED");
            }else{
                Log.d("MenuActivity", "DISCONNECTED");
                Toast.makeText(context.getApplicationContext(), "ATENCION! No hay acceso a Internet", Toast.LENGTH_LONG).show();
            }
        }
    };

    private static final String URI_REGISTRO = "http://so-unlam.net.ar/api/api/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        nombre = (EditText) findViewById(R.id.etNom);
        apellido = (EditText) findViewById(R.id.eTApe);
        dni = (EditText) findViewById(R.id.eTDni);
        email = (EditText) findViewById(R.id.idEmail);
        password = (EditText) findViewById(R.id.eTContraseña);
        comision = (EditText) findViewById(R.id.eTcomision);
        txtResp = (TextView) findViewById(R.id.textrespuesta);
        btnRegistrar = (Button) findViewById(R.id.btnRegistro);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(esValido()){
                    //Log.i();
                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("env", "PROD");
                        obj.put("name", nombre.getText().toString());
                        obj.put("lastname", apellido.getText().toString());
                        obj.put("dni", Integer.parseInt(dni.getText().toString()));
                        obj.put("email", email.getText().toString());
                        obj.put("password", password.getText().toString());
                        obj.put("commission", Integer.parseInt(comision.getText().toString()));

                        Intent i = new Intent(Registro.this, ServiceHTTP.class);

                        i.putExtra("uri", URI_REGISTRO);
                        i.putExtra("datosJson", obj.toString());

                        startService(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                configurarBroadcastReceiverRegistro();
            }
        });
    }


    private void configurarBroadcastReceiverRegistro(){
        filtro = new IntentFilter("android.intent.action.MAIN");
        filtro.addCategory("android.intent.category.LAUNCHER");
        registerReceiver(receiverRegistro, filtro);
    }

    class ReceptorOperacionRegistro extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String datosJsonString = intent.getStringExtra("datosJson");
                token = intent.getStringExtra("token");
                JSONObject datosJson = new JSONObject(datosJsonString);
                if(datosJson.toString() == null) return;
                if(token != "" && intent.getStringExtra("uri").equals( URI_REGISTRO)){
                    Intent i = new Intent(Registro.this, Login.class);
                    i.putExtra("token", token );
                    startActivity(i);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtResp.setText(token);
                        }
                    }, 3000);
                }else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtResp.setText("ATENCION! Fallo la conexion con el servidor, algunos de los campos ingresador no son validos");
                        }
                    }, 2000);
                }
                txtResp.setText(datosJsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean esValido(){
        String campNombre = nombre.getText().toString();
        String campApellido = apellido.getText().toString();
        String campDni = dni.getText().toString();
        String campEmail = email.getText().toString();
        String campPass = password.getText().toString();
        String campComi = comision.getText().toString();

        boolean valido = true;
        if(campNombre.isEmpty()){
            nombre.setError("Debe ingresar su nombre para registrarse");
            valido = false;
        }
        if(campApellido.isEmpty()){
            apellido.setError("Debe ingresar su apellido para registrarse");
            valido = false;
        }
        if(campDni.isEmpty() || campDni.length() > 8){
            dni.setError("Numero de dni incorrecto, recuerde que no debe ingresar mas de 8 caracteres");
            valido = false;
        }
        if(campEmail.isEmpty()){
            email.setError("Debe ingresar su email para registrarse");
            valido = false;
        }
        if(campPass.isEmpty() || campPass.length() < 8){
            password.setError("Campo Password incorrecto, recuerde debe ingresar 8 caracteres o mas");
            valido = false;
        }
        if(campComi.isEmpty()){
            comision.setError("Debe ingresar la comision para registrarse");
            valido = false;
        }

        return valido;
    }

}