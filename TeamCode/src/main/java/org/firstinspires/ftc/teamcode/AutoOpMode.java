
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//import org.opencv.android.CameraBridgeViewBase;

public abstract class AutoOpMode extends LinearOpMode {

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
    //Servo Sampler;

    //Sensors

    BNO055IMU imu;

    //Other Variables

    ElapsedTime time; //returns time in s, ms, or ns since last reset

    double currentGyro;
    double previousGyro;
    int gyroMultiplier;


    //Constants

    //Initializes Motors, Servos, Sensors, etc when the robot is hanging
    public void initialize() throws InterruptedException {
        time = new ElapsedTime();
        //Drive Train Motors

        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        TeamMarker = hardwareMap.servo.get("TeamMarker");
        //Sampler = hardwareMap.servo.get("Sampler");

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

        resetTimer();
        previousGyro = 0;
        currentGyro = 0;
        gyroMultiplier = 0;
    }

    //Initializes Gyro, since in the actual game the gyro may need to be initialized after
    //everything else (once the robot lands on the ground)
    public void initializeGyro() throws InterruptedException {

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

    public void resetTimer() throws InterruptedException {
        time.reset();
    }

    public double getTime() throws InterruptedException {
        return time.milliseconds();
    }

    //Normally getGyroYaw() returns a value from between -180 to 180
    //This leads to issues when you are near -180 or 180
    //getFunctionalGyroYaw() effectively increases the range
    //to -infinity to infinity as long as it is constantly being
    //updated with gyro data
    public double getFunctionalGyroYaw() throws InterruptedException {
        previousGyro = currentGyro;
        currentGyro = getGyroYaw();
        if (previousGyro <= -160 && currentGyro > 160) {
            gyroMultiplier--;
        }
        if (currentGyro <= -160 && previousGyro > 160) {
            gyroMultiplier++;
        }
        return gyroMultiplier * 360 + getGyroYaw();
    }

    //TODO: Create basic methods to set all motors to a certain power + stop the motors

    public void setPower(double power) throws InterruptedException {
        FL.setPower(-power);
        FR.setPower(power);
        BL.setPower(-power);
        BR.setPower(power);
    }

    public void setZero() throws InterruptedException {
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    public void turn(double power) throws InterruptedException {
        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);
    }

    //TODO: Create a basic time-based movement method

    //TODO: Create a basic encoder-based movement method

    //TODO: Create a Proportion-based encoder movement method

    public void pturn(double desired) throws InterruptedException {
        double proximity;
        if (desired > 0) {
            while (Math.abs(getGyroYaw()) > desired) {
                proximity = (Math.abs(getFunctionalGyroYaw()) - desired);
                turn(proximity * .05 + .1);
            }
        } else {
            while (Math.abs(getFunctionalGyroYaw()) < desired) {
                proximity = (Math.abs(getFunctionalGyroYaw()) - desired);
                turn(-proximity * .05 - .1);
            }
        }
    }


    //TODO: Create a PI or PID-based movement method

    //TODO: Create a basic time-based turning method

    //TODO: Create a Proportion-based turning method

    public void turn(double degreeTurned, double power) throws InterruptedException {
        double startAngle = getFunctionalGyroYaw();
        turn(power);
        while (Math.abs(getFunctionalGyroYaw() - startAngle) < degreeTurned) {
            telemetry.addData("Angle", getFunctionalGyroYaw());
            telemetry.update();
        }
        setPower(0);
    }

    //TODO: Create a PI or PID-based turning method

    //TODO: Create basic methods to manipulate the addition servos and motors

    public void resetTeamMarker() {
        TeamMarker.setPosition(.7);
    }

    public void flickTeamMarker() {
        TeamMarker.setPosition(.2);
    }

    //public void resetSampler(){
    //    Sampler.setPosition(.2);
    //}

    //public void hitGold(){
    //    Sampler.setPosition(.7);
    //}

    //TODO: Create an approach to detecting the gold. Some possibilities include Color Sensor, OpenCV, BitMaps

    //TODO: Create basic code for range sensors

    //TODO: Create range sensor based movement code

    //TODO: Create a potential gyro-based movement code to finish in the crater


}
