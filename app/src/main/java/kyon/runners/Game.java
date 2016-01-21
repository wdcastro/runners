package kyon.runners;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by John on 21/01/2016.
 */
public class Game {

    public static int screenWidth;
    public static int screenHeight;
    public static float screenDensity;


    private int gameTimeSec;

    private ArrayList<Enemy> enemyList;

    private Runner runner;


    public static Bitmap runninganimationStrip;
    public static Bitmap jumpinganimationStrip;
    public static Bitmap runningthrowStrip;
    public static Bitmap runnerStrip;
    public static Bitmap jumpingStrip;
    public static Bitmap throwingStrip;

    private Paint paintForImages;

    public Game(int screenWidth, int screenHeight, Resources resources){
        Game.screenWidth = screenWidth;
        Game.screenHeight = screenHeight;
        Game.screenDensity = resources.getDisplayMetrics().density;

        paintForImages = new Paint();
        paintForImages.setFilterBitmap(true);

        runner = new Runner(0);

        this.LoadContent(resources);
        this.ResetGame();


    }

    public void Update(long gameTime){
        gameTimeSec = (int) gameTime/1000;


        runner.update();

    }

    public void Draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        runner.draw(canvas);



        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);



        canvas.drawText("Game is running: " + gameTimeSec + " sec", screenWidth/4, screenHeight/2, paint);
        canvas.drawText("frame: " + runner.getFrameNumber(), screenWidth/4, screenHeight/2+30, paint);
        canvas.drawText("y: " + runner.y, screenWidth/4, screenHeight/2+60, paint);

    }

    /**
     * Load files.
     */
    private void LoadContent(Resources resources){
        runninganimationStrip = BitmapFactory.decodeResource(resources, R.drawable.runninganimation);
        jumpinganimationStrip = BitmapFactory.decodeResource(resources, R.drawable.jumpinganimation);
        runningthrowStrip = BitmapFactory.decodeResource(resources, R.drawable.runningthrow);


        runnerStrip = Bitmap.createScaledBitmap(runninganimationStrip, (screenHeight/2)*7, screenHeight/2, false);
        jumpingStrip = Bitmap.createScaledBitmap(jumpinganimationStrip, (screenHeight/2)*12, screenHeight/2, false);
        throwingStrip = Bitmap.createScaledBitmap(runningthrowStrip, (screenHeight/2)*6, screenHeight/2, false);
        //will have to rescale the strips to the screen size



    }


    /**
     * For (re)setting some game variables before game can start.
     */
    private void ResetGame(){
    }

    public void touchEvent_actionDown(MotionEvent event){

    }

    /**
     * When moving on screen is detected.
     *
     * @param event MotionEvent
     */
    public void touchEvent_actionMove(MotionEvent event){
        /*if(!runner.isJumping){
            runner.isAttacking = true;
        }*/

    }

    /**
     * When touch on screen is released.
     *
     * @param event MotionEvent
     */
    public void touchEvent_actionUp(MotionEvent event){

        if(!runner.isJumping && !runner.isAttacking){
            runner.isJumping = true;
            runner.currentFrame = 0;
        }
    }
}
