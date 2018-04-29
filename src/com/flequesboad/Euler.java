package com.flequesboad;

import com.flequesboad.exercises.Fxy;
import com.flequesboad.exercises.XY;

import java.util.concurrent.Callable;

public class Euler {
    private Double h;
    public Euler(Double shag){
        this.setH(shag);
    }
    public void setH(Double x){
        this.h = x;
    }
    public XY explicit(XY xy){
        double y1 = xy.getY() + h * new Fxy(xy).call();
        return new XY(xy.getX() + h, y1);
    }
    public XY implicit(XY xy, Callable <Double> f){
        double y1 = new Fxy(xy).call();
        return new XY(xy.getX() + h, y1);
    }
    public XY forecast(XY xy){
        double y = xy.getY();
        double y1 = xy.getY() + (h/2)*(Math.pow(y,2.0) + 2 + Math.pow((y+h*(Math.pow(y,2.0)+1)),2.0));
        return new XY(xy.getX()+h,y1);
    }
}
