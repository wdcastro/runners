package kyon.runners;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

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



    private int frameCount = 7;


    public boolean testPos = true;

    public boolean isJumping = false;
    public boolean isAttacking = false;
    public boolean isRunning = true;

    private Bitmap striptodraw;

    public int currentFrame;

    public Runner (int x){
        this.x = x;
        this.y = (Game.screenHeight/2)-(frameHeight/2);
        originaly = this.y;
        jumpHeight = Game.screenHeight/6;

        lastFrameUpdate = System.currentTimeMillis();




    }

    public void update(){
        long time = System.currentTimeMillis();
        if(lastFrameUpdate+frameDuration<time) {
            getCurrentFrame();
            lastFrameUpdate=time;
        }

        //need to update frameCount after checking for actions

    }

    public void getCurrentFrame(){
        //jumping code
        if(isJumping){ //and is not attacking to come later
            striptodraw = Game.jumpingStrip;
            frameCount = 12;
            if(currentFrame<6 && y>originaly-jumpHeight){
                y-=jumpHeight/6;
            } else if(y<=originaly){
                y+=jumpHeight/6;
            }


        } else {//not jumping and not attacking
            striptodraw = Game.runnerStrip;
            frameCount = 7;
        }
        if(currentFrame < frameCount-1){
            currentFrame++;
        }
        else{
            //change back to default run if in different states
            if(isAttacking){
                isAttacking = false;
            } else if(isJumping){
                isJumping = false;
            }
            currentFrame = 0;
        }

        //move and crop functions
        whereToDraw.set(x,y,frameWidth+x, frameHeight+y);
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;




    }

    public int getFrameNumber(){
        return currentFrame;
    }

    public void draw(Canvas canvas){

        //animation code
        canvas.drawBitmap(striptodraw, frameToDraw, whereToDraw, null);
        /*if(testPos){
            canvas.drawBitmap(Game.pos1,30,30,null);
        }
        else {
           canvas.drawBitmap(Game.pos2,30,30,null);
        }*/
        //remove the previous runner
        //add new runner

    }

}
