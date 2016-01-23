package kyon.runners;

import android.content.Context;
import android.media.MediaPlayer;

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

    MediaPlayer music;

    public Soundtrack(Context context){

        music = MediaPlayer.create(context, R.raw.redzone);
        music.start();

    }

    public void stopMusic(){
        music.stop();
        music.release();

    }

    public ArrayList loadSong(String url){
        ArrayList i = null;
        //read song
        //set music player
        //create map
        //return arraylist
        return i;
    }
}
