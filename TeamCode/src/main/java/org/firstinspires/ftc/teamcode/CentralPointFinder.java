package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous
public class CentralPointFinder extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        GoldDetectorVuforia GDV = new GoldDetectorVuforia(false);

        //waitForStart();
        while (!isStarted()){
            //sleep(100);
            Bitmap bm = GDV.getImage();
            //70 -40 40 Cube
            //20 -80 -80 Sphere
            double[] array = GDV.classifyAsGoldOrMineral(bm);
            //double[] array = GDV.getCIELABOfMiddlePoint(bm);
            telemetry.addData("Scores", Arrays.toString(array));
            telemetry.addData("ID", GDV.determineLocationOfGold(bm));
            tallyVisionResults(GDV.determineLocationOfGold(bm));
            telemetry.addData("RESULTS", Arrays.toString(vision));
            //telemetry.addData("Best Match", GDV.getBestThreePlaceMatchExtended(array));
            //telemetry.addData("Num POints", array[3]);
            telemetry.update();
        }
    }
}
