package com.flequesboad.exercises;

import java.util.Arrays;
import java.util.List;

public class XYZ extends XY implements Comparable{
    private Double z;
    public XYZ(Double x, Double y, Double z){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }
    protected void setZ(Double z){
        this.z = z;
    }
    public Double getZ() {
        return z;
    }
    public List<Double> getXYZ(){
        Double[] x = new Double[3];
        x[0] = this.getX();
        x[1] = this.getY();
        x[2] = this.getZ();
        return Arrays.asList(x);
    }
    /*@Override
    public String toString(){
        return this.getX() + "; " + this.getY() + "; " + this.getZ();
    }*/
    @Override
    public int compareTo(Object o) {
        if(o.getClass() == this.getClass()){
            //if(this.getY() < cast o.getY())
        }
        return 0;
    }

}
