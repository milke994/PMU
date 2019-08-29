package com.example.dusanmilic.projekat2019.game;

import android.graphics.Bitmap;
import android.graphics.Paint;

import java.io.Serializable;

public abstract class Ball {

    private Coordinates center, velocity, aceleration;

    private float radius, mass;

    private Bitmap bitmap;

    private Paint paint;


    public Ball(Bitmap bitmap) {
        this.center = new Coordinates();
        this.velocity = new Coordinates();
        this.aceleration = new Coordinates();
        this.bitmap = bitmap;
        radius = bitmap.getWidth() / 2;
        mass = 2 * radius;
        this.setAcelerationX(0);
        this.setAcelerationY(0);
        this.setVelocityX(0);
        this.setVelocityY(0);
        paint = new Paint();
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public Coordinates getCenter() {
        return center;
    }

    public Coordinates getVelocity() {
        return velocity;
    }

    public float getRadius() {
        return radius;
    }

    public void setVelocityX(int x) {
        this.velocity.setX(x);
    }

    public void setVelocityY(int y){
        this.velocity.setY(y);
    }

    public float getMass() {
        return mass;
    }

    public void setAcelerationX(int x) {
        this.aceleration.setX(x);
    }

    public void setAcelerationY(int y){
        this.aceleration.setY(y);
    }

    public int getVelocityX() {
        return velocity.getX();
    }

    public int getVelocityY() { return velocity.getY(); }

    public int getAcelerationX() {
        return aceleration.getX();
    }

    public int getAcelerationY() { return aceleration.getY(); }

    public int getCenterX() { return this.center.getX();}

    public int getCenterY() { return this.center.getY();}

    public void setCenterX(int x) { this.center.setX(x); }

    public void setCenterY(int y) { this.center.setY(y); }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getDrawingX(){
        return this.center.getX() - this.getBitmap().getWidth() / 2;
    }

    public int getDrawingY(){
        return this.center.getY() - this.getBitmap().getHeight() / 2;
    }
}
