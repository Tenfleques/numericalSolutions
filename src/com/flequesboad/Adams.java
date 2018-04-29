package com.flequesboad;

import com.flequesboad.exercises.Fxy;
import com.flequesboad.exercises.XY;

import java.util.concurrent.Callable;

public class Adams {
    private Double h;
    public Adams(Double shag){
        this.setH(shag);
    }
    public void setH(Double x){
        this.h = x;
    }
    public XY explicit(XY xy, XY xy_){
        //явный метод
        Fxy f = new Fxy(xy);
        Fxy f_ = new Fxy(xy_);

        double y1 = f.call() + (f.call() - f_.call())*(xy.getX() - xy_.getX())/h;
        return new XY(xy.getX() + h, y1);
    }
    public XY implicit(XY xy, Callable<Double> f){
        //неявный методы
        double y1 = new Fxy(xy).call();
        return new XY(xy.getX() + h, y1);
    }
}
