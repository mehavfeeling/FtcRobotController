package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

/** this is master HW controller class. That acts as singleton for all motors, servos and other components */
@TeleOp (name="EurekaMaster")
public class EurekaMasterController extends LinearOpMode {
//public class EurekaMasterController {

    public static final String LEFT_FRONT_MOTOR = "left_front_motor_0";
    public static final String RIGHT_FRONT_MOTOR = "right_front_motor_1";
    public static final String LEFT_BACK_MOTOR = "left_back_motor_2";
    public static final String RIGHT_BACK_MOTOR = "right_back_motor_3";

    public static final String ARM_SHOULDER_MOTOR = "arm_shoulder_motor_0";
    public static final String ARM_ELBOW_MOTOR = "arm_elbow_motor_1";
    
    public static final String ARM_WRIST_SERVO = "arm_wrist_servo_0";
    public static final String LEFT_CLAW_SERVO = "left_claw_servo_1";
    public static final String RIGHT_CLAW_SERVO = "right_claw_servo_2";



    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor rightFrontMotor = null;
    private DcMotor rightBackMotor = null;

    // Declare arm motors and servos
    private DcMotor armShoulderMotor = null;
    private DcMotor armElbowMotor = null;
    
    private Servo leftClawServo = null;
    private Servo rightClawServo = null;
    private Servo armWristServo = null;

    @Override
    public void runOpMode() {

        

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontMotor  = hardwareMap.get(DcMotor.class, LEFT_FRONT_MOTOR);
        rightFrontMotor = hardwareMap.get(DcMotor.class, RIGHT_FRONT_MOTOR);
        leftBackMotor  = hardwareMap.get(DcMotor.class, LEFT_BACK_MOTOR);
        rightBackMotor = hardwareMap.get(DcMotor.class, RIGHT_BACK_MOTOR);

        armShoulderMotor  = hardwareMap.get(DcMotor.class, ARM_SHOULDER_MOTOR);
        armElbowMotor = hardwareMap.get(DcMotor.class, ARM_ELBOW_MOTOR);

        leftClawServo = hardwareMap.get(Servo.class, LEFT_CLAW_SERVO);
        rightClawServo = hardwareMap.get(Servo.class, RIGHT_CLAW_SERVO);
        armWristServo = hardwareMap.get(Servo.class, ARM_WRIST_SERVO);



        // ########################################################################################
        // !!!            IMPORTANT Motor Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to Motor forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }

            // Send calculated power to wheels
            leftFrontMotor.setPower(leftFrontPower);
            rightFrontMotor.setPower(rightFrontPower);
            leftBackMotor.setPower(leftBackPower);
            rightBackMotor.setPower(rightBackPower);
            
            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.
  
            double armExtendUp = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            double armExtendDown = gamepad1.a ? -1.0 : 0.0;  // A gamepad
            double armPivotUp = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            double armPivotDown = gamepad1.x ? -1.0 : 0.0;  // X gamepad
            double leftClawUp = gamepad1.left_trigger; //wheel claw up left
            double rightClawUp = gamepad1.left_trigger; //wheel claw up right
            double leftClawDown = -gamepad1.right_trigger; //wheel claw down left
            double rightClawDown = -gamepad1.right_trigger; //wheel claw down right
            double armWristUp = gamepad1.dpad_up ? 1.0 : 0.0; //wrist up
            double armWristDown = gamepad1.dpad_down ? -1.0 : 0.0; //wrist down
            
            armShoulderMotor.setPower(armPivotUp);
            armShoulderMotor.setPower(armPivotDown);
            
            armElbowMotor.setPower(armExtendUp);
            armElbowMotor.setPower(armExtendDown);
            
            armWristServo.setPosition(armWristUp);
            armWristServo.setPosition(armWristDown);
            
            leftClawServo.setPosition(leftClawUp);
            leftClawServo.setPosition(leftClawDown);
            
            rightClawServo.setPosition(rightClawUp);
            rightClawServo.setPosition(rightClawDown);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.update();
        }
    }

    public static void main(String[] s) {
        EurekaMasterController emc = new EurekaMasterController();
        emc.runOpMode();
    }

}
