package com.example.a2dtopviewsurvival;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a2dtopviewsurvival.gameObject.Circle;
import com.example.a2dtopviewsurvival.gameObject.Enemy;
import com.example.a2dtopviewsurvival.gamePanel.GameOver;
import com.example.a2dtopviewsurvival.gameObject.Player;
import com.example.a2dtopviewsurvival.gameObject.Spell;
import com.example.a2dtopviewsurvival.gamePanel.Joystick;
import com.example.a2dtopviewsurvival.gamePanel.Performance;

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
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    private Performance performance;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callbacks
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(context);
        joystick = new Joystick(275, 700, 70, 40);

        // Initialize game objects
        player = new Player(context, joystick, 2*500, 500, 30);

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

        // Draw game objects
        player.draw(canvas);

        for(Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }

        for(Spell spell : spellList) {
            spell.draw(canvas);
        }

        // Draw game panels
        joystick.draw(canvas);
        performance.draw(canvas);

        // Draw Game over if the player is dead
        if(player.getHealthPoints() <= 0) {
            gameOver.draw(canvas);
        }
    }

    public void update() {
        // Stop updating the game if the player is dead
        if(player.getHealthPoints() <= 0) {
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
                    break;
                }
            }
        }
    }
}


