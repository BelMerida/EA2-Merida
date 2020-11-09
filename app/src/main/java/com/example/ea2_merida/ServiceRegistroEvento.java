package com.example.ea2_merida;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONObject;
import org.json.JSONException;

import static java.lang.Thread.sleep;


public class ServiceRegistroEvento extends IntentService {
    // static List<String> descripciones= new ArrayList<String>(),type_events=new ArrayList<String>();
    static boolean enEjecucion = true;
    static Queue<String> descripciones = new LinkedList<String>(), type_events = new LinkedList<String>();
    public ServiceRegistroEvento() {
        super("ServiceRegistroEvento");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        while (enEjecucion) {

            while (!descripciones.isEmpty() && !type_events.isEmpty()) {
                String descripcion = descripciones.poll();
                String type_event = type_events.poll();

            }
        }
        enEjecucion = true;
    }

    public static void agregarEvento(String descripcion, String type_event) {
        descripciones.add(descripcion);
        type_events.add(type_event);
    }

    public static void detener() {
        enEjecucion = false;
    }
}
