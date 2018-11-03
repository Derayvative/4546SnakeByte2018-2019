package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class TurnTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        setTeamMarker();
        waitForStart();
        pLeftTurn(25);
        sleep(1000);
        pLeftTurn(45);
        sleep(1000);
        pLeftTurn(90);
        sleep(1000);


    }
}
