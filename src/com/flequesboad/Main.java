package com.flequesboad;

import com.flequesboad.exercises.CompareXY;
import com.flequesboad.exercises.XYZ;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

public class Main extends Application {
    private static AdamsBashfott adamsBashfott;
    private static List<XYZ> xyz;
    private static Double getDelta(XYZ yh, XYZ yh2){
        return Math.abs(yh.getZ() - yh2.getZ());
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
        //System.out.print("count");
        if(deltaYMax > eps)
            return adamsBashfottDriver(h, x, y, z, ex, max, eps);
        return h;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*Double h = 0.375, x = -1.0, y = 0.0, z = 2.0, max = 2.0;
        int ex = 3;
        Double eps = 0.0001;
        h = adamsBashfottDriver(h, x, y, z, ex, max, eps);
        System.out.println(h);


        for(XYZ abc : xyz){
            System.out.println(abc.getXYZ());
        }
        new LineChartResults().start();*/
        /*RungeKutta rk = new RungeKutta(0.1, 1);
        XYZ xyz = new XYZ(0.0,0.0, 1.0);
        int i = 0;
        while(xyz.getX() < 0.6){
            xyz = rk.getYi(xyz);
            System.out.println("y_"+ ++i +"(x): "+xyz.getY() + " tan(x): "+ Math.tan(xyz.getX()));
        }*/
        //compare exercise 1 with analytical results
        /*double x = 0.1;
        while(x <= 0.8){
            System.out.println(" tan("+x+"): "+ Math.tan(x));
            x += h;
        }*/
        Double h = 0.15, x = 0.0, y = 1.0, z = 1.0, max = 6.0;
        int ex = 2;
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

        for(XYZ abc : xyz){
            if(abc.getX() <= 6.0 && skipper % 16 == 0.0)
                System.out.println(abc.getXYZ());
            skipper ++;
        }

        System.out.println();
        System.out.println("Пойск ymax");
        Interpolation interpolationMin = new Interpolation(ex);
        eps = 0.000001;
        List<List<XYZ>> maxResults = interpolationMin.extreme(xyz,eps, 1);

        maxResults.forEach(points -> {
            System.out.println();
            points.forEach(System.out::println);
        });
    }
}
