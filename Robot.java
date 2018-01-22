package org.usfirst.frc.team6679.robot;


import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	// Initialize steering wheel & turning axis and power
	private Joystick steeringWheel = new Joystick(0);
	private double steeringWheelTurn = 0;
	private double steeringWheelPower = 0;

	// Initialize Left & Right Drive Train Powers
	private double leftPower = 0;
	private double rightPower = 0;

	// Initialize Motors (The Number In () Is The Port # Of What Is Connected Where)
	private VictorSP frontLeftDriveMotor1 = new VictorSP(4);
	private CANTalon frontLeftDriveMotor2 = new CANTalon(2);
	private CANTalon frontRightDriveMotor1 = new CANTalon(1);
	private CANTalon frontRightDriveMotor2 = new CANTalon(3);
	private VictorSP backLeftDriveMotor1 = new VictorSP(1);
	private VictorSP backLeftDriveMotor2 = new VictorSP(0);
	private VictorSP backRightDriveMotor1 = new VictorSP(3);
	private VictorSP backRightDriveMotor2 = new VictorSP(2);
		
	// Initialize Variables To Scale the Drivetrain's power
	private double drivetrainPower = 1;
	
	// Initialize a boolean to toggle between on and off
	private boolean drivingEnabled = false;
	
	// Initialize An Array To Store The Status Of The Joystick Buttons
	private boolean[] stick1BtnPressed = new boolean[10];

	// Code Run When The Robot Is Turned On
	public void robotInit()
	{
		// Adds Useful Values / Information To The Driver Station Window
		SmartDashboard.putNumber("Drivetrain Power (%)", drivetrainPower * 100);
	}

	// Code Grabs Inputted Values From The Driver Station Dashboard Window
	public void disabledPeriodic()
	{
		getDisplayValues();
	}

	// Code Run Non Stop During The Teleop Mode
	public void teleopPeriodic() 
	{
		// Gets The Values From The Driver Station Dashboard
		getDisplayValues();

		// Calls The Method To Get & Store The Input From The Joystick
		getInput();

		// Calls The Method To Update The Driver Station Window's Values
		updateDisplay();
		
		// R2 Button (Allows the motors to run)
		if (steeringWheel.getRawButton(7) && stick1BtnPressed[7] == false) 
		{
			stick1BtnPressed[7] = true;
			drivingEnabled = true;
		}
		else if (steeringWheel.getRawButton(7) && stick1BtnPressed[7] == true) 
		{
			stick1BtnPressed[7] = false;
		}
		
		// R1 Button (Stops the motors from running)
		if (steeringWheel.getRawButton(8) && stick1BtnPressed[8] == false) 
		{
			stick1BtnPressed[8] = true;
			drivingEnabled = false;
		}
		else if (steeringWheel.getRawButton(8) && stick1BtnPressed[8] == true) 
		{
			stick1BtnPressed[8] = false;
		}
		
		// Sets the motors to off if the driving toggle has been disabled
		if (drivingEnabled == false)
		{
			leftPower = 0;
			rightPower = 0;
		}

		// Passes On The Input Grabbed From The Above Method To Move The Robot Around
		drive();
	}

	// Method To Get Called To Get The Input From The User
	public void getInput() 
	{
		// Gets Input From The Steering Wheel
		steeringWheelTurn = steeringWheel.getX();
		steeringWheelPower = steeringWheel.getY();

		// Calculates The Final Power Values To Send To The Motors
		leftPower = ((steeringWheelTurn * 2) - steeringWheelPower);
		rightPower = ((steeringWheelTurn * 2) + steeringWheelPower);
		//leftPower = -steeringWheelPower;
		//rightPower = steeringWheelPower;
		
	}

	// Method To Update The Visually Presented Data In The GUI Driver Station Window
	public void updateDisplay()
	{
		SmartDashboard.putNumber("Drivetrain Power (%)", drivetrainPower * 100);
	}

	// Method To Get The Value From The Dash Board Window
	public void getDisplayValues()
	{
		drivetrainPower = SmartDashboard.getNumber("Drivetrain Power (%)", drivetrainPower) / 100;
	}

	// Method To Set The Motors Speeds To What Has Been Stored From The getInput Method
	public void drive() 
	{
		frontLeftDriveMotor1.set(leftPower);
		frontLeftDriveMotor2.set(leftPower);
		frontRightDriveMotor1.set(rightPower);
		frontRightDriveMotor2.set(rightPower);
		backLeftDriveMotor1.set(leftPower);
		backLeftDriveMotor2.set(leftPower);
		backRightDriveMotor1.set(rightPower);
		backRightDriveMotor2.set(rightPower);
	}

}