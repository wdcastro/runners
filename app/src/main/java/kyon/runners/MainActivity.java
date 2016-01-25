package kyon.runners;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;


public class MainActivity extends Activity {


    private boolean showingMainMenu;
    private GamePanel gamePanel;

    public File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug", "isWritable "+isExternalStorageWritable());
        f = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.txt");
        String path = f.getAbsolutePath();
        Log.d("debug", "file created "+path);
        String s = "test";
        OutputStream os;
        try {
            os = new BufferedOutputStream(new FileOutputStream(f));
            Log.d("debug", "stream open");
            byte[] data = s.getBytes();
            String teststring = new String(data);
            Log.d("debug", teststring);

            os.write(data);
            os.flush();

            os.close();
            Log.d("debug", "written");
        }catch(Exception e){
            Log.e("errors", e.toString());
        }
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
        if(isExternalStorageReadable()){//need to read songs from external memory, check
            showingMainMenu = false;

            // Start and show game.
            gamePanel = new GamePanel(this);
            setContentView(gamePanel);
        }
    }


//code for checking if external storage is read writable
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
