package com.example.a2dtopviewsurvival;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private int outerCircleCenterPoistionX;
    private int outerCircleCenterPoistionY;
    private int innerCircleCenterPoistionX;
    private int innerCircleCenterPoistionY;

    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
        // Outer and inner circle make up the joystick
        outerCircleCenterPoistionX = centerPositionX;
        outerCircleCenterPoistionY = centerPositionY;
        innerCircleCenterPoistionX = centerPositionX;
        innerCircleCenterPoistionY = centerPositionY;

        // Radius of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // Paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                outerCircleCenterPoistionX,
                outerCircleCenterPoistionY,
                outerCircleRadius,
                outerCirclePaint
        );
        // Draw inner circle
        canvas.drawCircle(
                innerCircleCenterPoistionX,
                innerCircleCenterPoistionY,
                innerCircleRadius,
                innerCirclePaint
        );

    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPoistionX = (int) (outerCircleCenterPoistionX + actuatorX*outerCircleRadius);
        innerCircleCenterPoistionY = (int) (outerCircleCenterPoistionY + actuatorY*outerCircleRadius);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = calculateDistance(touchPositionX, touchPositionY);
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPoistionX;
        double deltaY = touchPositionY - outerCircleCenterPoistionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < outerCircleRadius) {
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        } else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }
    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    private double calculateDistance(double x, double y) {
        return Math.sqrt(
                Math.pow(outerCircleCenterPoistionX - x, 2) +
                Math.pow(outerCircleCenterPoistionY - y, 2)
        );
    }
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(
                Math.pow(x1 - x2, 2) +
                Math.pow(y1 - y2, 2)
        );
    }

    public double getActuatorX() {
        return actuatorX;
    }
    public double getActuatorY() {
        return actuatorY;
    }
}
