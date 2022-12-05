package com.example.a2dtopviewsurvival;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.a2dtopviewsurvival.R;

public class MainActivity extends Activity {
    MediaPlayer bgmPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bgmPlayer = MediaPlayer.create(this, R.raw.race_to_mars);
        bgmPlayer.setLooping(true);
        bgmPlayer.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = (Button) findViewById(R.id.startBtn) ;
        startBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartGame.class) ;

                startActivity(intent) ;
            }
        });

        Button rankBtn = (Button) findViewById(R.id.rankBtn) ;
        rankBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Self1303.class) ;
//
//                startActivity(intent) ;
            }
        });

        Button optionBtn = (Button) findViewById(R.id.optionBtn) ;
        optionBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Self1303.class) ;
//
//                startActivity(intent) ;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("MainActivity.java", "onBackPressed()");
        // Comment out super to prevent any back press action
        // super.onBackPressed();
    }
}