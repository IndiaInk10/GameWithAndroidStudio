package com.example.a2dtopviewsurvival.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.a2dtopviewsurvival.GameDisplay;

/***
 * Circle is an abstract class which implements a draw method from GameObject for drawing the object
 * as a circle
 */
public abstract class Circle extends GameObject {

    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);

        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
    }

    /***
     * isColliding checks if two circles objects are colliding, based on their position and radius
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = GameObject.getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();

        if(distance <= distanceToCollision)  return true;
        else return false;
    }

    private double getRadius() {  return radius;  }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle(
                (float) gameDisplay.gameToDisplayCoordinatesX(positionX),
                (float) gameDisplay.gameToDisplayCoordinatesY(positionY),
                (float)radius,
                paint
        );
    }
}
