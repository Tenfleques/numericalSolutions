package com.flequesboad.exercises;

public class XY {
    private Double x,y;
    public XY(){
        this.setX(0.0);
        this.setY(0.0);
    }
    public XY(double x, double y){
        this.setX(x);
        this.setY(y);
    }
    protected void setX(double x){
        this.x = x;
    }
    protected void setY(double y){
        this.y = y;
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }

}
