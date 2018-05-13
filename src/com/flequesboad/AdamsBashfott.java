package com.flequesboad;

import com.flequesboad.exercises.Fxyz;
import com.flequesboad.exercises.Gxyz;
import com.flequesboad.exercises.XYZ;

import java.util.ArrayList;
import java.util.List;

public class AdamsBashfott {
    private final Double h;
    private final Double[] fs;
    private final Double[] gs;
    private final int ex;
    private final XYZ xyz;
    private double maxX;
    private ArrayList<XYZ> res;
    AdamsBashfott(Double h, Double x, Double y, Double z, int ex){
        this.h = h;
        this.fs = new Double[4];
        this.gs = new Double[4];
        this.ex = ex;
        this.xyz = new XYZ(x,y, z);
        this.maxX = h * 10;
        res = new ArrayList<>();
    }
    AdamsBashfott(Double h, XYZ xyz, int ex){
        this.h = h;
        this.fs = new Double[4];
        this.gs = new Double[4];
        this.ex = ex;
        this.xyz = xyz;
        this.maxX = h * 10;
        res = new ArrayList<>();
    }
    private void resetFsGs(){
        for(int j = 0; j < 4; ++j ){
            fs[j] = 0.0;
            gs[j] = 0.0;
        }
    }
    public List<XYZ> getRes(){
        XYZ xyz = this.xyz;
        res = new ArrayList<>();
        resetFsGs();
        RungeKutta rk = new RungeKutta(h, ex);
        int i = 3;
        res.add(xyz);
        while(i >= 0){
            xyz = rk.getYi(xyz);
            res.add(xyz);
            fs[i] = new Fxyz(xyz, ex).call();
            gs[i] = new Gxyz(xyz, ex).call();
            i--;
        }
        while(xyz.getX() < this.maxX) {
            xyz = run(xyz);
            res.add(xyz);
        }
        return res;
    }
    private XYZ run(XYZ xyz){
        Double x = xyz.getX() + this.h;
        Double y = this.calc(xyz.getY(),fs[0], fs[1], fs[2], fs[3]);
        Double z = this.calc(xyz.getZ(),gs[0], gs[1], gs[2], gs[3]);

        //phase out fi_3 = f[3], fi = f[0] new
        for(int i = 3; i > 0; --i){
            fs[i] = fs[i - 1];
            gs[i] = gs[i - 1];
        }

        fs[0] = new Fxyz(x,y,z, ex).call();
        gs[0] = new Gxyz(x,y,z, ex).call();

        return new XYZ(x,y,z);
    }

    private Double calc(Double yi, Double fi, Double fi_1, Double fi_2, Double fi_3){
        return yi + (h/24)*(55*fi - 59*fi_1 + 37*fi_2 - 9*fi_3);
    }
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }
}
