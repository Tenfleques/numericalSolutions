package com.flequesboad;

import com.flequesboad.exercises.CompareXY;
import com.flequesboad.exercises.XY;
import com.flequesboad.exercises.XYZ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shooting {
    private final Integer stepSize = 20;
    XY y1,y2;
    Double eps;
    int ex;
    public Shooting(XY ya, XY yb, int ex, Double eps){
        //initial shoot
        y1 = ya;
        y2 = yb;
        this.ex = ex;
        this.eps = eps;
    }
    public List<XYZ> getRes(){
        Double h = ( y2.getX() - y1.getX() )/ stepSize;
        Double z = converge(y2.getY(),y1.dydx(y2), y2.getY()*20, h);

        AdamsBashfott adamsBashfott = new AdamsBashfott(h,y1.getX(),y1.getY(),z,ex);
        adamsBashfott.setMaxX(y2.getX());
        List<XYZ> res = adamsBashfott.getRes();
        return res;
    }
    private double f(Double z){
        Double h = ( y2.getX() - y1.getX() )/ stepSize;
        AdamsBashfott adamsBashfott = new AdamsBashfott(h,y1.getX(),y1.getY(),z,ex);
        adamsBashfott.setMaxX(y2.getX());
        List<XYZ> res = adamsBashfott.getRes();
        Double fx = res.get(res.size() - 1).getY();
        return fx;
    }
    public Double converge( Double target, Double mn, Double mx, Double shag){
        List<XY> points = new ArrayList<>();
        int sz = 4;
        Double[][] D = new Double[sz][sz];
        List<Double> negs = new ArrayList<>(), poss = new ArrayList<>();
        for(int i = 0; i < sz; ++i){
            for(int j = 0; j < sz; ++j)
                D[i][j] = 0.0;
        }
        int i,j, k = 0;
        Double Q = mn, z = target;

        for(Double tmp = mn; tmp <= mx; tmp += shag){
            if(f(tmp) < target){
                negs.add(tmp);
            }else
                poss.add(tmp);
        }
        Collections.sort(negs);
        Collections.sort(poss);
        //negs.forEach(a -> System.out.println(f(a)));
        //System.out.println();
        //poss.forEach(System.out::println);
        if(negs.size() >= 2 && poss.size() >= 2){
            negs = negs.subList(0,2);
            negs.addAll(poss.subList(0,2));

            i = 0;
            for (Double tmp: negs) {
                points.add(new XY(tmp,f(tmp)));
                D[i++][0] = tmp;
            }
            //points.forEach(System.out::println);
            do {
                k++;
                for(j = 1; j < sz; j++)
                    for(i = j; i < sz; i++)
                        D[i][j]=(D[i][j-1]-D[j-1][j-1])/(points.get(i).getY()-points.get(j-1).getY());

                Q = D[sz-1][sz-1];
                for (i = sz - 2; i >=0; i--)
                    Q = Q*(z-points.get(i).getY()) + D[i][i];
                //System.out.println("\n k: " + k  + "\t x*: " + Q  + "\t f(x) " + f(Q) );
                int indexMid;
                if(f(Q) < target) {
                    indexMid = points.indexOf(Collections.min(points, new CompareXY()));
                }else
                    indexMid = points.indexOf(Collections.max(points, new CompareXY()));

                points.set(indexMid, new XY(Q,f(Q)));

                for(i = 0; i<sz; ++i){
                    D[i][0] = points.get(i).getX();
                }
            }while(Math.abs(f(Q) - target) > eps && k < 5);
        }
        return Q;
    }
}
