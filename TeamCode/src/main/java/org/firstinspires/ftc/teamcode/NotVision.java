package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.opencv.android.Utils;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

//import static org.opencv.core.CvType.CV_16UC3;

@Autonomous
public class NotVision extends AutoOpMode{

    VuforiaLocalizer vuforia;
    Bitmap image1;
    Bitmap image2;
    Bitmap image3;


    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        vuforiaInit();
        waitForStart();
        //flyByPictureTake();
        pLeftTurn(55);
        sleep(300);
        image1 = getImage();
        double scoreR = findCentralWhitness(image1);
        pLeftTurn(25);
        sleep(300);
        image2 = getImage();
        double scoreM = findCentralWhitness(image2);
        pLeftTurn(25);
        sleep(300);
        image3 = getImage();
        double scoreL = findCentralWhitness(image3);
        telemetry.addData("L", scoreL);
        telemetry.addData("M", scoreM);
        telemetry.addData("R", scoreR);
        telemetry.update();
        sleep(3000);
        if (scoreL < scoreM && scoreL < scoreR){
            pRightTurn(80);
            telemetry.addData("SetUp", "GMM");
            telemetry.update();
           /*
           setPower(0.5);
           sleep(10000);
           */
            setPower(0.5);
            sleep(1500);
            //moveToRange(5);
            turnToPosition(40);
            moveToRange(30);
            dropTeamMarker();
            sleep(1000);
            turnToPosition(47);
            setPower(-0.4);
            sleep(10000);
        }
        if (scoreM < scoreL && scoreM < scoreR){
            telemetry.addData("SetUp", "MGM");
            telemetry.update();
            pRightTurn(113);
            setPower(0.5);
            sleep(10000);

            moveTime(1500, .4);
            sleep(1000);
            moveToRange(15.0);
            setTeamMarker();
            sleep(1000);
            dropTeamMarker();
            sleep(1000);
            setTeamMarker();
            pRightTurn(55);
            pMoveBackward(5000);


        }
        if (scoreR < scoreL && scoreR < scoreM){
            telemetry.addData("SetUp", "MMG");
            telemetry.update();
            pRightTurn(145);
            setPower(0.5);
            sleep(1500);
            moveToRange(15);
            turnToPosition(-40);
            moveToRange(20);
            dropTeamMarker();
            sleep(500);
            turnToPosition(55);
            sleep(1000);
            setPower(-0.5);
            sleep(5000);
        }

        //getAvgColorOnLeft(bob);
        //Image rgb = getImage();
        //Mat matrix = new Mat(rgb.getWidth(), rgb.getHeight(), CV_16UC3, rgb.getPixels());

    }


    public void vuforiaInit() throws InterruptedException{
        /*To access the image: you need to iterate through the images of the frame object:*/

        //getImage();
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = "AQ1iIdT/////AAAAGZ0U6OKRfU8tpKf9LKl/7DM85y3Wp791rb6q3WwHfYaY53vqKSjAO8wU2FgulWnDt6gLqu9hB33z1reejMz/NyfL8u11QZlMIbimmnP/v4hvoXZWu0p62V9eMG3R2PQ3Z7rZ0qK8HwsQYE/0jmBhTy0D17M4fWpNW64QQnMJqFxq/N1BXm32PEInYDHBYs7WUrHL5oa9xeSSurxUq/TqDpeJwQM+1/GYppdAqzbcM1gi3yzU7JDLdNtOZ6+lbi5uXlU++GnFvQaEXL9uVcnTwMEgBhBng6oOEVoEDXiSUBuZHuMRGZmHfVXSNE3m1UXWyEdPTlMRI5vfEwfsBHmQTmvYr/jJjng3+tBpu85Q1ivo";
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        vuforia = ClassFactory.getInstance().createVuforia(params);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB565 format for the image
        vuforia.setFrameQueueCapacity(4); //tells VuforiaLocalizer to only store one frame at a time
    }
    public Bitmap getImage() throws InterruptedException {


        /*To access the image: you need to iterate through the images of the frame object:*/

        //getImage();


        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        long numImages = frame.getNumImages();
        Image rgb = null;
        for (int i = 0; i < numImages; i++) {
            Image img = frame.getImage(i);
            int fmt = img.getFormat();
            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;
            }
        }

        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());

        return bm;
        // construct an OpenCV mat from the bitmap using Utils.bitmapToMat()
       /*
       Mat mat = new Mat(bm.getWidth(), bm.getHeight(), CvType.CV_8UC4);

       telemetry.addData("Hello2", "Hello");
       telemetry.update();
       Utils.bitmapToMat(bm, mat);
       telemetry.addData("Hello3", "Hello");
       telemetry.update();

       // convert to BGR before returning
       Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);

       frame.close();

       return mat;
       */

    }

    public void flyByPictureTake() throws InterruptedException{
        double start = getFunctionalGyroYaw();
        double proximity = Math.abs(110);
        boolean pic1Taken = false;
        boolean pic2Taken = false;
        boolean pic3Taken = false;
        double rScore = Double.MAX_VALUE, mScore = Double.MAX_VALUE, lScore = Double.MAX_VALUE;

        Bitmap right, middle, left;
        while (Math.abs(getFunctionalGyroYaw() - start) < 110) {
            proximity = Math.abs((Math.abs(getFunctionalGyroYaw() - start) - 110));
            turn(proximity * .0025 + .10);
            if (!pic1Taken && Math.abs(getFunctionalGyroYaw() - start) > 55){
                pic1Taken = true;
                right = getImage();
                rScore = findCentralWhitness(right);
            }
            if (!pic2Taken && Math.abs(getFunctionalGyroYaw() - start) > 80){
                pic2Taken = true;
                middle = getImage();
                mScore = findCentralWhitness(middle);
            }
            if (!pic3Taken && Math.abs(getFunctionalGyroYaw() - start) > 105){
                pic1Taken = true;
                left = getImage();
                lScore = findCentralWhitness(left);
            }

        }

        telemetry.addData("Scores", lScore + " " + mScore + " " + rScore);
        telemetry.update();
        setZero();
        sleep(10000);
    }

    public void getAvgColorOnLeft(Bitmap bm_img) throws InterruptedException{
        int RED_COUNTER = 0, BLUE_COUNTER = 0, X1 = 0, X2 = 0;
        Color cur_color = null;
        int cur_color_int, rgb[] = new int[3];
        float hsv[] = new float[3];
        int hueMax = 0;
        double hueTotal = 0;
        double satTotal = 0;
        double valueTotal = 0;
        int count = 0;
        int avgX = 0;
        int avgY = 0;
        double leftScore = 0;
        double lCount = 0;
        double midScore = 0;
        double mCount = 0;
        double rightScore = 0;
        double rCount = 0;

        int width = bm_img.getWidth();
        int height = bm_img.getHeight();

        for (int i = 0; i < width/3; i = i + 2){
            for (int j = 0; j < height; j = j + 2){
                cur_color_int = bm_img.getPixel(i,j);
                rgb[0] = cur_color.red(cur_color_int);
                rgb[1] = cur_color.green(cur_color_int);
                rgb[2] = cur_color.blue(cur_color_int);

                Color.RGBToHSV(rgb[0],rgb[1],rgb[2],hsv);

                double hue = hsv[0];
                hueTotal += hue;
                count++;
                telemetry.addData("Average", hueTotal/count);
                telemetry.update();

            }
        }
        sleep(10000);

    }

    public void colorAnalyzer(Bitmap bm_img, int pix) {
        int RED_COUNTER = 0, BLUE_COUNTER = 0, X1 = 0, X2 = 0;
        Color cur_color = null;
        int cur_color_int, rgb[] = new int[3];
        float hsv[] = new float[3];
        int hueMax = 0;
        double hueTotal = 0;
        double satTotal = 0;
        double valueTotal = 0;
        int count = 0;
        int avgX = 0;
        int avgY = 0;
        double leftScore = 0;
        double lCount = 0;
        double midScore = 0;
        double mCount = 0;
        double rightScore = 0;
        double rCount = 0;

        int width = bm_img.getWidth();
        int height = bm_img.getHeight();

        for (int i = 0; i < width; i = i + 2){
            for (int j = (int)(height * 1.0/3); j < height * 2.0/3; j = j + 2){
                cur_color_int = bm_img.getPixel(i,j);
                rgb[0] = cur_color.red(cur_color_int);
                rgb[1] = cur_color.green(cur_color_int);
                rgb[2] = cur_color.blue(cur_color_int);

                Color.RGBToHSV(rgb[0],rgb[1],rgb[2],hsv);

                double hue = hsv[0];
                double sat = hsv[1];
                if (hue  <= 10 && sat <= 0.1){
                    if (i < width/3.0){
                        leftScore = leftScore + Math.pow((i),2) + Math.pow((i - 1.0/3.0 * width),2);
                        lCount++;
                    }
                    else if (i < 2.0/3.0 * width){
                        midScore = midScore + Math.pow((i - 1.0/3.0 * width),2) + Math.pow((i - 2.0/3.0 * width),2);
                        mCount++;
                    }
                    else{
                        rightScore = rightScore + Math.pow((i - 2.0/3.0 * width),2) + Math.pow((i - width),2) ;
                        rCount++;
                    }
                }


            }
        }
        double l = leftScore/lCount;
        double m = midScore/mCount;
        double r = rightScore/rCount;

        telemetry.addData("L", l + " " + lCount);
        telemetry.addData("M", m + " " + mCount);
        telemetry.addData("R", r + " " + rCount);
        if (l < m && l < r){
            if (lCount > mCount && lCount > rCount){
                telemetry.addData("Left", "Left");
            }
            else{
                telemetry.addData("Likely", "Left");
            }
        }
        if (m < r && m < l){
            if (mCount > lCount && mCount > rCount){
                telemetry.addData("Mid", "Mid");
            }
            else{
                telemetry.addData("Likely", "Mid");
            }
        }
        if (r < m && r < l){
            if (rCount > mCount && rCount > lCount){
                telemetry.addData("Right", "Right");
            }
            else{
                telemetry.addData("Likely", "Right");
            }
        }
        telemetry.update();

        sleep(5000);
    }

    public void colorAnalyzerFastProcessing(Bitmap bm_img, int pix) {
        int RED_COUNTER = 0, BLUE_COUNTER = 0, X1 = 0, X2 = 0;
        Color cur_color = null;
        int cur_color_int, rgb[] = new int[3];
        float hsv[] = new float[3];
        int hueMax = 0;
        double hueTotal = 0;
        double satTotal = 0;
        double valueTotal = 0;
        int count = 0;
        int avgX = 0;
        int avgY = 0;
        double leftScore = 0;
        double lCount = 0;
        double midScore = 0;
        double mCount = 0;
        double rightScore = 0;
        double rCount = 0;

        int width = bm_img.getWidth();
        int height = bm_img.getHeight();

        for (int i = 0; i < width; i = i + 3){
            for (int j = (int)(height * 1.0/3); j < height * 2.0/3; j = j + 3){
                cur_color_int = bm_img.getPixel(i,j);
                rgb[0] = cur_color.red(cur_color_int);
                rgb[1] = cur_color.green(cur_color_int);
                rgb[2] = cur_color.blue(cur_color_int);

                Color.RGBToHSV(rgb[0],rgb[1],rgb[2],hsv);

                double hue = hsv[0];
                double sat = hsv[1];
                if (hue  <= 10 && sat <= 0.1){
                    if (i < width/3.0){
                        leftScore = leftScore + Math.pow((i),2) + Math.pow((i - 1.0/3.0 * width),2);
                        lCount++;
                    }
                    else if (i < 2.0/3.0 * width){
                        midScore = midScore + Math.pow((i - 1.0/3.0 * width),2) + Math.pow((i - 2.0/3.0 * width),2);
                        mCount++;
                    }
                    else{
                        rightScore = rightScore + Math.pow((i - 2.0/3.0 * width),2) + Math.pow((i - width),2) ;
                        rCount++;
                    }
                }


            }
        }
        double l = leftScore/lCount;
        double m = midScore/mCount;
        double r = rightScore/rCount;

        if (lCount < rCount && lCount < mCount){
            telemetry.addData("Left", "Left");
            telemetry.update();
        }
        else if (mCount < lCount && mCount < rCount){
            telemetry.addData("Mid", "Mid");
            telemetry.update();
        }
        else if (rCount < lCount && rCount < mCount){
            telemetry.addData("Right", "Right");
            telemetry.update();
        }
        sleep(5000);

    }

    public void determineCompactnessOfYellowPixels(Bitmap bm_img, int pix) {
        int RED_COUNTER = 0, BLUE_COUNTER = 0, X1 = 0, X2 = 0;
        Color cur_color = null;
        int cur_color_int, rgb[] = new int[3];
        float hsv[] = new float[3];
        int hueMax = 0;
        double hueTotal = 0;
        double satTotal = 0;
        double valueTotal = 0;
        int count = 0;
        int avgX = 0;
        int avgY = 0;
        double leftScore = 0;
        double lCount = 0;
        double midScore = 0;
        double mCount = 0;
        double rightScore = 0;
        double rCount = 0;

        int width = bm_img.getWidth();
        int height = bm_img.getHeight();

        for (int i = 0; i < width; i = i + 2){
            for (int j = (int)(height * 1.0/3); j < height * 2.0/3; j = j + 2){
                cur_color_int = bm_img.getPixel(i,j);
                rgb[0] = cur_color.red(cur_color_int);
                rgb[1] = cur_color.green(cur_color_int);
                rgb[2] = cur_color.blue(cur_color_int);

                Color.RGBToHSV(rgb[0],rgb[1],rgb[2],hsv);

                double hue = hsv[0];
                if (hue >= 35 && hue <=45){
                    if (i < width/3.0){
                        leftScore = leftScore + Math.pow((i),2) + Math.pow((i - 1.0/3.0 * width),2);
                        lCount++;
                    }
                    else if (i < 2.0/3.0 * width){
                        midScore = midScore + Math.pow((i - 1.0/3.0 * width),2) + Math.pow((i - 2.0/3.0 * width),2);
                        mCount++;
                    }
                    else{
                        rightScore = rightScore + Math.pow((i - 2.0/3.0 * width),2) + Math.pow((i - width),2) ;
                        rCount++;
                    }
                }


            }
        }
        double l = leftScore/lCount;
        double m = midScore/mCount;
        double r = rightScore/rCount;

        telemetry.addData("L", l + " " + lCount);
        telemetry.addData("M", m + " " + mCount);
        telemetry.addData("R", r + " " + rCount);
        if (l < m && l < r){
            if (lCount > mCount && lCount > rCount){
                telemetry.addData("Left", "Left");
            }
            else{
                telemetry.addData("Likely", "Left");
            }
        }
        if (m < r && m < l){
            if (mCount > lCount && mCount > rCount){
                telemetry.addData("Mid", "Mid");
            }
            else{
                telemetry.addData("Likely", "Mid");
            }
        }
        if (r < m && r < l){
            if (rCount > mCount && rCount > lCount){
                telemetry.addData("Right", "Right");
            }
            else{
                telemetry.addData("Likely", "Right");
            }
        }
        telemetry.update();

        sleep(5000);
    }

    public double findCentralWhitness(Bitmap bm_img) throws InterruptedException{

        Color cur_color = null;
        int cur_color_int, rgb[] = new int[3];
        float hsv[] = new float[3];
        int hueMax = 0;
        double whiteScore = 0;

        int width = bm_img.getWidth();
        int height = bm_img.getHeight();

        double centerX = width / 2;
        double centerY = height * 3.0 / 4;

        double count = 0;

        double distance = 0;

        for (int i = (int)(width * 0.25); i < (int)(width * 0.75); i = i + 4){
            for (int j = (int)(height * 1.0/3); j < height; j = j + 4){
                cur_color_int = bm_img.getPixel(i,j);
                rgb[0] = cur_color.red(cur_color_int);
                rgb[1] = cur_color.green(cur_color_int);
                rgb[2] = cur_color.blue(cur_color_int);

                Color.RGBToHSV(rgb[0],rgb[1],rgb[2],hsv);

                double hue = hsv[0];
                double sat = hsv[1];

                if (hue <= 50 && hue >= 30 && sat >= 0.80){
                    count++;
                    distance += getDistance(i,centerX,j,centerY);
                }



            }
        }

        try {
            telemetry.addData("Central Tendency", distance / count);
            telemetry.addData("Count", count);
            telemetry.addData("Selection Index", distance/count/count);
        }
        catch (ArithmeticException e){
            telemetry.addData("POints", "None Found");
        }
        telemetry.update();

        double score = distance/count/count;

        if (Double.isNaN(score)){
            return Double.MAX_VALUE;
        }

        return distance/count/count;
    }

    public double getDistance(double x1, double x2, double y1, double y2) throws InterruptedException{
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    public void determineBestDifferenceOn2Halves(Bitmap bm_img) throws InterruptedException{
        Color cur_color = null;
        int cur_color_int, rgb[] = new int[3];
        float hsv[] = new float[3];
        int hueMax = 0;
        double whiteScore = 0;

        int width = bm_img.getWidth();
        int height = bm_img.getHeight();

        double centerX = width / 2;
        double centerY = height * 3.0 / 4;

        double[] hsvLeft = new double[3];
        int leftCount = 0;
        int rightCount = 0;
        double[] hsvRight = new double[3];

        double count = 0;

        double distance = 0;

        for (int i = (int)(0); i < (int)(width); i = i + 1){
            for (int j = (int)(0); j < height; j = j + 1){
                cur_color_int = bm_img.getPixel(i,j);
                rgb[0] = cur_color.red(cur_color_int);
                rgb[1] = cur_color.green(cur_color_int);
                rgb[2] = cur_color.blue(cur_color_int);

                Color.RGBToHSV(rgb[0],rgb[1],rgb[2],hsv);

                double hue = hsv[0];
                double sat = hsv[1];
                double value = hsv[2];

                if (i < width/2){
                    hsvLeft[0] += hue;
                    hsvLeft[1] += sat;
                    hsvLeft[2] += value;
                    leftCount++;
                }
                else{
                    hsvRight[0] += hue;
                    hsvRight[1] += sat;
                    hsvRight[2] += value;
                    rightCount++;
                }



            }

        }
        hsvLeft[0] = hsvLeft[0] / leftCount;
        hsvLeft[1] = hsvLeft[1] / leftCount;
        hsvLeft[2] = hsvLeft[2] / leftCount;

        hsvRight[0] = hsvRight[0] / rightCount;
        hsvRight[1] = hsvRight[1] / rightCount;
        hsvRight[2] = hsvRight[2] / rightCount;

        telemetry.addData("Left", Arrays.toString(hsvLeft));
        telemetry.addData("Right", Arrays.toString(hsvRight));
        telemetry.update();

        sleep(5000);
    }
}











