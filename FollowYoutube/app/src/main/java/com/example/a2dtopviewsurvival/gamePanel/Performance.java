package com.example.a2dtopviewsurvival.gamePanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2dtopviewsurvival.GameLoop;
import com.example.a2dtopviewsurvival.R;

public class Performance {

    private GameLoop gameLoop;
    private Context context;

    public Performance(Context context, GameLoop gameLoop) {
        this.context = context;
        this.gameLoop = gameLoop;
    }

    public void draw(Canvas canvas) {
        drawUPS(canvas);
        drawFPS(canvas);
    }

    // draw update per seconds
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int Color = ContextCompat.getColor(context, R.color.green);
        paint.setColor(Color);
        paint.setTextSize(50);
        canvas.drawText("UPS : " + averageUPS, 50, 100, paint);
    }
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int Color = ContextCompat.getColor(context, R.color.green);
        paint.setColor(Color);
        paint.setTextSize(50);
        canvas.drawText("FPS : " + averageFPS, 50, 150, paint);
    }
}
