package com.example.ea2_merida;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.graphics.Paint;
import android.view.View;

public class Manzana extends View{
    Paint paint;
    float centroX;
    float centroY;
    float radio;

    public Manzana(Context context){
        super(context);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        radio= getWidth();
        centroX=2*radio-10;
        centroY= getHeight()-2*radio;
        canvas.drawCircle(100,800,30,paint);
    }

    public boolean isCovered(float pelotaX, float pelotaY){
        float minimoX = 70;
        float minimoY = 700;
        float maximoX = 200;
        float maximoY = 900;
        Log.i("pminimoX",String.valueOf(minimoX));
        Log.i("pmCentroGusanoX", String.valueOf(pelotaX));
        Log.i("pmaximoX",String.valueOf(maximoX));
        Log.i("pminimoY",String.valueOf(minimoY));
        Log.i("pmCentroGusanoY",String.valueOf(pelotaY));
        Log.i("pmaximoY",String.valueOf(maximoY));

        if(pelotaX > minimoX && pelotaX < maximoX && pelotaY > minimoY && pelotaY < maximoY){
            return true;
        }
        return false;
    }
}
