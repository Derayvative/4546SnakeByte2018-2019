package org.firstinspires.ftc.teamcode;

public class ServoTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        TeamMarker.setPosition(0);
        for (double i = 0; i <= 1.0; i = i + 0.1){
            TeamMarker.setPosition(i);
            telemetry.addData("Position", i);
            telemetry.update();
            sleep(500);
        }
    }
}
