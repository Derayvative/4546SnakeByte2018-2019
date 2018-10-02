package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class TurnTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        setTeamMarker();
        waitForStart();
        moveToRangeStraighten(15,0);
        pRightTurn(90);
        moveToRangeStraighten(50, 90);
        pRightTurn(135);
        sleep(1000);
        dropTeamMarker();
        sleep(1000);
        setTeamMarker();
        pRightTurn(45);
        setPower(0.5);
        sleep(10000);
    }
}
