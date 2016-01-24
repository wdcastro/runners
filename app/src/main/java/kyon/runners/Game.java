package kyon.runners;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by John on 21/01/2016.
 */
public class Game {

    //touch variables

    public float leftdownx;
    public float leftdowny;
    public float leftinitialx;
    public float leftinitialy;
    public float leftupx;
    public float leftupy;
    public float leftpointerindex = -1;

    public float rightdownx;
    public float rightdowny;
    public float rightinitialx;
    public float rightinitialy;
    public float rightupx;
    public float rightupy;
    public float rightpointerindex = -1;


    public static int screenWidth;
    public static int screenHeight;
    public static float screenDensity;


    private int gameTimeSec;

    //private ScrollingList enemyList;
    //private ScrollingList<Projectile> projectileList = new ScrollingList(10);

    private Runner runner;
    private ArrayList<Projectile> projectileList;


    public static Bitmap runninganimationStrip;
    public static Bitmap jumpinganimationStrip;
    public static Bitmap runningthrowStrip;
    public static Bitmap jumpingthrowStrip;
    public static Bitmap projectileimage;
    public static Bitmap runningScaled;
    public static Bitmap jumpingScaled;
    public static Bitmap runningthrowScaled;
    public static Bitmap jumpingthrowScaled;
    public static Bitmap projectileScaled;


    private Paint paintForImages;

    public Game(int screenWidth, int screenHeight, Resources resources){
        Game.screenWidth = screenWidth;
        Game.screenHeight = screenHeight;
        Game.screenDensity = resources.getDisplayMetrics().density;

        paintForImages = new Paint();
        paintForImages.setFilterBitmap(true);

        runner = new Runner(0);

        projectileList = new ArrayList<Projectile>(10);



        this.LoadContent(resources);
        this.ResetGame(); //reset scores



    }

    public void Update(long gameTime){
        gameTimeSec = (int) gameTime/1000;


        runner.update();

        for(int i = 0; i < projectileList.size(); i++){
            projectileList.get(i).update();

        }


    }

    public void Draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        runner.draw(canvas);

        for(int i = 0; i < projectileList.size(); i++){
            Projectile d = projectileList.get(i);
            if(d.expire){
                projectileList.remove(i);
            } else {
                d.draw(canvas);
            }
        }

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);

        //canvas.drawBitmap(projectileScaled, 50,50,paintForImages);

        canvas.drawText("Game is running: " + gameTimeSec + " sec", screenWidth/4, screenHeight/2, paint);
        canvas.drawText("frame: " + runner.getFrameNumber(), screenWidth/4, screenHeight/2+30, paint);
        canvas.drawText("y: " + runner.y, screenWidth/4, screenHeight/2+60, paint);
        canvas.drawText("is attacking:" + runner.isAttacking, screenWidth/4, screenHeight/2+90,paint);
        canvas.drawText("is jumping:"+runner.isJumping, screenWidth/4, screenHeight/2+120, paint);
        canvas.drawText("projectile list size:"+projectileList.size(), screenWidth/4, screenHeight/2+150, paint);
        canvas.drawText("delta left x"+projectileList.size() + " delta left y", screenWidth/4, screenHeight/2+180, paint);
        canvas.drawText("delta right x"+projectileList.size() + " delta right y", screenWidth/4, screenHeight/2+210, paint);


    }

    /**
     * Load files.
     */
    private void LoadContent(Resources resources){
        runninganimationStrip = BitmapFactory.decodeResource(resources, R.drawable.runninganimation);
        runningScaled = Bitmap.createScaledBitmap(runninganimationStrip, (screenHeight/2)*7, screenHeight/2, false);
        runninganimationStrip.recycle();
        jumpinganimationStrip = BitmapFactory.decodeResource(resources, R.drawable.jumpinganimation);
        jumpingScaled = Bitmap.createScaledBitmap(jumpinganimationStrip, (screenHeight/2)*12, screenHeight/2, false);
        jumpinganimationStrip.recycle();
        runningthrowStrip = BitmapFactory.decodeResource(resources, R.drawable.runningthrow);
        runningthrowScaled = Bitmap.createScaledBitmap(runningthrowStrip, (screenHeight/2)*6, screenHeight/2, false);
        runningthrowStrip.recycle();
        jumpingthrowStrip = BitmapFactory.decodeResource(resources, R.drawable.jumpingthrow);
        jumpingthrowScaled = Bitmap.createScaledBitmap(jumpingthrowStrip, (screenHeight/2)*8, screenHeight/2, false);
        jumpingthrowStrip.recycle();
        projectileimage = BitmapFactory.decodeResource(resources, R.drawable.projectile);
        projectileScaled = Bitmap.createScaledBitmap(projectileimage, (screenHeight/640)*75, (screenHeight/640)*25, false);
        projectileimage.recycle();
        //will have to rescale the strips to the screen size



    }


    /**
     * For (re)setting some game variables before game can start.
     */
    private void ResetGame(){

    }

    public void touchEvent_actionDown(MotionEvent event, int id){

        float x = MotionEventCompat.getX(event, id);
        float y = MotionEventCompat.getY(event, id);

        if(x > screenWidth/2 && rightpointerindex == -1){
            rightdownx = x;
            rightdowny = y;
            rightpointerindex = id;
        }
        if(x < screenWidth/2 && leftpointerindex == -1){
            leftdownx = x;
            leftdowny = y;
            leftpointerindex = id;
        }
        //while down, jump IDEA
    }
    public void touchEvent_actionPointerDown(MotionEvent event, int id){
        float x = MotionEventCompat.getX(event, id);
        float y = MotionEventCompat.getY(event, id);

        if(x > screenWidth/2 && rightpointerindex == -1){
            rightdownx = x;
            rightdowny = y;
            rightpointerindex = id;
        }
        if(x < screenWidth/2 && leftpointerindex == -1){//if there isnt already a left touch
            leftdownx = x;
            leftdowny = y;
            leftpointerindex = id;
        }
        //while down, jump IDEA
    }

    /**
     * When moving on screen is detected.
     *
     * @param event MotionEvent
     */
    public void touchEvent_actionMove(MotionEvent event, int id){



    }

    /**
     * When touch on screen is released.
     *
     * @param event MotionEvent
     */
    public void touchEvent_actionUp(MotionEvent event, int id){

        float x = MotionEventCompat.getX(event, id);
        float y = MotionEventCompat.getY(event, id);

        if(id == leftpointerindex){
            if(!runner.isJumping && !runner.isAttacking){
                runner.isJumping = true;
                runner.currentFrame = 0;
                leftpointerindex = -1;
            }
        } else if (id == rightpointerindex){


                //can use strokethreshold to determine comfortable play
                if(!runner.isAttacking){
                    runner.isAttacking = true;

                    projectileList.add(new Projectile(runner.getFrameWidth(),runner.y+(runner.getFrameHeight()/2),50));
                    runner.currentFrame = 0;
                }


            /*if(downy-upy < 0){//point of touch is less than point of release = up to down
                //do nothing
            }*/
            rightpointerindex = -1;
        }


    }
}
