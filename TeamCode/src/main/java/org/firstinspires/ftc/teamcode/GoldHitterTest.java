package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class GoldHitterTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        GoldDetectorColorSensor GD = new GoldDetectorColorSensor(CS, DS);
        waitForStart();
        setPower(0.15);
        String objectSeen = "";
        while (!objectSeen.equals("GOLD_CLOSE") && !objectSeen.equals("GOLD_FAR") && opModeIsActive()){
            double[] RGB = {CS.red(), CS.green(), CS.blue()};
            ColorSpaceConvertor.capRGB(RGB);
            double[] CIELAB = ColorSpaceConvertor.RGVtoCIELAB(RGB);
            //objectSeen = GD.identifyObject();
            if (GD.isObjectInRange()){
                setZero();
                sleep(300);
                objectSeen = GD.identifyObject();
                setPower(0.15);
                sleep(400);
            }
            telemetry.addData("Gold Similarity", ColorSpaceConvertor.CalculateCIELABSimilarity(CIELAB, RobotConstants.GOLD_CIELAB_VALUES_CLOSE));
            telemetry.addData("Mineral Similarity", ColorSpaceConvertor.CalculateCIELABSimilarity(CIELAB, RobotConstants.MINERAL_CIELAB_VALUES_CLOSE));
            telemetry.addData("Gold Similarity Far", ColorSpaceConvertor.CalculateCIELABSimilarity(CIELAB, RobotConstants.GOLD_CIELAB_VALUES_FAR));
            telemetry.addData("Mineral Similarity Far", ColorSpaceConvertor.CalculateCIELABSimilarity(CIELAB, RobotConstants.MINERAL_CIELAB_VALUES_FAR));
            telemetry.addData("OBJECT", objectSeen);
            telemetry.update();
            idle();
        }
        setZero();
        pturn(360);
    }
}
