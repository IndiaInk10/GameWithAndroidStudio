package com.example.a2dtopviewsurvival;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class StartGame extends Activity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("StartGame.java", "onCreate()");
        super.onCreate(savedInstanceState);

        // Set content view to game, so that objects in the Game class can be rendered to the screen
        game = new Game(this);
        setContentView(game);

        Toast.makeText(this, "Survive as long as possible!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        Log.d("StartGame.java", "onStart()");
        super.onStart();
    }
    @Override
    protected void onResume() {
        Log.d("StartGame.java", "onResume()");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.d("StartGame.java", "onPause()");
        game.pause();
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.d("StartGame.java", "onStop()");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d("StartGame.java", "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d("StartGame.java", "onBackPressed()");
        // Comment out super to prevent any back press action
        // super.onBackPressed();
    }
}