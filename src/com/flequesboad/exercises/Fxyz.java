package com.flequesboad.exercises;

import java.util.concurrent.Callable;
/*

 exercise 1

 * y" + 2*y*y' = 0
 * y(0) = 0, y' = 1
 *
 exercise 2
 * y'^2 + 1
 * -------- + y" = 0
 * 1 + t^2
 *
 * y(0) = y'(0) = 0
 *
 * t = [0; 6] h0 = 0.15
 * hпечат = 0.6
 *
 exercise 3
 * y" = 6x
 * y(-1) = 0
 * y'(-1) = 2
 * x = [-1; 2]
 * h = 0.375
 *
 * */

public class Fxyz implements Callable<Double>{
    Double x,y,z;
    int ex;
    public Fxyz(){
        this.setXYZ(0.0,0.0,0.0);
        this.ex = 1;
    }
    public Fxyz(int ex){
        this.setXYZ(0.0,0.0,0.0);
        this.ex = ex;
    }
    public Fxyz(Double a, Double b, Double c, int ex){
        this.setXYZ(a,b,c);
        this.ex = ex;
    }
    public Fxyz(XYZ xyz, int ex){
        this.setXYZ(xyz);
        this.ex = ex;
    }
    public void setXYZ(XYZ xyz) {
        this.x = xyz.getX();
        this.y = xyz.getY();
        this.z = xyz.getZ();
    }
    public void setXYZ(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Double call(){
        switch (this.ex){
            case 1:
            case 2:
                return z;
            case 3:
                return z;
            default:
                return z;
        }
    }
}
