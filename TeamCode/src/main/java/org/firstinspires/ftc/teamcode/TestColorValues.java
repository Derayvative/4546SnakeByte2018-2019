package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@Autonomous
public class TestColorValues extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        initializeDriveTrainOnly();
        CS = hardwareMap.colorSensor.get("tapeSensor");
        DS = hardwareMap.get(DistanceSensor.class, "tapeSensor");
        waitForStart();
        int redVal = CS.red();
        int blueVal = CS.blue();
        int greenVal = CS.green();
        telemetry.addData("Red Value: ", redVal);
        telemetry.addData("Blue Value: ", blueVal);
        telemetry.addData("Green Value: ", greenVal);
        telemetry.update();
        sleep(10000);
        setZero();
    }

}
