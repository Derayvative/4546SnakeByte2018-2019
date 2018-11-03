package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.RobotConstants.TEAM_MARKER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.TEAM_MARKER_UP_POSITION;

// Luca test 9/20
// Luca's push 9/20
// Luca's pushhhhhh 9/20
// Landon 9/20

@TeleOp
public class TeleOpMode extends OpMode{

    // ======= instance variables: =======

    private double halfSpeed;

    // DcMotors - Drive-train


    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    DcMotor middleIntake;
    DcMotor outerIntake;

    DcMotor lift;

    Servo TeamMarker;

    Servo basketServo;
    Servo gateServo;

    boolean basketServoPositionDown = true;
    boolean gateServoPositionDown = true;

    double servoPos;

    //Time-related variables
    ElapsedTime time;
    double mostRecentBPress;
    double mostRecentAPress;

    //Constants




    // DcMotors - Intake
    // (9/18: needs hardware-map on phone)
    DcMotor IT;

    @Override


    // =======Initialization: Hardware Mapping, variable setting =======
    // functions upon initialization

    public void init() {

        // dynamic variables:


        halfSpeed = 1;

        //Drive-train
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        middleIntake = hardwareMap.dcMotor.get("middleIntake");
        outerIntake = hardwareMap.dcMotor.get("outerIntake");
        lift = hardwareMap.dcMotor.get("lift");

        TeamMarker = hardwareMap.servo.get("TeamMarker");

        basketServo = hardwareMap.servo.get("basketServo");
        gateServo = hardwareMap.servo.get("gateServo");

        //Intake
        //Commented out until Trollbot has an intake
        //IT = hardwareMap.dcMotor.get("IT");
        //TeamMarker.setPosition(TEAM_MARKER_UP_POSITION);

        //Time-Related Variables
        time = new ElapsedTime();
        time.reset();
        mostRecentBPress = 0;
        mostRecentAPress = 0;



    }

    @Override

    // ======= Controls (as Tele-Op is running) =======

    public void loop() {



        // Drive-train:

        if (Math.abs(gamepad1.left_stick_y) > .1) {
            FL.setPower(gamepad1.left_stick_y * halfSpeed);
            BL.setPower(gamepad1.left_stick_y * halfSpeed);
        } else {
            FL.setPower(0);
            BL.setPower(0);
        }


        if (Math.abs(gamepad1.right_stick_y) > .1) {
            FR.setPower(-gamepad1.right_stick_y * halfSpeed);
            BR.setPower(-gamepad1.right_stick_y * halfSpeed);
        } else {
            FR.setPower(0);
            BR.setPower(0);
        }

        if (time.milliseconds() - mostRecentBPress > 100) {
            if (gamepad1.b && (halfSpeed == 1)) {
                halfSpeed = 0.5;
            } else if ((gamepad1.b) && (halfSpeed == 0.5)) {
                halfSpeed = 1;
            }
            mostRecentBPress = time.milliseconds();
        }

        if (gamepad2.right_trigger > 0.1){
            middleIntake.setPower(-0.65);
            outerIntake.setPower(-0.65);
        }
        else if (gamepad2.left_trigger > 0.1){
            middleIntake.setPower(0.65);
            outerIntake.setPower(0.65);
        }
        else{
            middleIntake.setPower(0);
            outerIntake.setPower(0);
        }

        if (gamepad2.right_stick_y > 0.1){
            lift.setPower(gamepad2.right_stick_y);
        }
        else if (gamepad2.right_stick_y < -0.1){
            lift.setPower(gamepad2.right_stick_y);
        }
        else{
            lift.setPower(0);
        }

        if (gamepad2.a && basketServoPositionDown && time.milliseconds() - mostRecentAPress > 250){
            basketServoPositionDown = false;
            basketServo.setPosition(0.4);
            mostRecentAPress = time.milliseconds();
        }
        else if (gamepad2.a && !basketServoPositionDown && time.milliseconds() - mostRecentAPress > 250){
            basketServoPositionDown = true;
            basketServo.setPosition(0.9);
            mostRecentAPress = time.milliseconds();
        }

        if (gamepad2.b && gateServoPositionDown && time.milliseconds() - mostRecentAPress > 250){
            gateServoPositionDown = false;
            gateServo.setPosition(0.7);
            mostRecentAPress = time.milliseconds();
        }
        else if (gamepad2.b && !gateServoPositionDown && time.milliseconds() - mostRecentAPress > 250){
            gateServoPositionDown = true;
            gateServo.setPosition(0.3);
            mostRecentAPress = time.milliseconds();
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

