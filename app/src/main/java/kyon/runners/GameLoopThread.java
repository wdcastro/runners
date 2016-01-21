package kyon.runners;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by John on 21/01/2016.
 */
public class GameLoopThread extends Thread {

    private final static int MAX_FPS = 40;          		// How many times per second the game should be updated, drawn?
    private final static int MAX_FRAME_SKIPS = 5; 			// Maximum number of frames to be skipped
    private final static int FRAME_PERIOD = 1000 / MAX_FPS; // The frame period

    // Surface holder that can access the physical surface.
    private SurfaceHolder surfaceHolder;

    private Game game;

    // Elapsed game time in milliseconds.
    private long gameTime;

    // Holds the state of the game loop.
    public boolean running;


    public GameLoopThread(SurfaceHolder surfaceholder, Game game){
        super();
        this.surfaceHolder = surfaceholder;
        this.game = game;

    }

    public void run(){
        Canvas canvas;


        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        int framesSkipped;	// number of frames being skipped

        sleepTime = 0;

        while(running){
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder){
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;

                    this.game.Update(this.gameTime);
                    this.game.Draw(canvas);

                    timeDiff = System.currentTimeMillis()-beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if(sleepTime > 0){
                        try{
                            Thread.sleep(sleepTime);
                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }

                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                    // We need to catch up, so we update without drawing the game to the screen.
                    this.game.Update(this.gameTime);

                    sleepTime += FRAME_PERIOD; // Add FRAME_PERIOD to check while condition again.
                    framesSkipped++;
                }

                this.gameTime += System.currentTimeMillis() - beginTime;

            } catch(Exception e){
                e.printStackTrace();
            }
            finally {
                // In case of an exception the surface is not left in an inconsistent state.
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
