package com.example.a2dtopviewsurvival.object;

import android.content.Context;

/***
 * Player is the main character of the game, which the user can control with a touch joystick
 * The player class is an extension of a Circle, which is an extension of a GameObject
 */
import androidx.core.content.ContextCompat;

import com.example.a2dtopviewsurvival.GameLoop;
import com.example.a2dtopviewsurvival.Joystick;
import com.example.a2dtopviewsurvival.R;
import com.example.a2dtopviewsurvival.Utils;

public class Player extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
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
            // Nomralize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
