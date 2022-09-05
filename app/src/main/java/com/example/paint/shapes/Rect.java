package com.example.paint.shapes;

import android.graphics.Paint;

public class Rect {
    float xPos1;
    float yPos1;
    float xPos2;
    float yPos2;
    int paint;

    public float getxPos1() {
        return xPos1;
    }

    public void setxPos1(float xPos1) {
        this.xPos1 = xPos1;
    }

    public float getyPos1() {
        return yPos1;
    }

    public void setyPos1(float yPos1) {
        this.yPos1 = yPos1;
    }

    public float getxPos2() {
        return xPos2;
    }

    public void setxPos2(float xPos2) {
        this.xPos2 = xPos2;
    }

    public float getyPos2() {
        return yPos2;
    }

    public void setyPos2(float yPos2) {
        this.yPos2 = yPos2;
    }

    public int getPaint() {
        return paint;
    }

    public void setPaint(int paint) {
        this.paint = paint;
    }
}
