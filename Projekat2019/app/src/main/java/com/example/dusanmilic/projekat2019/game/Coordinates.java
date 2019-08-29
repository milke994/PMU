package com.example.dusanmilic.projekat2019.game;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private int x;
    private int y;

    public Coordinates() {
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
