package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RangeSensorTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        turn(0.2);
        double highestReading = 0;
        double highestAngle = 0;

        while(opModeIsActive()){
            double reading = getRangeReading();
            double angle = getFunctionalGyroYaw();
            telemetry.addData("Range", reading);
            telemetry.addData("Gyro", angle);
            if (reading > highestReading){
                highestReading = reading;
                highestAngle = angle;
            }
            telemetry.addData("Highest", highestReading);
            telemetry.addData("H Angle", highestAngle);
            telemetry.update();
            sleep(300);
        }

    }
}
