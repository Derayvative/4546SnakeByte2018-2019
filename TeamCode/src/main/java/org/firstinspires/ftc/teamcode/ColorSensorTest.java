package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous
public class ColorSensorTest extends LinearOpMode {
    ColorSensor CS;

    @Override
    public void runOpMode() throws InterruptedException {
        CS = hardwareMap.colorSensor.get("goldDetector");
        GoldDetectorColorSensor goldFinder = new GoldDetectorColorSensor(CS);

        waitForStart();

        while (opModeIsActive()) {
            double[] RGB = {CS.red(), CS.green(), CS.blue()};
            ColorSpaceConvertor.capRGB(RGB);
            double[] CIELAB = ColorSpaceConvertor.RGVtoCIELAB(RGB);
            telemetry.addData("Gold Similarity", ColorSpaceConvertor.CalculaeCIELABSimilarity(CIELAB, RobotConstants.GOLD_CIELAB_VALUES_CLOSE));
            telemetry.addData("Mineral Similarity", ColorSpaceConvertor.CalculaeCIELABSimilarity(CIELAB, RobotConstants.MINERAL_CIELAB_VALUES_CLOSE));
            telemetry.update();
            sleep(1000);
        }
    }
}
