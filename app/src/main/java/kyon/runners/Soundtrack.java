package kyon.runners;

import android.content.Context;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by John on 21/01/2016.
 */
public class Soundtrack {

    //needs to read sound package
    //needs to read notes
    //needs to play sound obviously
    //will be made in game panel for the context
    //will interact with game to spawn enemy prolly pass arraylist to game

    private MediaPlayer music;
    private int[] noteTimings;
    private int currentnoteCount;
    public static boolean isPlaying;
    String directory;
    Context c;


    public Soundtrack(Context context){
        c= context;

        isPlaying = false;
        currentnoteCount = 0;

    }

    public void loadMap(String mapname){
        directory = MainActivity.external.toString()+"/"+mapname;
        Log.d("pathdebug", "map folder "+ directory);
    }


    public void loadSong(){
        String song = directory+"/song.mp3";
        Log.d("pathdebug", "song path "+song);
        Uri songpath = Uri.parse(song);
        music = MediaPlayer.create(c, songpath);
    }

    public void loadNotes(){

        String notes = directory+"/mapdata.txt";
        Uri notespath = Uri.parse(notes);

        Log.d("musicdebug", String.valueOf(music.getDuration()));
        String c;
        int currentline = -1;//-1 is reading header
        try{
            BufferedReader in = new BufferedReader(new FileReader(notespath.getPath()));
            Log.d("notesdebug", "reader created");
            if(currentline == -1){
                c=in.readLine();
                noteTimings = new int[Integer.parseInt(c)];
                currentline++;
                Log.d("notesdebug", "line count is "+c);
            }
            while((c=in.readLine())!= null){
                noteTimings[currentline] = Integer.parseInt(c);
                Log.d("notesdebug","timing "+c);
                currentline++;
            }
        } catch(Exception e){
            System.out.println(e);
        }


    }

    public void startMusic(){
        music.start();
        isPlaying = true;
    }

    public void stopMusic(){
        music.stop();
        music.release();
        isPlaying = false;

    }

    public void update(){
        if(isPlaying){
            float time = music.getCurrentPosition();
            Log.d("notesdebug", "expecting note time:"+noteTimings[currentnoteCount]);
            //Log.d("song time", String.valueOf(time));
            if(time >= noteTimings[currentnoteCount]){
                //release enemy
                Log.d("notesdebug","note hit " + String.valueOf(time));
                currentnoteCount++;
            }
        }

    }

    public void reset(){
        stopMusic();
        currentnoteCount = 0;
        isPlaying = false;
    }

}
