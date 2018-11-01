package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutonomousDepot extends AutoOpMode {

    GoldDetectorVuforia GDV = new GoldDetectorVuforia();
    Bitmap image1;
    Bitmap image2;
    Bitmap image3;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        //pLeftTurn(55);
        turnToPosition(-55);
        turnToPosition(-55);

        image1 = GDV.getImage();
        telemetry.addData("Hello","Hello");
        telemetry.update();

        sleep(300);
        double scoreR = GDV.findCentralYellowness(image1);
        //pLeftTurn(25);
        turnToPosition(-85);
        turnToPosition(-85);
        sleep(300);
        image2 = GDV.getImage();
        double scoreM = GDV.findCentralYellowness(image2);
        turnToPosition(-110);
        turnToPosition(-110);
        //pLeftTurn(25);
        sleep(300);
        image3 = GDV.getImage();
        double scoreL = GDV.findCentralYellowness(image3);
        telemetry.addData("L", scoreL);
        telemetry.addData("M", scoreM);
        telemetry.addData("R", scoreR);
        telemetry.update();
        //sleep(1000);
        if (scoreL < scoreM && scoreL < scoreR){
            //PIRightTurn(73);
           // turnToPosition(-25);
            turnToPosition(-25);
            telemetry.addData("SetUp", "GMM");
            telemetry.update();

           //setPower(0.5);
           //sleep(10000);

            //setPower(0.5);
            setPower(0.35);
            sleep(1500);
            moveToRangePIStraightenToStartAngle(15);
            turnToPosition(40);
            moveToRangePIStraightenToStartAngle(20);
            //scoreMarker();
            turnToPosition(55);
            //setPower(-0.4);
            //sleep(10000);
            sleep(200);
            glideAgainstWallMovingBack();
        }
        if (scoreM < scoreL && scoreM < scoreR) {
            telemetry.addData("SetUp", "MGM");
            telemetry.update();
            //PIRightTurn(113);
            //turnToPosition(0);
            turnToPosition(0);

            moveTime(2000, .35);
            //moveToRangePIStraightenToStartAngle(20.0);
            //scoreMarker();
            moveToRangePIStraightenToStartAngle(23);
            pRightTurn(55);
            //pMoveBackward(10000);
            turnToPosition(55);
            sleep(200);
            glideAgainstWallMovingBack();
        }
        if (scoreR < scoreL && scoreR < scoreM){
            telemetry.addData("SetUp", "MMG");
            telemetry.update();
            //PIRightTurn(145);
            turnToPosition(0);
            turnToPosition(32);
            //urnToPositionPI(25);

            setPower(0.25);
            sleep(1500);
            moveToRangePIStraightenToStartAngle(20);
            turnToPosition(-45);
            turnToPosition(-45);

            //moveToRangePIStraightenToStartAngle(15);
            //scoreMarker();
            moveToRangePIStraighten(12, -45);
            turnToPosition(48);
            //sleep(1000);
            //setPower(-0.5);
            //sleep(5000);
            sleep(200);
            glideAgainstWallMovingBack();
        }

    }
}
