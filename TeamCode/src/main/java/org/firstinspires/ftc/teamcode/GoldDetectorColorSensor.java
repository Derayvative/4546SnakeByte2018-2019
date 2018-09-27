package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;



public class GoldDetectorColorSensor {
    //Color Sensor attached to the side of the drivetrain used to analyze the minerals + gold
    ColorSensor goldDetector;
    float[] MineralHSV;
    float[] GoldHSV;

    public GoldDetectorColorSensor(ColorSensor cs) throws InterruptedException {
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

        ArrayList<Integer> Alpha = new ArrayList<>();
        ArrayList<Integer> Red = new ArrayList<>();
        ArrayList<Integer> Blue = new ArrayList<>();
        ArrayList<Integer> Green = new ArrayList<>();

        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.milliseconds() < 15000){
            Alpha.add(goldDetector.alpha());
            Red.add(goldDetector.red());
            Blue.add(goldDetector.blue());
            Green.add(goldDetector.green());
        }

        double[] RGBAvg = {getAvg(Alpha),getAvg(Red), getAvg(Blue), getAvg(Green), getSTDDev(getAvg(Alpha),Alpha), getSTDDev(getAvg(Red), Red), getSTDDev(getAvg(Blue), Blue), getSTDDev(getAvg(Green), Green),};
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



    public float[] getHSVArray(int r, int g, int b) throws InterruptedException{
        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);
        hsv[0] /= 360.0;
        return hsv;
    }

    public double findHSVAvg() throws InterruptedException{
        int count = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        double h = 0;
        double s = 0;
        double v = 0;
        while (timer.milliseconds() < 15000){
            h += getHSVArray(goldDetector.red(),goldDetector.blue(),goldDetector.green())[0];
            s += getHSVArray(goldDetector.red(),goldDetector.blue(),goldDetector.green())[1];
            v += getHSVArray(goldDetector.red(),goldDetector.blue(),goldDetector.green())[0];
        }
        return 0;
    }

    public String identifyGoldOrMineral(float[] hsv) throws InterruptedException{
        double vectorSimilarityMineral = Math.sqrt(Math.pow(MineralHSV[0] - hsv[0],2)+ Math.pow(MineralHSV[1] - hsv[1],2) + Math.pow(MineralHSV[2] - hsv[2],2));
        double vectorSimilarityGold = Math.sqrt(Math.pow(GoldHSV[0] - hsv[0],2)+ Math.pow(GoldHSV[1] - hsv[1],2) + Math.pow(GoldHSV[2] - hsv[2],2));
        if (vectorSimilarityGold > vectorSimilarityMineral){
            return "Mineral";
        }
        else if (vectorSimilarityMineral > vectorSimilarityGold){
            return "Gold";
        }
        return null;

    }








}
