package com.example.ea2_merida;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Paint;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Gusano extends View {
    private Paint paint;
    private float ancho;
    private float largo;
    private float posX=0, posY=0;
    private float anteriorX=0, anteriorY=0;
    private boolean primera = true;
    private Bitmap bitmap;
    private int mWidth;
    private int mHeight;
    private Bitmap pelota;
    private float espacioMovimiento = this.ancho / 135;
    private float centroX;
    private float centroY;
    private float radio;

    public Gusano(Context context){
        super(context);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gusano);
        paint = new Paint();
    }

    public void onSizeChanged(int a, int b, int c, int d){
        mWidth = getWidth() / 7;
        mHeight = getWidth() / 7;
        radio = mWidth /2;
        centroY = posY + radio;
        centroX = posX + radio;
        //redimensiona el bitmap
        pelota = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, true);
    }

    protected  void onDraw(Canvas canvas){
        this.ancho = canvas.getWidth();
        espacioMovimiento = this.ancho/110;
        this.largo = canvas.getHeight();
        this.centroX = posX+radio;
        this.centroY = posY+radio;
        canvas.drawBitmap(pelota,posX,posY,null);
    }

    public boolean mover(float x, float y, List<Obstaculo> listaObs) {
        double margenSuperior = 1.5;
        double margenInferior = -1.5;
        boolean seMovio = false;
        this.centroX = posX + radio;
        this.centroY = posY + radio;
        Log.i("centroX", String.valueOf(centroX));
        Log.i("centroY", String.valueOf(centroY));

        if(primera == true){
            primera = false;
            anteriorX = x;
            anteriorY = y;
            return seMovio;
        }

        List<Boolean> puede = sinObstaculo(listaObs);
        //se mueve si hubo cambios en el eje X
        if(anteriorX != x){
            //derecha
            if(puede.get(3) && x < margenInferior && posX + this.mWidth < this.ancho){
                posX += espacioMovimiento;
                seMovio = true;
            } else if(puede.get(2) && x > margenSuperior && posX > 0){ //izquierda
                posX -= espacioMovimiento;
                seMovio = true;
            }
            anteriorX = x;
        }

        if(anteriorY != y){
            if(puede.get(1) && y < margenInferior && posY > 0){
                posY -= espacioMovimiento;
                seMovio = true;
            } else if (puede.get(0) && y > margenSuperior && posY + this.mHeight < this.largo){
                posY += espacioMovimiento;
                seMovio = true;
            }

            anteriorY = y;
        }
        return seMovio;
    }

    public float getRadio() { return radio; }

    public List<Boolean> sinObstaculo(List<Obstaculo> lista){
        List<Boolean> listaPuede = new ArrayList<Boolean>();
        boolean abajo = true, arriba = true, izquierda = true, derecha = true;

        List<Coordenada> coordenadasIzq = new ArrayList<Coordenada>();
        List<Coordenada> coordenadasDer = new ArrayList<Coordenada>();
        List<Coordenada> coordenadasArriba = new ArrayList<Coordenada>();
        List<Coordenada> coordenadasAbajo = new ArrayList<Coordenada>();
        coordenadasArribaAbajo(coordenadasArriba, coordenadasAbajo);
        coordenadasIzqDer(coordenadasIzq, coordenadasDer);

        for(Obstaculo obs : lista){
            for (Coordenada coor : coordenadasAbajo){
                if(abajo && obs.existeObstaculo(coor.getX(), coor.getY())){
                    abajo = false;
                }
            }

            for(Coordenada coor : coordenadasArriba){
                if(arriba && obs.existeObstaculo( coor.getX(), coor.getY())){
                    arriba = false;
                }
            }

            for(Coordenada coor : coordenadasIzq){
                if(izquierda && obs.existeObstaculo(coor.getX(), coor.getY())){
                    izquierda = false;
                }
            }

            for(Coordenada coor : coordenadasDer){
                if(derecha && obs.existeObstaculo(coor.getX(), coor.getY())){
                    derecha = false;
                }
            }
        }

        listaPuede.add(abajo);
        listaPuede.add(arriba);
        listaPuede.add(izquierda);
        listaPuede.add(derecha);

        return listaPuede;
    }

    public void coordenadasArribaAbajo(List<Coordenada> coordenadasArriba, List<Coordenada> coordenadasAbajo){
        float x;
        for( x = centroX - radio + 3; x < centroX + radio; x += 3){
            Coordenada coordenada = new Coordenada();
            coordenada.setX(x);
            coordenada.establecerY(radio, centroX, centroY, true);
            coordenadasAbajo.add(coordenada);
            Coordenada coordenada2 = new Coordenada();
            coordenada2.setX(x);
            coordenada2.establecerY(radio, centroX, centroY, true);
            coordenadasArriba.add(coordenada2);
        }
    }

    public void coordenadasIzqDer(List<Coordenada> coordenadasIzq, List<Coordenada> coordenadasDer){
        float y;
        for(y = centroY - radio + 3; y < centroY + radio; y += 3){
            Coordenada coordenada = new Coordenada();
            coordenada.setY(y);
            coordenada.establecerX(radio, centroX, centroY, true);
            coordenadasDer.add(coordenada);
            Coordenada coordenada2 = new Coordenada();
            coordenada2.setY(y);
            coordenada2.establecerX(radio, centroX, centroY, true);
            coordenadasIzq.add(coordenada2);
        }
    }

    public float getCentroX() {
        Log.i("getCentroX", String.valueOf(this.centroX));
        return this.centroX;
    }

    public float getCentroY(){
        Log.i("getCentroY", String.valueOf(this.centroY));
        return this.centroY;
    }
}

