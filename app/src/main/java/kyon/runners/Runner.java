package kyon.runners;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by John on 21/01/2016.
 */
public class Runner {

    private final static int frameWidth = Game.screenHeight/2;
    private final static int frameHeight = Game.screenHeight/2;
    private final static int frameDuration = 25; //how long each frame lasts, fps codes
    private long lastFrameUpdate;

    //Runner position on screen
    public float x = 0;
    public float y = 0;
    public float originaly;
    public float jumpHeight;
    private Rect frameToDraw = new Rect(0,0, frameWidth, frameHeight);
    private RectF whereToDraw = new RectF(0,0, frameWidth, frameHeight);

    //Runner animation variables
    public static Bitmap runninganimationStrip;
    public static Bitmap jumpinganimationStrip;
    public static Bitmap runningthrowStrip;
    public static Bitmap jumpingthrowStrip;

    public static Bitmap runningScaled;
    public static Bitmap jumpingScaled;
    public static Bitmap runningthrowScaled;
    public static Bitmap jumpingthrowScaled;



    private int frameCount = 7;

    public boolean isJumping = false;
    public boolean isAttacking = false;

    private Bitmap striptodraw;

    public int currentFrame;


    public Runner (){
        this.x = 0;
        this.y = (Game.screenHeight/2)-(frameHeight/2);
        originaly = this.y;
        jumpHeight = Game.screenHeight/6;

        lastFrameUpdate = System.currentTimeMillis();




    }

    public void loadContent(Resources resources){
        runninganimationStrip = BitmapFactory.decodeResource(resources, R.drawable.run);
        runningScaled = Bitmap.createScaledBitmap(runninganimationStrip, (Game.screenHeight/2)*10, Game.screenHeight/2, false);
        runninganimationStrip.recycle();
        jumpinganimationStrip = BitmapFactory.decodeResource(resources, R.drawable.jumpinganimation);
        jumpingScaled = Bitmap.createScaledBitmap(jumpinganimationStrip, (Game.screenHeight/2)*12, Game.screenHeight/2, false);
        jumpinganimationStrip.recycle();
        runningthrowStrip = BitmapFactory.decodeResource(resources, R.drawable.runningthrowcut);
        runningthrowScaled = Bitmap.createScaledBitmap(runningthrowStrip, (Game.screenHeight/2)*6, Game.screenHeight/2, false);
        runningthrowStrip.recycle();
        jumpingthrowStrip = BitmapFactory.decodeResource(resources, R.drawable.jumpingthrow);
        jumpingthrowScaled = Bitmap.createScaledBitmap(jumpingthrowStrip, (Game.screenHeight/2)*8, Game.screenHeight/2, false);
        jumpingthrowStrip.recycle();
    }

    public int getFrameWidth(){
        return frameWidth;
    }

    public int getFrameHeight(){
        return frameHeight;
    }

    public void update(){
        long time = System.currentTimeMillis();
        if(lastFrameUpdate+frameDuration<time) {
            getCurrentFrame();
            lastFrameUpdate=time;
        }

    }

    public void getCurrentFrame(){
        //jumping code
        if(isAttacking){
            if(isJumping){
                striptodraw = jumpingthrowScaled;
                frameCount = 8;
            } else {
                striptodraw = runningthrowScaled;
                frameCount = 6;
            }
        } else{
            if(isJumping){
                striptodraw = jumpingScaled;
                frameCount = 12;
                if(currentFrame<6 && y>originaly-jumpHeight && !isAttacking){
                    y-=jumpHeight/6;
                } else if(y<=originaly && !isAttacking){
                    y+=jumpHeight/6;
                }
            } else {
                striptodraw = runningScaled;
                frameCount = 10;
            }
        }
        if(currentFrame < frameCount-1){
            currentFrame++;
        }
        else{//run out of frames in current action
            //change back to default run if in different states
            if(isAttacking){//if action was attack, remove attack
                isAttacking = false;
                if(isJumping){//if the attack ended while jumping, go back to jumping
                    striptodraw = jumpingScaled;
                }
            } else if(isJumping){//if action was jumping, remove jumping
                isJumping = false;
            }
            currentFrame = 0;//go to beginning of strip
        }

        //move and crop functions
        whereToDraw.set(x,y,frameWidth+x, frameHeight+y);
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;




    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(striptodraw, frameToDraw, whereToDraw, null);

    }

}
