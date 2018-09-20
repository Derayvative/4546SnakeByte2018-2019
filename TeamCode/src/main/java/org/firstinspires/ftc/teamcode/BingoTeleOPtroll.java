package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

// Luca test 9/20
// Luca's push 9/20
@TeleOp
public class BingoTeleOPtroll extends OpMode{

    // ======= instance variables: =======

    private boolean halfSpeed;

    // DcMotors - Drive-train

    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    // DcMotors - Intake
    // (9/18: needs hardware-map on phone)
    DcMotor IT;

    @Override


    // =======Initialization: Hardware Mapping, variable setting =======
    // functions upon initialization

    public void init() {

        // dynamic variables:

        halfSpeed = false;

        //Drive-train
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

        //Intake
        //Commented out until Trollbot has an intake
        //IT = hardwareMap.dcMotor.get("IT");



    }

    @Override

    // ======= Controls (as Tele-Op is running) =======

    public void loop() {

        // Drive-train:

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


        // Intake:

        /*
        if (gamepad1.right_trigger > 0.1) {
            IT.setPower(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger > 0.1){
            IT.setPower(-gamepad1.left_trigger);
        }
        else{
            IT.setPower(0);
        }
        */

        // Half-speed:



    } // end of loop



        //TODO: Add controls for the intake motor (UPDATE: created, but needs HW Map)

        //TODO: Potentially add a half-speed for the drive train
        // Slackhub test
    }

