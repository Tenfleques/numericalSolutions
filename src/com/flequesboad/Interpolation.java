package com.flequesboad;

import com.flequesboad.exercises.CompareXY;
import com.flequesboad.exercises.XY;
import com.flequesboad.exercises.XYZ;

import java.util.Collections;
import java.util.List;

public class Interpolation {
    int ex;
    Interpolation(int ex){
        this.ex = ex;
    }
    private double delta(XY xy0, XY xyj){
        return (xyj.getY() - xy0.getY())/(xyj.getX() - xy0.getX());
    }
    private double delta(XY xy0, XY xy1, XY xyj){
        return (delta(xy0, xyj) - delta(xy0, xy1)) / (xyj.getX() - xy1.getX());
    }
    private Double minx(XY xy0, XY xy1, XY xy2){
        return (delta(xy0, xy1, xy2)*(xy0.getX() + xy1.getX()) - delta(xy0,xy1))/ (2 * delta(xy0, xy1, xy2));
    }
    public void min(List<XYZ> xyz, double ep){
        List<XYZ> points = getNeighborhood(xyz);
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
        Double newShag;
        while(points.size() > 2 && Math.abs(points.get(0).getX() - points.get(2).getX()) > ep && k < 10) {
            for(j = 1; j < 3; j++)
                for(i = j; i < 3; i++)
                    D[i][j]=(D[i][j-1]-D[j-1][j-1])/(points.get(i).getX()-points.get(j-1).getX());

            Q =(D[2][2] * (points.get(0).getX() + points.get(1).getX())
                    - D[1][1]) / (2 * D[2][2]);

            newShag = Math.abs(Q - points.get(1).getX());

            AdamsBashfott nab = new AdamsBashfott(newShag,points.get(0),ex);
            nab.setMaxX(points.get(2).getX());
            points = getNeighborhood(nab.getRes());

            if(points.get(2).getY() > points.get(0).getY()){
                D[0][0] = points.get(0).getY();
            }else{
                D[2][0] = points.get(2).getY();
            }
            k++;
            System.out.println("k: " + k);
            points.forEach(System.out::println);
            System.out.println();
        }
    }
    List<XYZ> getNeighborhood(List <XYZ> xyz){
        XYZ maxPoint = Collections.max(xyz, new CompareXY());
        int indexMaxPoint = xyz.indexOf(maxPoint);
        List<XYZ> points = xyz.subList(indexMaxPoint-1,indexMaxPoint+2);
        return points;
    }
}
