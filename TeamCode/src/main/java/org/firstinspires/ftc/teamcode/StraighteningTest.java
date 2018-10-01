package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class StraighteningTest extends AutoOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        moveForwardStraight(0.3, 0, 7000);
    }
}
