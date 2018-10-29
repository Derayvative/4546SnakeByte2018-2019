package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class MovementTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        /*
        moveToRangePIStraighten(50, 0);
        moveToRangePIStraighten(20, 0);
        moveToRangePIStraighten(70, 0);
        */
        //PILeftTurn(90);
        //PILeftTurn(45);
        //turnToPositionPI(0);
        //PILeftTurn(45);
        //pLeftTurn(90);
        //pRightTurn(45);
        //pRightTurn(90);
        //turnToPositionPI(0);
       // moveToRangePIStraightenToStartAngle(50);
        turnToPosition(0);
        //pLeftTurn(46);
    }
}