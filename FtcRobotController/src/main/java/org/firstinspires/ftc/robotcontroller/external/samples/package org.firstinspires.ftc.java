package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Auton_code(name = "Mecanumcode (Blocks to Java)")
public class Mecanumcode.java extends LinearOpMode {

private DcMotor backrightdrive;
private DcMotor frontrightdrive;
private DcMotor frontleftdrive;
private DcMotor backleftdrive;

/**

This function is executed when this OpMode is selected from the Driver Station.
*/
@Override
public void runOpMode() {
float vertical;
float horizontal;
float pivot;
backrightdrive = hardwareMap.get(DcMotor.class, "backrightdrive");
frontrightdrive = hardwareMap.get(DcMotor.class, "frontrightdrive");
frontleftdrive = hardwareMap.get(DcMotor.class, "frontleftdrive");
backleftdrive = hardwareMap.get(DcMotor.class, "backleftdrive");

// Put initialization blocks here.
backrightdrive.setDirection(DcMotor.Direction.REVERSE);
frontrightdrive.setDirection(DcMotor.Direction.REVERSE);
waitForStart();
if (opModeIsActive()) {
  vertical = -gamepad1.right_stick_y;
  horizontal = gamepad1.right_stick_x;
  pivot = gamepad1.left_stick_x;
  while (opModeIsActive()) {
    frontrightdrive.setPower(-pivot + (vertical - horizontal));
    backrightdrive.setPower(-pivot + vertical + horizontal);
    frontleftdrive.setPower(pivot + vertical + horizontal);
    backleftdrive.setPower(pivot + (vertical - horizontal));
    // Put loop blocks here.
    telemetry.update();
  }
  // Put run blocks here.
}
}
}
//mecanum code?