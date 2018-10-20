package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous

public class Test_pMoveForward extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        pMoveForward(1000);
        sleep(1000);
        pMoveForward(1000);
    }
}