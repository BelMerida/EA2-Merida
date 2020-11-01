package com.example.ea2_merida;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConexionInternet extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!verificarInternet(context)){
            Toast.makeText(context, "Internet desconectada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Internet conectada", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verificarInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()){
            return true;
        } else {
            return false;
        }
    }
}
