package com.flequesboad.exercises;
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
public class Gxyz extends Fxyz{
    public Gxyz(){
        super();
    }
    public Gxyz(int ex){
        super(ex);
    }
    public Gxyz(Double a, Double b, Double c, int ex){
        super(a,b,c,ex);
    }
    public Gxyz(XYZ xyz, int ex){
        super(xyz,ex);
    }
    @Override
    public Double call(){
        switch(this.ex){
            case 1:
                return 2*y;
            case 2:
                return -1* (z*z + 1)/(1 + x*x);
            case 3:
                return 6*x;
            case 4:
                return -y * Math.cosh(x);
            case 5:
                return (y*y + z*z)/y;//ilidio
            case 6:
                return (y*y + z*z)/y;// aser
        }
        return 2*y;//default exercise 1
    }
}
