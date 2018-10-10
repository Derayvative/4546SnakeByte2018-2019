package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.opencv.android.JavaCamera2View;

@Autonomous
public class GyroPitchTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        double highestPitch = Double.MIN_VALUE;
        double lowestPitch = Double.MAX_VALUE;
        setPower(0.5);

        while (opModeIsActive()){
            double pitch = getGyroPitch();
            if (getGyroPitch() < lowestPitch){
                lowestPitch = pitch;
            }
            if (getGyroPitch() > highestPitch){
                highestPitch = pitch;
            }
            telemetry.addData("Pitch", pitch);
            telemetry.addData("Highest Pitch", highestPitch);
            telemetry.addData("Lowest Pitch", lowestPitch);
            telemetry.update();
        }
    }
}
