package com.example.a2dtopviewsurvival;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Observer;

/***
 * Game manages all objects in the game and is responsible for updating all states
 * and render all objects to the screen
 */
public class GameLoop extends Thread {
    public static final double MAX_UPS = 60.0;
    // in Runtime, it can't be change
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private Game game;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private double averageUPS = 0.0;
    private double averageFPS = 0.0;

    private int survivalTime = 0;

    public GameLoop(Game game, SurfaceHolder surfaceHolder, int survivalTime) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
        this.survivalTime = survivalTime;
    }

    public double getAverageUPS() {
        return averageUPS;
    }
    public double getAverageFPS() {
        return averageFPS;
    }
    public int getSurvivalTime() {  return survivalTime; }

    public void startLoop() {
        Log.d("GameLoop.java", "startLoop()");
        isRunning = true;
        // threads use for multi task
        start();
    }

    @Override
    public void run() {
        Log.d("GameLoop.java", "run()");
        super.run();

        // Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        // GameLoop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning) {
            // Try to update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;

                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            // Pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)(updateCount * UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Skip frames to keep up with target UPS
            while(sleepTime < 0 && updateCount < UPS_PERIOD - 1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long)(updateCount * UPS_PERIOD - elapsedTime);
            }

            // Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);

                updateCount = frameCount = 0;
                startTime = System.currentTimeMillis();

                survivalTime++;
            }
        }
    }

    public void stopLoop() {
        Log.d("GameLoop.java", "stopLoop()");

        isRunning = false;
        // Wait for thread to join
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();;
        }
    }

    public void endLoop() {
        isRunning = false;
        game.setSurvivalTime(survivalTime);
    }
}
