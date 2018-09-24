package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class GoldDetectorColorSensor {
    //Color Sensor attached to the side of the drivetrain used to analyze the minerals + gold
    ColorSensor goldDetector;

    public GoldDetectorColorSensor(ColorSensor cs) {
        goldDetector = cs;
    }

    private int[] analyzeSample() throws InterruptedException{
        //Index 0 of array represents red, index 1 represents green value, index 2 represents blue
        int[] rgb = new int [3];

        rgb[0] = goldDetector.red();
        rgb[1] = goldDetector.blue();
        rgb[2] = goldDetector.green();

        return rgb;
    }

    public double[] collectYellowSample() throws InterruptedException{

        ArrayList<Integer> Red = new ArrayList<>();
        ArrayList<Integer> Blue = new ArrayList<>();
        ArrayList<Integer> Green = new ArrayList<>();

        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.milliseconds() < 15000){
            Red.add(goldDetector.red());
            Blue.add(goldDetector.blue());
            Green.add(goldDetector.green());
        }

        double[] RGBAvg = {getAvg(Red), getAvg(Blue), getAvg(Green), getSTDDev(getAvg(Red), Red), getSTDDev(getAvg(Blue), Blue), getSTDDev(getAvg(Green), Green),};
        return RGBAvg;
    }



    public double getAvg(ArrayList<Integer> a){
        double count = 0;
        int total = 0;
        for (int e : a){
            total += e;
            count++;
        }
        return total/count;
    }

    public double getSTDDev(double avg, ArrayList<Integer> a){
        double count = 0;
        double total = 0;
        for (int e : a){
            total += (Math.pow(e - avg, 2));
            count++;
        }
        return Math.sqrt(total/count);
    }



}
