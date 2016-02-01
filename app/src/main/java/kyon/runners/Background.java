package kyon.runners;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;


/**
 * Created by John on 27/01/2016.
 */
public class Background {

    /*

    OI RETARD USE 2 RECTS TO DRAW TWO HALVES OF THE BACKGROUND TO LOOP
     */

    private Bitmap bg;
    private Bitmap bgScaled;
    private Bitmap torches;
    private Bitmap torchesScaled;
    private Rect frameToDraw;
    private RectF whereToDraw;
    private String stage;
    private Resources r;

    public Background(String stage, Resources resources){
        this.stage = stage;
        r = resources;
        frameToDraw = new Rect(0,0,Game.screenWidth, Game.screenHeight);
        whereToDraw = new RectF(0,0,Game.screenWidth, Game.screenHeight);



    }

    public void loadContent(){
        switch(stage){
            case "forest":
               BitmapFactory.Options options = new BitmapFactory.Options();
               options.inPreferredConfig = Bitmap.Config.RGB_565;
               bg = BitmapFactory.decodeResource(r, R.drawable.backgroundstars, options);
               bgScaled = Bitmap.createScaledBitmap(bg, Game.screenHeight * 3, Game.screenHeight, false);
               bg.recycle();
               // BitmapFactory.Options op2 = new BitmapFactory.Options();

                torches = BitmapFactory.decodeResource(r, R.drawable.torches);
                torchesScaled = Bitmap.createScaledBitmap(torches, Game.screenHeight * 3, Game.screenHeight, false);
                torches.recycle();
                Log.d("debug", "stage is "+ stage);

        }
    }

    public void update(){
        if(frameToDraw.right>=Game.screenHeight*3){
            frameToDraw.set(0,0,Game.screenWidth, Game.screenHeight);

        } else {
            int currentx = frameToDraw.left;
            frameToDraw.set(currentx+100, 0, currentx+Game.screenWidth+100, Game.screenHeight);
        }
    }

    public void draw(Canvas canvas){
        Paint paintforimages = new Paint();
        paintforimages.setFilterBitmap(true);
        canvas.drawBitmap(bgScaled,0,0,paintforimages);
        canvas.drawBitmap(torchesScaled, frameToDraw, whereToDraw, null);
    }
}
