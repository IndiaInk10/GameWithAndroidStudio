package com.example.a2dtopviewsurvival;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.a2dtopviewsurvival.gameObject.Circle;
import com.example.a2dtopviewsurvival.gameObject.Enemy;
import com.example.a2dtopviewsurvival.gamePanel.GameOver;
import com.example.a2dtopviewsurvival.gameObject.Player;
import com.example.a2dtopviewsurvival.gameObject.Spell;
import com.example.a2dtopviewsurvival.gamePanel.Joystick;
import com.example.a2dtopviewsurvival.gamePanel.GameInfo;
import com.example.a2dtopviewsurvival.graphics.SpriteSheet;
import com.example.a2dtopviewsurvival.graphics.Animator;
import com.example.a2dtopviewsurvival.map.Tilemap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// render screen and touch input controller
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Tilemap tilemap;
    public final Player player;
    private final Joystick joystick;
    //    private final Enemy enemy;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    // 0 : laser / 1 : hit / 2 : hurt
    private List<MediaPlayer> sfxList = new ArrayList<MediaPlayer>();
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    // private Performance performance;
    private GameInfo gameInfo;
    private GameDisplay gameDisplay;

    private Context context;

    private int score;
    private int survivalTime;

    public Game(Context context) {
        super(context);
        this.context = context;

        score = 0;
        survivalTime = 0;

        // Initialize sfx player
        sfxList.add(MediaPlayer.create(context, R.raw.laser));
        sfxList.add(MediaPlayer.create(context, R.raw.hit));
        sfxList.add(MediaPlayer.create(context, R.raw.hurt));

        // Get surface holder and add callbacks
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder, survivalTime);

        // Initialize game panels
        // performance = new Performance(context, gameLoop);
        gameInfo = new GameInfo(context, this);
        gameOver = new GameOver(context);
        joystick = new Joystick(275, 700, 70, 40);

        // Initialize game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());
        player = new Player(context, joystick, 2*500, 500, 30, animator);

        // Initialize game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        // Initialize Tilemap
        tilemap = new Tilemap(spriteSheet);
        
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(joystick.getIsPressed()) {
                  // Joystick was pressed before this event -> cast spell
                    numberOfSpellsToCast++;
                } else if(joystick.isPressed((double)event.getX(), (double)event.getY())) {
                    // Joystick is pressed in this event -> setIsPressed(true) and store ID
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    // Joystick was not previously, and is not pressed in this event -> cast spell
                    numberOfSpellsToCast++;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                // Joystick was pressed previously and is now moved
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // Joystick was let go of -> setIsPressed(false) and resetActuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");

        if(gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder, survivalTime);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    // rendering screen
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw Tilemap
        tilemap.draw(canvas, gameDisplay);

        // Draw game objects
        player.draw(canvas, gameDisplay);

        for(Enemy enemy : enemyList) {
            enemy.draw(canvas, gameDisplay);
        }

        for(Spell spell : spellList) {
            spell.draw(canvas, gameDisplay);
        }

        // Draw game panels
        joystick.draw(canvas);
        //performance.draw(canvas);
        gameInfo.draw(canvas, gameDisplay);

        // Draw Game over if the player is dead
        if(player.getHealthPoints() <= 0) {
            gameOver.draw(canvas);
        }
    }

    public void update() {
        // Stop updating the game if the player is dead
        if(player.getHealthPoints() <= 0) {
            joystick.resetActuator();
            end();
            return;
        }

        // Update game state
        joystick.update();
        player.update();

        // Spawn enemy if it's time to spawn new enemies
        if(Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }
        while(numberOfSpellsToCast > 0) {
            spellList.add(new Spell(getContext(), player));
            laserSfx();
            numberOfSpellsToCast--;
        }

        // Update state of each enemy
        for(Enemy enemy : enemyList) {
            enemy.update();
        }

        // Update state of each spell
        for(Spell spell : spellList) {
            spell.update();
        }

        // Iterate through enemyList and check for collision between each enemy and the player
        // and spells in spellList
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if(Circle.isColliding(enemy, player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
                hurtSfx();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while(iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // Remove enemy if it collides with a spell
                if(Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    hitSfx();
                    score += 100;
                    break;
                }
            }
        }

        gameDisplay.update();

        survivalTime = gameLoop.getSurvivalTime();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
    public void end() {
        gameLoop.endLoop();
        Intent intent = new Intent(context, StoreRank.class);
        intent.putExtra("score", score);
        //intent.putExtra("survivalTime", survivalTime);
        context.startActivity(intent);
    }

    private void laserSfx() {
        sfxList.get(0).start();
    }
    private void hitSfx() {
        sfxList.get(1).start();
    }
    private void hurtSfx() {
        sfxList.get(2).start();
    }

    public int getScore() {  return score; }

    public int getSurvivalTime() { return survivalTime; }

    public void setSurvivalTime(int survivalTime) { this.survivalTime = survivalTime; }
}


