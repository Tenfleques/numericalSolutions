package com.flequesboad;

import com.flequesboad.exercises.XY;
import com.flequesboad.exercises.XYZ;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    private static AdamsBashfott adamsBashfott;
    private static List<XYZ> xyz;
    private static Double getDelta(XYZ yh, XYZ yh2){
        return Math.abs(yh.getZ() - yh2.getZ());
    }

    public static void shootingProblem(){
        /*
         * solve using the shooting method,
         *  y" + y cosh(x) = 0; y(0) = 0; y(2.2) = 1; x e [0,2.2]
         *  solve the Koshi task using AdamsBashFot method
         *  with precision of 0.0001
         *
         */

        XY ya = new XY(0.0,0.0);
        XY yb = new XY(2.2,1.0);
        int ex = 4;
        Double eps = 0.0001;
        Shooting sh = new Shooting(ya, yb, ex, eps);

        Stage stage = new Stage();
        stage.setTitle("Рещение ДУ 2-го порядка методом стрелба");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("x");

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Рещение ДУ 2-го порядка методом стрелба");

        lineChart.setCreateSymbols(false);

        XYChart.Series series = new XYChart.Series();
        XYChart.Series zseries = new XYChart.Series();

        series.setName("y");
        zseries.setName("y'");

        List<XYZ> res = sh.getRes();
        for(XYZ abc : res) {
            series.getData().add(new XYChart.Data<>(abc.getX(), abc.getY()));
            zseries.getData().add(new XYChart.Data<>(abc.getX(), abc.getZ()));
        }


        Scene scene = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        lineChart.getData().add(zseries);

        stage.setScene(scene);
        stage.show();

        Integer skipper = 0;
        for(XYZ abc : res){
            if(skipper % 2 == 0.0)
                System.out.println(abc.getXYZ());
            skipper ++;
        }
    }
    public static Double adamsBashfottDriver(Double h, Double x,Double y,Double z,int ex, Double max, Double eps){
        AdamsBashfott aB = new AdamsBashfott(h,x,y,z,ex);
        aB.setMaxX(max);
        List<XYZ> yh = aB.getRes();

        aB = new AdamsBashfott(h/2.0,x,y,z,ex);
        aB.setMaxX(max);
        List<XYZ> yh2 = aB.getRes();

        Double deltaYMax = getDelta(yh.get(0),yh2.get(0))*h;

        for(int i = 0, j = 0; i< yh.size() && j < yh2.size(); ++i, j += 2){
            deltaYMax = Math.max(deltaYMax, getDelta(yh.get(i),yh2.get(j))*h);
        }
        h /=2;
        xyz = yh2;
        if(deltaYMax > eps)
            return adamsBashfottDriver(h, x, y, z, ex, max, eps);
        return h;
    }
    public static void rpr(){
        //Для Илидио
        Double h = 0.05, hpechat = 0.1, x = 0.0, y = 1.0, z = .0, max = 1.2;
        int ex = Keys.ILIDIO.getValue();
        Double eps = 0.0001;
        h = adamsBashfottDriver(h, x, y, z, ex, max, eps);

        adamsBashfott = new AdamsBashfott(h,x,y,z,ex);
        adamsBashfott.setMaxX(max);

        Stage stage = new Stage();
        stage.setTitle("Рещение ДУ 2-го порядка методом Адамса-Башфорта");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("x");

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Рещение ДУ 2-го порядка методом Адамса-Башфорта");

        lineChart.setCreateSymbols(false);

        XYChart.Series series = new XYChart.Series();
        XYChart.Series zseries = new XYChart.Series();

        series.setName("y");
        zseries.setName("y'");

        for(XYZ abc : xyz) {
            series.getData().add(new XYChart.Data<>(abc.getX(), abc.getY()));
            zseries.getData().add(new XYChart.Data<>(abc.getX(), abc.getZ()));
        }


        Scene scene = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        lineChart.getData().add(zseries);

        stage.setScene(scene);
        stage.show();

        Integer skipper = 0;
        Integer skipperComparator = 2;//Integer.parseInt(Double.toString(hpechat/h));

        for(XYZ abc : xyz){
            if(abc.getX() <= max && skipper % skipperComparator == 0.0)
                System.out.println(abc.getXYZ());
            skipper ++;
        }

        System.out.println();
        System.out.println("Пойск y = 1.75693"); //Илидио
        Double ypoisk = 1.75693;

        Interpolation interpolationMin = new Interpolation(ex);
        eps = 0.00001;
        List<List<XYZ>> points = interpolationMin.point(xyz,eps, ypoisk);
        System.out.println(points.get(points.size() - 1).get(1));//тут наидется значение х и у' для данного значения у

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        rpr();
    }
}
