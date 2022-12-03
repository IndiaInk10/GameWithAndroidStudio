package com.example.a2dtopviewsurvival.gameObject;

import android.graphics.Canvas;

/***
 * GameObject is an abstract class which is the foundation of all world objects in the game
 */
public abstract class GameObject {
    protected double positionX;
    protected double positionY;

    protected double velocityX = 0.0;
    protected double velocityY = 0.0;

    protected double directionX = 1.0;
    protected double directionY = 0.0;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    public double getPositionX() {  return positionX;  }
    public double getPositionY() {  return positionY;  }
    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj1.getPositionX() - obj2.getPositionX(), 2) +
                Math.pow(obj1.getPositionY() - obj2.getPositionY(), 2)
        );
    }

    protected double getDirectionX() {
        return directionX;
    }
    protected double getDirectionY() {
        return directionY;
    }
}
