package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

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

    //Sensors

    BNO055IMU imu;

    //Other Variables

    ElapsedTime time; //returns time in s, ms, or ns since last reset

    //Constants

    //Initializes Motors, Servos, Sensors, etc when the robot is hanging
    public void initialize() throws InterruptedException{

        //Drive Train Motors

        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

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

        time.reset();
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

    //TODO once the robot is up and running, test to see if these values (e.g. firstAngle) are accurate
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

    public void resetTimer() throws InterruptedException{
        time.reset();
    }

    public double getTime() throws InterruptedException{
        return time.milliseconds();
    }

    //TODO: Create basic methods to set all motors to a certain power + stop the motors

    //TODO: Create a basic time-based movement method

    //TODO: Create a basic encoder-based movement method

    //TODO: Create a Proportion-based encoder movement method

    //TODO: Create a PI or PID-based movement method

    //TODO: Create a basic time-based turning method

    //TODO: Create a Proportion-based turning method

    //TODO: Create a PI or PID-based turning method

    //TODO: Create basic methods to manipulate the addition servos and motors

    //TODO: Create an approach to detecting the gold. Some possibilities include Color Sensor, OpenCV, BitMaps

    //TODO: Create basic code for range sensors

    //TODO: Create range sensor based movement code

    //TODO: Create a potential gyro-based movement code to finish in the crater


}
