package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous
public class pturnAutoTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        //GoldDetectorColorSensor GD = new GoldDetectorColorSensor(CS, DS);
        waitForStart();
        /*

        */
        pRightTurn(90);
        sleep(1000);
        pRightTurn(180);
        sleep(1000);
        pRightTurn(270);

        sleep(1000);
        pLeftTurn(90);
        sleep(1000);
        pLeftTurn(180);
        sleep(1000);
        pLeftTurn(270);

    }
}
