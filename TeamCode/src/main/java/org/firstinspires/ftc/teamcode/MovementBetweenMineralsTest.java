package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.Arrays;

@Autonomous
public class MovementBetweenMineralsTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        GoldDetectorColorSensor GD = new GoldDetectorColorSensor(CS,DS);
        waitForStart();
        moveForwardStraightUntilObjectDetected(0.15, 0, 5000);
        sleep(1000);
        alignWithSample();
        setPower(0.15);
        sleep(500);
        double[] RGB1 = {CS.red(), CS.green(), CS.blue()};
        ColorSpaceConvertor.capRGB(RGB1);
        double[] sample1 = ColorSpaceConvertor.RGVtoCIELAB(RGB1);
        sleep(1000);
        moveForwardStraightUntilObjectDetected(0.15, 0, 5000);
        sleep(1000);
        alignWithSample();
        setPower(0.15);
        sleep(500);
        double[] RGB2 = {CS.red(), CS.green(), CS.blue()};
        ColorSpaceConvertor.capRGB(RGB2);
        double[] sample2 = ColorSpaceConvertor.RGVtoCIELAB(RGB2);
        sleep(1000);
        moveForwardStraightUntilObjectDetected(0.15, 0, 5000);
        sleep(1000);
        alignWithSample();
        setPower(0.12);
        sleep(500);
        double[] RGB3 = {CS.red(), CS.green(), CS.blue()};
        ColorSpaceConvertor.capRGB(RGB3);
        double[] sample3 = ColorSpaceConvertor.RGVtoCIELAB(RGB3);
        sleep(1000);
        telemetry.addData("Results", GD.identifyLeastSimilarSample(sample1,sample2,sample3));
        telemetry.addData("1", Arrays.toString(sample1));
        telemetry.addData("2", Arrays.toString(sample2));
        telemetry.addData("3", Arrays.toString(sample3));
        telemetry.addData("12", ColorSpaceConvertor.CalculateCIELABSimilarity(sample1, sample2));
        telemetry.addData("13", ColorSpaceConvertor.CalculateCIELABSimilarity(sample1, sample3));
        telemetry.addData("23", ColorSpaceConvertor.CalculateCIELABSimilarity(sample3, sample2));
        telemetry.update();
        sleep(15000);
    }
}
