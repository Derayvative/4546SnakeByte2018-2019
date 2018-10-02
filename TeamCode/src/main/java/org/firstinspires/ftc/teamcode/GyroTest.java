package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class GyroTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        double pastPitch;
        double currentPitch = 0;
        setPower(0.5);
        while (opModeIsActive()){
            telemetry.addData("Yaw", getGyroYaw());
            telemetry.addData("FunctionalYaw", getFunctionalGyroYaw());
            telemetry.addData("Roll", getGyroRoll());
            pastPitch = currentPitch;
            currentPitch = getGyroPitch();
            telemetry.addData("Pitch", currentPitch);
            telemetry.addData("Pitch difference", currentPitch - pastPitch);
            telemetry.update();
        }
    }
}
