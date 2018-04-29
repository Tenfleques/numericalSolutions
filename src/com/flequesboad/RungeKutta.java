package com.flequesboad;

import com.flequesboad.exercises.Fxyz;
import com.flequesboad.exercises.Gxyz;
import com.flequesboad.exercises.XYZ;

public class RungeKutta {
    private Double h;
    private Fxyz fxyz;
    private Gxyz gxyz;
    public RungeKutta(Double shag, int ex){
        this.setH(shag);
        this.fxyz = new Fxyz(ex);
        this.gxyz = new Gxyz(ex);
    }
    public void setH(Double x){
        this.h = x;
    }
    public XYZ getYi(XYZ xyz){
        Double x = xyz.getX();
        Double y = xyz.getY();
        Double z = xyz.getZ();
        Double[] k = new Double[]{0.0,0.0,0.0,0.0};
        Double[] l = new Double[]{0.0,0.0,0.0,0.0};
        try {
            this.fxyz.setXYZ(x,y,z);
            this.gxyz.setXYZ(x,y,z);

            k[0] = h * this.fxyz.call();
            l[0] = h * this.gxyz.call();

            this.fxyz.setXYZ(x + 0.5*h, y + 0.5*k[0] , z + 0.5*l[0]);
            this.gxyz.setXYZ(x + 0.5*h, y + 0.5*k[0] , z + 0.5*l[0]);
            k[1] = h * this.fxyz.call();
            l[1] = h * this.gxyz.call();

            this.fxyz.setXYZ(x + 0.5*h, y + 0.5*k[1], z + 0.5*l[1]);
            this.gxyz.setXYZ(x + 0.5*h, y + 0.5*k[1], z + 0.5*l[1]);
            k[2] = h * this.fxyz.call();
            l[2] = h * this.gxyz.call();

            this.fxyz.setXYZ(x + h, y + k[2], z + l[2]);
            this.gxyz.setXYZ(x + h, y + k[2], z + l[2]);
            k[3] = h * this.fxyz.call();
            l[3] = h * this.gxyz.call();

            double y1 = y + (1.0/6.0)*(k[0] + 2*k[1] + 2*k[2] + k[3]);
            double z1 = z + (1.0/6.0)*(l[0] + 2*l[1] + 2*l[2] + l[3]);

            return new XYZ(x + h, y1, z1 );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new XYZ(0.0,0.0, 0.0);
    }

}
