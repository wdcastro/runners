package kyon.runners;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by John on 22/01/2016.
 */
public class Projectile {

    public float x;
    public float y;
    public float speed;
    public boolean expire;

    public Projectile(float x, float y, float s){
        this.x = x;
        this.y = y;
        this.speed = s;
        expire = false;
    }

    public void update(){
        if(x < (Game.screenWidth*7)/8){
            x +=speed;
        } else {
            expire = true;
        }
    }

    public void getCurrentFrame(){


    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(Game.projectileScaled, x, y, paint);
    }
}
