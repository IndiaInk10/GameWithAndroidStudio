package com.example.a2dtopviewsurvival.gamePanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

import com.example.a2dtopviewsurvival.Game;
import com.example.a2dtopviewsurvival.GameDisplay;
import com.example.a2dtopviewsurvival.GameLoop;
import com.example.a2dtopviewsurvival.R;

public class GameInfo {
    private Game game;
    private Context context;

    public GameInfo(Context context, Game game) {
        this.context = context;
        this.game = game;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        drawScore(canvas);
        drawTime(canvas, gameDisplay);
    }

    // draw update per seconds
    public void drawScore(Canvas canvas) {
        String score = Integer.toString(game.getScore());
        Paint paint = new Paint();
        int Color = ContextCompat.getColor(context, R.color.green);
        paint.setColor(Color);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE : " + score, 50, 100, paint);
    }
    public void drawTime(Canvas canvas, GameDisplay gameDisplay) {
        int time = game.getSurvivalTime();
        int minute = time / 60;
        int sec = time % 60;

        Paint paint = new Paint();
        int Color = ContextCompat.getColor(context, R.color.green);
        paint.setColor(Color);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText( String.format("%02d", minute) + ":" + String.format("%02d", sec), gameDisplay.getdisplayCenterX() - 65, 100, paint);
    }
}
