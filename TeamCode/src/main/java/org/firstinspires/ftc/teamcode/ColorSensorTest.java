package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import java.util.Arrays;

@Autonomous
public class ColorSensorTest extends LinearOpMode {
    ColorSensor CS;

    @Override
    public void runOpMode() throws InterruptedException {
        CS = hardwareMap.colorSensor.get("goldDetector");
        GoldDetectorColorSensor goldFinder = new GoldDetectorColorSensor(CS);

        waitForStart();
        telemetry.addData("Gold", Arrays.toString(goldFinder.collectYellowSample()));
        telemetry.update();
        sleep(5000);
        telemetry.addData("Red", CS.red());
        telemetry.addData("Blue", CS.blue());
        telemetry.addData("Green", CS.green());
        telemetry.update();
        sleep(5000);
    }
}
