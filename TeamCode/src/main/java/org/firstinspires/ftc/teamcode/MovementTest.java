package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class MovementTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        setPower(0.5);
        sleep(1500);
        moveToRange(15);
        turnToPosition(-40);
        moveToRange(30);
        turnToPosition(-130);
        dropTeamMarker();
        sleep(1000);
        setPower(-0.5);
        sleep(5000);
        //turnToPosition(90);
    }
}