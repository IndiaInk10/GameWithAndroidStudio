package com.example.a2dtopviewsurvival.gameObject;

import android.content.Context;
import android.graphics.Canvas;

/***
 * Player is the main character of the game, which the user can control with a touch joystick
 * The player class is an extension of a Circle, which is an extension of a GameObject
 */
import androidx.core.content.ContextCompat;

import com.example.a2dtopviewsurvival.Game;
import com.example.a2dtopviewsurvival.GameDisplay;
import com.example.a2dtopviewsurvival.GameLoop;
import com.example.a2dtopviewsurvival.gamePanel.HealthBar;
import com.example.a2dtopviewsurvival.gamePanel.Joystick;
import com.example.a2dtopviewsurvival.R;
import com.example.a2dtopviewsurvival.Utils;
import com.example.a2dtopviewsurvival.graphics.Animator;
import com.example.a2dtopviewsurvival.graphics.Sprite;

public class Player extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 10;
    private final Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;
    private Animator animator;
    private PlayerState playerState;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius, Animator animator) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context, this);
        this.healthPoints = MAX_HEALTH_POINTS;
        this.animator = animator;
        this.playerState = new PlayerState(this);
    }

    public void update() {
        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if(velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }

        playerState.update();
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animator.draw(canvas, gameDisplay, this);
        healthBar.draw(canvas, gameDisplay);
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if(healthPoints < 0) return;
        this.healthPoints = healthPoints;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
