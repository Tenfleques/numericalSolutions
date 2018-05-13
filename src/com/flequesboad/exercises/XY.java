package com.flequesboad.exercises;

import java.util.Comparator;

public class XY implements Comparator<XY>{
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

    @Override
    public String toString(){
        return x + "; " + y;
    }
    @Override
    public int compare(XY o1, XY o2) {
        return Double.compare(o1.getY(), o2.getY());
    }
}
