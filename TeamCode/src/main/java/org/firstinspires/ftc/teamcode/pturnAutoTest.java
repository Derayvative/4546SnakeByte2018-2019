package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous
public class pturnAutoTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        setZero();
        pturn(90);
    }
}
