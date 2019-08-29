package com.example.dusanmilic.projekat2019.controll;

public class GestureDetector {

    private boolean cancel;

    private float lastx;
    private float lastY;

    private GestureListener gestureListener;

    public GestureDetector() {
        gestureListener = null;
    }

    public void setGestureListener(GestureListener gestureListener) {
        this.gestureListener = gestureListener;
    }

    public boolean isCancel() {
        return this.cancel;
    }

    public void setCancel(boolean cancel) { this.cancel = cancel; }

    public boolean firstDown(float x, float y){
        lastx = x;
        lastY = y;
        cancel = false;

        if(gestureListener != null){
            return gestureListener.onFirstTouchDown(x, y);
        }
        return false;
    }

    public void aditionalTouchDown(float x, float y){
        this.cancel = true;
    }

    public boolean lastUp(float x, float y){
        if(gestureListener != null){
            return gestureListener.onAllTouchesUp(x, y);
        }
        return false;
    }

}
