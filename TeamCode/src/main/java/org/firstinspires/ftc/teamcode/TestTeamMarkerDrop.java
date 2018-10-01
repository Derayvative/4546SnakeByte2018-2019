package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;

@Autonomous
public class TestTeamMarkerDrop extends AutoOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        {
            initialize();
            waitForStart();
            setTeamMarker();
            pRightTurn(45);
            sleep(300);
            setPower(-.2);
            sleep(2500);
            dropTeamMarker();
            sleep(300);
            setPower(.2);
            setTeamMarker();
            sleep(3000);
            setZero();
        }
    }
}

