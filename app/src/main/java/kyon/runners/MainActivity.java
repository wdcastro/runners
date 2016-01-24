package kyon.runners;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;


public class MainActivity extends Activity {


    private boolean showingMainMenu;
    private GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        showingMainMenu = true;
    }


    @Override
    public void onBackPressed(){
        if(!showingMainMenu){
            showingMainMenu = true;
            // Stop game loop
            gamePanel.surfaceDestroyed(null);
            gamePanel.soundtrack.stopMusic();
            setContentView(R.layout.activity_main);
        }else{
            // Quit
            super.onBackPressed();
        }
    }

    // Start game on click
    public void onClickStartGame(View v){
        showingMainMenu = false;

        // Start and show game.
        gamePanel = new GamePanel(this);
        setContentView(gamePanel);
    }


}
