package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class BingoTeleOPtroll extends OpMode{

    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > .1) {
            FL.setPower(gamepad1.left_stick_y);
            BL.setPower(gamepad1.left_stick_y);
        } else {
            FL.setPower(0);
            BL.setPower(0);
        }

        if (Math.abs(gamepad1.right_stick_y) > .1) {
            FR.setPower(-gamepad1.right_stick_y);
            BR.setPower(-gamepad1.right_stick_y);
        } else {
            FR.setPower(0);
            BR.setPower(0);
        }

        //TODO: Add controls for the intake motor
        //TODO: Potentially add a half-speed for the drive train

    }
}
