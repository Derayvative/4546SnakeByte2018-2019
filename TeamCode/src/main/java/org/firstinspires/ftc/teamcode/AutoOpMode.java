package org.firstinspires.ftc.teamcode;

import android.graphics.Camera;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.teamcode.RobotConstants.TEAM_MARKER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.TEAM_MARKER_UP_POSITION;


public abstract class AutoOpMode extends LinearOpMode{

    //Declare all Motors, Servos, Sensors, etc

    //Drive Train Motors

    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    //Non-Drive Train Motors

    DcMotor intake;

    //Servos

    Servo TeamMarker;

    //Sensors

    BNO055IMU imu;

    //Other Variables

    ElapsedTime time; //returns time in s, ms, or ns since last reset

    double currentGyro;
    double previousGyro;
    int gyroMultiplier;

    ColorSensor CS;
    DistanceSensor DS;
    ModernRoboticsI2cRangeSensor rangeSensor;


    //Constants

    //Initializes Motors, Servos, Sensors, etc when the robot is hanging
    public void initialize() throws InterruptedException{
        time =  new ElapsedTime();
        //Drive Train Motors

        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        TeamMarker = hardwareMap.servo.get("TeamMarker");
        //Configures the encoders for the motors

        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Non-Drive Train Motors

        //Servos

        //Sensors

        initializeGyro();

        //Other Variables

        CS = hardwareMap.colorSensor.get("goldDetector");
        DS = hardwareMap.get(DistanceSensor.class, "goldDetector");
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "rangeSensor");

        resetTimer();
        previousGyro = 0;
        currentGyro = 0;
        gyroMultiplier = 0;
    }

    //Initializes Gyro, since in the actual game the gyro may need to be initialized after
    //everything else (once the robot lands on the ground)
    public void initializeGyro() throws InterruptedException{

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

    }

    public double getGyroPitch() throws InterruptedException {
        Orientation angles = imu.getAngularOrientation();
        return (angles.secondAngle * -1);
    }

    public double getGyroRoll() throws InterruptedException {
        Orientation angles = imu.getAngularOrientation();
        return (angles.thirdAngle * -1);
    }

    public double getGyroYaw() throws InterruptedException {
        Orientation angles = imu.getAngularOrientation();
        return (angles.firstAngle * -1);
    }

    public int getAvgEncoder() throws InterruptedException {
        return ( ( Math.abs(FL.getCurrentPosition()) + Math.abs(FR.getCurrentPosition()) ) /2);
    }


    public void turn(double rotation, double angle) throws InterruptedException {
        double first = getFunctionalGyroYaw();
        while ((Math.abs(getFunctionalGyroYaw() - first) < angle) && (opModeIsActive())) {
            turn(rotation);
            idle();
        }
        setZero();
    }


    public void resetTimer() throws InterruptedException{
        time.reset();
    }

    public double getTime() throws InterruptedException{
        return time.milliseconds();
    }

    //Normally getGyroYaw() returns a value from between -180 to 180
    //This leads to issues when you are near -180 or 180
    //getFunctionalGyroYaw() effectively increases the range
    //to -infinity to infinity as long as it is constantly being
    //updated with gyro data
    public double getFunctionalGyroYaw() throws InterruptedException{
        previousGyro = currentGyro;
        currentGyro = getGyroYaw();
        if (previousGyro <= -160 && currentGyro > 160){
            gyroMultiplier--;
        }
        if (currentGyro <= -160 && previousGyro > 160){
            gyroMultiplier++;
        }
        return gyroMultiplier * 360 + getGyroYaw();
    }

    //TODO: Create basic methods to set all motors to a certain power + stop the motors

    public void setPower(double power) throws InterruptedException{
        FL.setPower(-power * .87);
        FR.setPower(power);
        BL.setPower(-power * .87);
        BR.setPower(power);
    }

    public void setPowerAndTurn(double power, double turn) throws InterruptedException{
        FL.setPower(-power + turn);
        FR.setPower(power + turn);
        BL.setPower(-power + turn);
        BR.setPower(power + turn);
    }

    public double findAnglularPositionError(double desiredAngle) throws InterruptedException{
        return getFunctionalGyroYaw() - desiredAngle;
    }

    public double simpleStraighten(double desiredAngle) throws InterruptedException{
        double error = findAnglularPositionError(desiredAngle);
        return error * 0.05;
    }

    public void moveForwardStraight(double power, double desiredAngle, int timeMS) throws InterruptedException{
        setPower(power);
        double startTime = time.milliseconds();
        while (time.milliseconds() - startTime < timeMS){
            double correctionalTurn = simpleStraighten(desiredAngle);
            setPowerAndTurn(power, correctionalTurn);
            telemetry.addData("ANgle", getFunctionalGyroYaw());
            telemetry.update();
        }
        setZero();
    }


    public void setZero() throws InterruptedException{
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    public void turn(double power) throws InterruptedException{
        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);
    }


    //TODO: Create a basic time-based movement method

    public void moveTime(int time, double power) throws InterruptedException {

        setPower(power);
        sleep(time);
        setZero();
    }


    //TODO: Create a basic encoder-based movement method

    // Move forward based off encoders
    public void moveForwardEncoder(double power, int distance) throws InterruptedException {
        int startPos = getAvgEncoder();
        while ((Math.abs(getAvgEncoder() - startPos) < distance) && (opModeIsActive())) {
            setPower(power);
            telemetry.addData("distance", getAvgEncoder() - startPos);
            telemetry.update();
            idle();
        }
        setZero();
        if (Math.abs(getAvgEncoder() - startPos) > distance + 50) {
            telemetry.addData("overshoot", "fix");
            telemetry.update();
        }
    }

    public void pMoveForward(int distance) throws InterruptedException {
        int startPos = getAvgEncoder();
        while ((Math.abs(getAvgEncoder() - startPos) < distance) && (opModeIsActive())) {
            int distanceAway = Math.abs(distance - Math.abs(getAvgEncoder() - startPos));
            setPower(distanceAway * .0005 + .05);
            telemetry.addData("distance", Math.abs(distanceAway - Math.abs(getAvgEncoder() - startPos)));
            telemetry.addData("Power", distanceAway * .0005 + .005);
            telemetry.update();
            idle();
        }
        setZero();
        if (Math.abs(getAvgEncoder() - startPos) > distance + 50) {
            telemetry.addData("overshoot", "fix");
            telemetry.update();
        }
    }


    // Move backwards based off encoders
    public void moveBackwardEncoder(double power, int distance) throws InterruptedException {
        int startPos = getAvgEncoder();
        while ((Math.abs(getAvgEncoder() - startPos) < distance) && (opModeIsActive())) {
            setPower(-power);
            telemetry.addData("distance", getAvgEncoder() - startPos);
            telemetry.update();
            idle();
        }
        setZero();
        if (Math.abs(getAvgEncoder() - startPos) > distance + 50) {
            telemetry.addData("overshoot", "fix");
            telemetry.update();
        }
    }

    //TODO: Create a Proportion-based encoder movement method

    //TODO: Create a PI or PID-based movement method

    //TODO: Create a basic time-based turning method

    //TODO: Create a Proportion-based turning method

    public void pRightTurn(double desired) throws InterruptedException {
        double start = getFunctionalGyroYaw();
        double proximity = Math.abs(desired);
        while (Math.abs(getFunctionalGyroYaw() - start) < desired) {
            proximity = (Math.abs(getFunctionalGyroYaw() - start - desired));
            telemetry.addData("Proximity Value: ", proximity);
            telemetry.addData("Turn value: ", -proximity * .0025 - .12);
            telemetry.addData("Yaw Value:", getFunctionalGyroYaw());
            telemetry.update();
            turn(-proximity * .0025 - .12);
        }
        setZero();
    }

    public void pLeftTurn(double desired) throws InterruptedException {
        double start = getFunctionalGyroYaw();
        double proximity = Math.abs(desired);
        while (Math.abs(getFunctionalGyroYaw() - start) < desired) {
            proximity = Math.abs((Math.abs(getFunctionalGyroYaw() - start) - desired));
            telemetry.addData("Proximity Value: ", proximity);
            telemetry.addData("Turn value: ", proximity * .0025 + .12);
            telemetry.addData("Yaw Value:", getFunctionalGyroYaw());
            telemetry.update();
            turn(proximity * .0025 + .12);
        }
        setZero();
    }



    //TODO: Create a PI or PID-based turning method

    //TODO: Create basic methods to manipulate the addition servos and motors

    public void setTeamMarker() {
        TeamMarker.setPosition(TEAM_MARKER_UP_POSITION);
        telemetry.addData("TMarker Pos: ", TeamMarker.getPosition());
        telemetry.update();
    }
    public void dropTeamMarker() {
        TeamMarker.setPosition(TEAM_MARKER_DOWN_POSITION);
        telemetry.addData("TMarker Pos: ", TeamMarker.getPosition());
        telemetry.update();
    }

    //TODO: Create an approach to detecting the gold. Some possibilities include Color Sensor, OpenCV, BitMaps

    //TODO: Create basic code for range sensors

    public double getRangeReading() throws InterruptedException{
        double reading = rangeSensor.getDistance(DistanceUnit.CM);
        while (reading > 1000 || Double.isNaN(reading) && opModeIsActive()){
            reading = rangeSensor.getDistance(DistanceUnit.CM);
        }
        return reading;
    }

    //TODO: Create range sensor based movement code

    public void moveToRange(double rangeCM) throws InterruptedException {
        while (Math.abs(getRangeReading() - rangeCM) > 1.5){
            double error = getRangeReading() - rangeCM;
            if (error > 0){
                setPower(0.1 + Math.abs(error) * 0.23/50);
            }
            else if (error < 0){
                setPower(-0.2 - Math.abs(error) * 0.23/50);
            }
            telemetry.addData("Range", getRangeReading());
            telemetry.update();
        }
        setPower(0);
    }

    public void moveToRangeStraighten(double rangeCM, double angle) throws InterruptedException {
        while (Math.abs(getRangeReading() - rangeCM) > 1.5){
            double error = getRangeReading() - rangeCM;
            double angularCorrection = simpleStraighten(angle);
            if (error > 0){
                setPowerAndTurn(0.1 + Math.abs(error) * 0.23/50, angularCorrection);
            }
            else if (error < 0){
                setPowerAndTurn(-0.2 - Math.abs(error) * 0.23/50, angularCorrection);
            }
            telemetry.addData("Range", getRangeReading());
            telemetry.update();
        }
        setPower(0);
    }

    //TODO: Create a potential gyro-based movement code to finish in the crater



}
