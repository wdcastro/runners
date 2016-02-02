package kyon.runners;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    public float leftpointerindex = -1;

    public float rightdownx;
    public float rightdowny;
    public float rightpointerindex = -1;

    private boolean isSongSelect;

    private String currentmap = "testmap";



    public static int screenWidth;
    public static int screenHeight;
    public static float screenDensity;

    private Runner runner;
    private ArrayList<Projectile> projectileList;



    public static Bitmap projectileimage;
    public static Bitmap projectileScaled;
    public Background bg;
    public Soundtrack soundtrack;


    public Game(int screenWidth, int screenHeight, Resources resources){
        Game.screenWidth = screenWidth;
        Game.screenHeight = screenHeight;
        Game.screenDensity = resources.getDisplayMetrics().density;





        soundtrack = new Soundtrack(GamePanel.c);



        enterGamePlay(resources);

    }

    public void Update(){


        bg.update();
        runner.update();
        soundtrack.update();

        for(int i = 0; i < projectileList.size(); i++){
            projectileList.get(i).update();

        }


    }

    public void enterSongSelect(){
        isSongSelect = true;
    }

    public void enterGamePlay(Resources resources){
        isSongSelect = false;
        bg = new Background("forest");
        runner = new Runner();
        projectileList = new ArrayList<Projectile>(10);
        this.LoadContent(resources);
        this.ResetGame(); //reset scores

    }

    public void Draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        bg.draw(canvas);
        runner.draw(canvas);

        for(int i = 0; i < projectileList.size(); i++){
            Projectile d = projectileList.get(i);
            if(d.expire){
                projectileList.remove(i);
            } else {
                d.draw(canvas);
            }
        }

    }

    /**
     * Load files.
     */
    private void LoadContent(Resources resources){

        projectileimage = BitmapFactory.decodeResource(resources, R.drawable.projectile);
        projectileScaled = Bitmap.createScaledBitmap(projectileimage, (screenHeight/640)*75, (screenHeight/640)*25, false);
        projectileimage.recycle();
        runner.loadContent(resources);
        bg.loadContent(resources);
        soundtrack.loadMap(currentmap);
        soundtrack.loadSong();
        soundtrack.loadNotes();

        //will have to rescale the strips to the screen size

    }


    /**
     * For (re)setting some game variables before game can start.
     */
    private void ResetGame(){

    }

    public void stop(){
        soundtrack.stopMusic();
    }

    public void touchEvent_actionDown(MotionEvent event, int id){
        if(!soundtrack.isPlaying){
            soundtrack.startMusic();
        }

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
    }

    /**
     * When moving on screen is detected.
     *
     * @param event MotionEvent
     */
    public void touchEvent_actionMove(MotionEvent event, int id){

        float x = MotionEventCompat.getX(event, id);
        float y = MotionEventCompat.getY(event, id);

        if(id == leftpointerindex){
            if(Math.abs(leftdownx-x)<screenWidth/8 && Math.abs(leftdowny-y)>screenHeight/4 ){//minimum swipe threshold
                if(leftdowny>y && Math.abs(leftdowny-y)>screenHeight/6){//initial is greater than now, down to up
                    if(!runner.isJumping){// && !runner.isAttacking
                        runner.isJumping = true;
                        runner.currentFrame = 0;
                    }
                }
            }


        }

        if(id==rightpointerindex){
            if(Math.abs(rightdowny-y)<screenHeight/4 && Math.abs(rightdownx-x)>screenWidth/8){
                if(rightdownx<x){//initial is less than now, left to right
                    if(!runner.isAttacking){
                        runner.isAttacking = true;

                        projectileList.add(new Projectile(runner.getFrameWidth(),runner.y+(runner.getFrameHeight()/2),50));
                        runner.currentFrame = 0;
                    }

                }
                if(rightdownx>x){//initial is greater than now, right to left

                }
            }
        }

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
            leftpointerindex = -1;//reset pointer on exit

        } else if (id == rightpointerindex){
            rightpointerindex = -1;
        }


    }
}
