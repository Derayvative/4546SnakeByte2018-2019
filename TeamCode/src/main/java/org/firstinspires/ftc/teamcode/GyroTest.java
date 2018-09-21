package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class GyroTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("Yaw", getGyroYaw());
            telemetry.addData("FunctionalYaw", getFunctionalGyroYaw());
            telemetry.addData("Roll", getGyroRoll());
            telemetry.addData("Pitch", getGyroPitch());
            telemetry.update();
        }
    }
}
