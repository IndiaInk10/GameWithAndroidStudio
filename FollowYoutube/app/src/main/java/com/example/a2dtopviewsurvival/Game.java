package com.example.a2dtopviewsurvival;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a2dtopviewsurvival.object.Circle;
import com.example.a2dtopviewsurvival.object.Enemy;
import com.example.a2dtopviewsurvival.object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// render screen and touch input controller
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Player player;
    private final Joystick joystick;
//    private final Enemy enemy;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();


    public Game(Context context) {
        super(context);

        // Get surface holder and add callbacks
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);


        // Initialize game objects
        joystick = new Joystick(275, 700, 70, 40);
        player = new Player(getContext(), joystick, 2*500, 500, 30);
//        enemy = new Enemy(getContext(), player, 500, 200, 30);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double)event.getX(), (double)event.getY())) {
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    // rendering screen
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);

        for(Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }
    }

    // draw update per seconds
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int Color = ContextCompat.getColor(getContext(), R.color.green);
        paint.setColor(Color);
        paint.setTextSize(50);
        canvas.drawText("UPS : " + averageUPS, 50, 100, paint);
    }
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int Color = ContextCompat.getColor(getContext(), R.color.green);
        paint.setColor(Color);
        paint.setTextSize(50);
        canvas.drawText("FPS : " + averageFPS, 50, 150, paint);
    }

    public void update() {
        // Update game state
        joystick.update();
        player.update();

        // Spawn enemy if it's time to spawn new enemies
        if(Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        // Update state of each enemy
        for(Enemy enemy : enemyList) {
            enemy.update();
        }

        // Iterate through enemyList and check for collision between each enemy and the player
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()) {
            if(Circle.isColliding(iteratorEnemy.next(), player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
            }
        }
    }
}


