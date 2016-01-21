package kyon.runners;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by John on 21/01/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {


    GameLoopThread gameLoopThread;
    Game game;
    Soundtrack soundtrack;
    Context c;

    public GamePanel(Context context) {
        super(context);
        c = context;

        // Focus must be on GamePanel so that events can be handled.
        this.setFocusable(true);
        // For intercepting events on the surface.
        this.getHolder().addCallback(this);


    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    public void surfaceCreated(SurfaceHolder holder) {
        // We can now safely start the game loop.
        startGame();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        gameLoopThread.running = false;

        // Shut down the game loop thread cleanly.
        boolean retry = true;
        while(retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    }

    private void startGame(){
        soundtrack = new Soundtrack(c);
        game = new Game(getWidth(), getHeight(), getResources());

        gameLoopThread = new GameLoopThread(this.getHolder(), game);

        gameLoopThread.running = true;
        gameLoopThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // This is for single-touch. For multi-touch use MotionEventCompat.getActionMasked(event);
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            game.touchEvent_actionDown(event);
        }

        if(action == MotionEvent.ACTION_MOVE) {
            game.touchEvent_actionMove(event);

        }

        if(action == MotionEvent.ACTION_UP){
            game.touchEvent_actionUp(event);
        }

        return true;
    }

}
