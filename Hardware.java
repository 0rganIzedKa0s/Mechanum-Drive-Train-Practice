package org.firstinspires.ftc.teamcode;
import java.lang.Math;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware {
    //Motor Creation
    public DcMotor backLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor frontLeftMotor = null;
    public DcMotor frontRightMotor = null;

    //Servo Creation At Some Point

    //Additional Variables
    HardwareMap hardwareMap = null;
    public ElapsedTime runtime = new ElapsedTime();
    public static float[] lCoords = {0,0};
    public static float[] rCoords = {0};
    public static float lMagnitude = 0;
    public static double lDirection;
    public static String thing = "";
    public static boolean Dleft = false;
    public static boolean Dright = false;
    public static boolean grabbing = false;

    public Hardware(HardwareMap hwMap) {
        initialize(hwMap);
    }

    private void initialize (HardwareMap hwMap) {
        hardwareMap = hwMap;
        //Add Actual Name To Device Name Whatever It's Called on the Phone
        frontRightMotor = hardwareMap.get(DcMotor.class, deviceName: "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, deviceName:"backRigthMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, devicename:"frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, deviceName:"backLeftMotor");
;
        //Connect Servos Here If We nEEd Them

        //Motor Direction
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set Motor Mode
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // O Power Behavior Setup
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Motor Power 0 During Init
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);

    }
}
    public void controller1 () {
        //left stick control
        lCoords[0] = -gamepad1.left_stick_x;
        lCoords[1] = -gamepad1.left_stick_y;
        lDirection = ((float) Math.toDegrees(Math.atan(lCoords[1] / lCoords[0])));
        if (lCoords[0] > 0) {
            if (lCoords[1] > 0) {
                lDirection += 180;
            } else {
                lDirection -= 180;
            }
        }
        lDirection += 360;
        lDirection = 360 - lDirection;
        if (lDirection < 0) {
            lDirection += 360;
        }
        lMagnitude = ((float)Math.sqrt((lCoords[0]*lCoords[0])+(lCoords[1]*lCoords[1])));
        lMagnitude = lMagnitude;
        double lBackPower = 0;
        double rBackPower = 0;
        double lFrontPower = 0;
        double rFrontPower = 0;
        thing = "";

        if (lCoords[0] > 0 || lCoords[0] < 0 || lCoords[1] > 0 || lCoords[1] < 0 ) {
            if (lDirection >= 22.5 && lDirection < 67.5) {
                //b
                //lFront and rBack go forward
                thing = "DiagonalForwardRight";
                lFrontPower = ((lMagnitude));
                rBackPower = ((lMagnitude));
            }

            if (lDirection >= 67.5 && lDirection < 112.5) {
                //a
                //all forwards
                thing = "Forwards";
                lFrontPower = ((lMagnitude));
                rBackPower = ((lMagnitude));
                lBackPower = ((lMagnitude));
                rFrontPower = ((lMagnitude));
            }

            if (lDirection >= 112.5 && lDirection   < 157.5) {
                //h
                //lBack and rFront forwards
                thing = "DiagonalForwardLeft";
                lBackPower = ((lMagnitude));
                rFrontPower = ((lMagnitude));
            }

            if (lDirection >= 157.5 && lDirection < 202.5) {
                //g
                //lFront and rBack backwards, lBack and rFront forwards
                thing = "Left";
                lFrontPower = (-(lMagnitude));
                rBackPower = (-(lMagnitude));
                lBackPower = ((lMagnitude));
                rFrontPower = ((lMagnitude));
            }

            if (lDirection >= 202.5 && lDirection < 247.5) {
                //f
                //lFront and rBack backwards
                thing = "DiagonalBackLeft";
                lFrontPower = (-(lMagnitude));
                rBackPower = (-(lMagnitude));
            }

            if (lDirection >= 247.5 && lDirection < 292.5) {
                //e
                //all motors fire backwards
                thing = "Backwards";
                lFrontPower = (-(lMagnitude));
                rBackPower = (-(lMagnitude));
                lBackPower = (-(lMagnitude));
                rFrontPower = (-(lMagnitude));
            }

            if (lDirection >= 292.5 && lDirection < 337.5) {
                //d
                //front left and back right backwards
                thing = "DiagonalBackRight";
                rFrontPower = (-(lMagnitude));
                lBackPower = (-(lMagnitude));
            }

            if ((lDirection >= 337.5 && lDirection < 0) || (lDirection < 22.5)) {
                //c
                //left front and right back forwards, others backwards
                thing = "Right";
                lFrontPower = ((lMagnitude));
                rBackPower = ((lMagnitude));
                lBackPower = (-(lMagnitude));
                rFrontPower = (-(lMagnitude));
            }
        }

        //right stick control
        rCoords[0] = -gamepad1.right_stick_x;
        if (rCoords[0] > 0 || rCoords[0] < 0) {
            lFrontPower += (-rCoords[0]);
            rBackPower += (rCoords[0]);
            lBackPower += (-rCoords[0]);
            rFrontPower += (rCoords[0]);
        }

        double lPower = Math.max(Math.abs(lFrontPower),Math.abs(lBackPower) );
        double rPower = Math.max(Math.abs(rFrontPower),Math.abs(rBackPower) );
        double AbsMaxValue = Math.max(lPower,rPower);

        if (AbsMaxValue > 1) {
            frontLeftMotor.setPower((lFrontPower/AbsMaxValue));
            backLeftMotor.setPower((lBackPower/AbsMaxValue));
            frontRightMotor.setPower((rFrontPower/AbsMaxValue));
            backRightMotor.setPower((rBackPower/AbsMaxValue));
        } else {
            frontLeftMotor.setPower((lFrontPower));
            backLeftMotor.setPower((lBackPower));
            frontRightMotor.setPower((rFrontPower));
            backRightMotor.setPower((rBackPower));
        }
    }