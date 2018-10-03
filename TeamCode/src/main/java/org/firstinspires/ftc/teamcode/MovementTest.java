package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class MovementTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        moveForwardEncoder(.2, 500);
        sleep(500);
        moveBackwardEncoder(.2, 500);
    }
}