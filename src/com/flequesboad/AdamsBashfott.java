package com.flequesboad;

import com.flequesboad.exercises.CompareXY;
import com.flequesboad.exercises.Fxyz;
import com.flequesboad.exercises.Gxyz;
import com.flequesboad.exercises.XYZ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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


    public void min(Double eps){
        List<XYZ> points, fgs;

        if(res.size() < 1)
            this.getRes();

        System.out.println(Collections.min(res, new CompareXY()));
        XYZ minpoint = Collections.min(res, new CompareXY());
        int indexMinPoint = res.indexOf(minpoint);
        //fgs = res.subList(indexMinPoint-4,indexMinPoint+2);
        points = res.subList(indexMinPoint-1,indexMinPoint+2);

        //Collections.reverse(fgs);
        points.forEach(a-> System.out.println(a));

        /*Double[][] D = new Double[3][3]; //(3,{0,0,0});
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j)
                D[i][j] = 0.0;
        }
        int i,j, k = 0;
        Double Q;
        i = 0;
        for(XYZ c: points){
            D[i][0] = c.getY();
            i++;
        }
        while(Math.abs(points.get(0).getX() - points.get(2).getX()) > eps && k < 5) {
            for(j = 1; j < 3; j++)
                for(i = j; i < 3; i++)
                    D[i][j]=(D[i][j-1]-D[j-1][j-1])/(points.get(i).getX()-points.get(j-1).getX());

            Q =(D[2][2] * (points.get(0).getX() + points.get(1).getX())
                    - D[1][1]) / (2 * D[2][2]);

            if(points.get(2).getY() < points.get(0).getY()){
                //D[0][0] = fgsTmp.get(0).getY();
                //points.set(0, fgsTmp.get(0));
            }else{
                //D[2][0] = fgsTmp.get(0).getY();
                //points.set(2, fgsTmp.get(0));
            }
            k++;
            System.out.println("k: " + k + "\t x*: " + Q + "\t f(x) " + points.get(1));
        }*/

    }
    public void maxx(Double eps){
        List<XYZ> points, fgs;

        if(res.size() < 1)
            this.getRes();


        XYZ maxpoint = Collections.max(res, new CompareXY());
        int indexMaxPoint = res.indexOf(maxpoint);
        fgs = res.subList(indexMaxPoint-5,indexMaxPoint+2);
        points = fgs.subList(4,7);

        System.out.println();
        fgs.forEach(a-> System.out.println(a));

        System.out.println();
        points.forEach(a-> System.out.println(a));

        Double[][] D = new Double[3][3]; //(3,{0,0,0});
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j)
                D[i][j] = 0.0;
        }
        int i,j, k = 0;
        Double Q;
        i = 0;
        for(XYZ c: points){
            D[i][0] = c.getY();
            i++;
        }
        while(Math.abs(points.get(0).getX() - points.get(2).getX()) > eps && k < 100) {
            for(j = 1; j < 3; j++)
                for(i = j; i < 3; i++)
                    D[i][j]=(D[i][j-1]-D[j-1][j-1])/(points.get(i).getX()-points.get(j-1).getX());

            Q =(D[2][2] * (points.get(0).getX() + points.get(1).getX())
                    - D[1][1]) / (2 * D[2][2]);


            List<XYZ> lowerFgs = fgs.subList(0,4);
            Collections.reverse(lowerFgs);

            List<XYZ> upperFgs = fgs.subList(2,7);
            Collections.reverse(upperFgs);

            if(points.get(2).getY() < points.get(0).getY()){
                /*List <XYZ> newFgs = run(new XYZ(Q,points.get(1).getY(), points.get(1).getZ()), lowerFgs);
                D[0][0] = newFgs.get(0).getY();
                points.set(0, newFgs.get(0));

                Collections.reverse(newFgs);
                for(j = 0; j < 4; ++ j)
                    fgs.set(j,newFgs.get(j));*/
            }else{
                /*List <XYZ> newFgs = run(new XYZ(Q,points.get(1).getY(), points.get(1).getZ()), upperFgs);
                D[2][0] = newFgs.get(0).getY();
                points.set(2, newFgs.get(0));

                Collections.reverse(newFgs);
                for(j = 2; j < 6; ++ j)
                    fgs.set(j,newFgs.get(j - 2));*/
            }
            k++;
            System.out.println("k: " + k + "\t" + points.get(1));
        }

    }
}
