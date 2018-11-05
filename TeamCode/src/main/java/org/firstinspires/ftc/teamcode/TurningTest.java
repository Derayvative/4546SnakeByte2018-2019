package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous
public class TurningTest extends AutoOpMode {
    VuforiaLocalizer vuforia;
    Bitmap image1;
    Bitmap image2;
    Bitmap image3;

    @Override
    public void runOpMode() throws InterruptedException {
        //vuforiaInit();
        initialize();
        waitForStart();
        pLeftTurn(90);
        sleep(1000);
        pLeftTurnTest(90);
        sleep(1000);
        pLeftTurn(90);
        sleep(1000);
        pLeftTurnTest(45);
    }
}
