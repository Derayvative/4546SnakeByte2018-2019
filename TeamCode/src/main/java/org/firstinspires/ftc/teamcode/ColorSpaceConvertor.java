package org.firstinspires.ftc.teamcode;

//Basic tool for experimentation with color sensor and potential CV
//Meant to convert RGB color space into CIELAB color space
//The problem with RGB color space is that it is difficult to
//tell how similar 2 colors are based off of their RGB values
//CIELAB color space more matches how the human eye detects
//similarities between colors

//In CIELAB color space, L represents how white or black an object is
//a represents how red or green and object is and b represents blue or yellow
public final class ColorSpaceConvertor {


    //No Argument private constructor prevents class from being initialized
    //To use methods, type ColorSpaceConvert.methodName()
    private ColorSpaceConvertor(){

    }

    public static double[] RGBtoXYZ(int[] RGB) throws InterruptedException{
        double r = RGB[0];
        double g = RGB[1];
        double b = RGB[2];

        if (r > 0.04045){
            r = Math.pow(((r + 0.055 ) / 1.055), 2.4);
        }
        else{
            r /= 12.92;
        }

        if (b > 0.04045){
            b = Math.pow(((b + 0.055 ) / 1.055), 2.4);
        }
        else{
            b /= 12.92;
        }
        if (g > 0.04045){
            g = Math.pow(((g + 0.055 ) / 1.055), 2.4);
        }
        else{
            g /= 12.92;
        }
        r *= 100;
        g *= 100;
        b *= 100;

        double X = r * 0.4124 + g * 0.3576 + b * 0.1805;
        double Y = r * 0.2126 + g * 0.7152 + b * 0.0722;
        double Z = r * 0.0193 + g * 0.1192 + b * 0.9505;

        double[] XYZ = {X,Y,Z};
        return XYZ;
    }
}
