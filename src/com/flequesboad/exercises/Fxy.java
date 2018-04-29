package com.flequesboad.exercises;

import java.util.concurrent.Callable;

public class Fxy implements Callable<Double>{
    public XY xy;
    public Fxy(Double x, Double y){
        xy = new XY(x,y);
    }
    public Fxy(XY xy){
        this.xy = xy;
    }
    public Double call(){
        return xy.getY();
    }
}
