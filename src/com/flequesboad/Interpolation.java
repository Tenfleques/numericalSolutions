package com.flequesboad;

import com.flequesboad.exercises.CompareXY;
import com.flequesboad.exercises.XY;
import com.flequesboad.exercises.XYZ;

import java.util.ArrayList;
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
    public List<List<XYZ>> point(List<XYZ> xyz, double ep, Double y){
        List<XYZ> points = getNeighborhood(xyz,y); //get left of y, near y  and 2 right of y
        List<List<XYZ>> results = new ArrayList<>();
        Double[][] D = new Double[3][3]; //(3,{0,0,0});
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j)
                D[i][j] = 0.0;
        }
        int i,j, k = 0;
        Double Q = Double.MAX_VALUE;
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

            newShag = Math.abs(points.get(1).getX() - points.get(0).getX()) * 0.1;

            AdamsBashfott nab = new AdamsBashfott(newShag,points.get(0),ex);
            nab.setMaxX(points.get(2).getX());
            points = getNeighborhood(nab.getRes(), y);

            if(points.get(2).getY() > points.get(0).getY()){
                D[0][0] = points.get(0).getY();
            }else{
                D[2][0] = points.get(2).getY();
            }

            k++;
            results.add(points);
        }
        return results;
    }
    public List<List<XYZ>> extreme(List<XYZ> xyz, double ep, int isMax){
        List<XYZ> points = getNeighborhood(xyz,isMax);
        List<List<XYZ>> results = new ArrayList<>();
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
            points = getNeighborhood(nab.getRes(), isMax);
            if(isMax == 1){
                if(points.get(2).getY() > points.get(0).getY()){
                    D[0][0] = points.get(0).getY();
                }else{
                    D[2][0] = points.get(2).getY();
                }
            }else{
                if(points.get(2).getY() < points.get(0).getY()){
                    D[0][0] = points.get(0).getY();
                }else{
                    D[2][0] = points.get(2).getY();
                }
            }

            k++;
            results.add(points);
        }
        return results;
    }
    List<XYZ> getNeighborhood(List <XYZ> xyz, int isMax){
        XYZ extremePoint;
        if(isMax == 1){
            extremePoint = Collections.max(xyz, new CompareXY());
        }else{
            extremePoint = Collections.min(xyz, new CompareXY());
        }
        int indexExtremePoint = xyz.indexOf(extremePoint);
        List<XYZ> points;
        try {
            points = xyz.subList(indexExtremePoint - 1, indexExtremePoint + 2);
        }catch (IndexOutOfBoundsException e){
            //e.printStackTrace();
            System.out.println("Неправильная екстрема спросили, максимальна = 1, минимума = 0");
            points = new ArrayList<>();
        }
        return points;
    }
    List<XYZ> getNeighborhood(List <XYZ> xyz, Double y){
        XYZ leftPoint = xyz.get(0);
        XYZ centralPoint = xyz.get(xyz.size() - 2);
        XYZ rightPoint = xyz.get(xyz.size() - 1);

        int rightCount = 0;
        for(XYZ i : xyz){
            if(i.getY() < y)
                leftPoint = i;
            if (i.getY() >= y) {
                if(rightCount == 0)
                    centralPoint = i;

                if(rightCount > 0) {
                    rightPoint = i;
                    break;
                }
                rightCount ++;
            }
        }
        /*System.out.println(leftPoint);
        System.out.println(centralPoint);
        System.out.println(rightPoint);*/

        List<XYZ> points = new ArrayList<>();
        points.add(leftPoint);
        points.add(centralPoint);
        points.add(rightPoint);
        //System.out.println(points);
        return points;
    }
}
