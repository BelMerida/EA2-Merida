package com.example.ea2_merida;

import android.content.Context;
import android.graphics.Canvas;
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
    }

    public void onDraw(Canvas canvas){
        radio= getWidth();
        centroX=2*radio-10;
        centroY= getHeight()-2*radio;
        canvas.drawCircle(centroX,centroY,radio,paint);
    }

    public boolean isCovered(float gusanoX, float gusanoY){
        float minimoX = this.centroX-10;
        float minimoY = this.centroY-10;
        float maximoX = this.centroX+10;
        float maximoY = this.centroY+10;
        Log.i("pminimoX",String.valueOf(minimoX));
        Log.i("pmCentroGusanoX", String.valueOf(gusanoX));
        Log.i("pmaximoX",String.valueOf(maximoX));
        Log.i("pminimoY",String.valueOf(minimoY));
        Log.i("pmCentroGusanoY",String.valueOf(gusanoY));
        Log.i("pmaximoY",String.valueOf(maximoY));

        if(gusanoX > minimoX && gusanoX < maximoX && gusanoY > minimoY && gusanoY < maximoY){
            return true;
        }
        return false;
    }
}
