
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5665.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;
public class Robot extends IterativeRobot {
	Spark leftDrive= new Spark(0);
	Spark rightDrive= new Spark(1);
    Spark vinc = new Spark(2);
	DifferentialDrive robotDrive = new DifferentialDrive(leftDrive, rightDrive);
	private Joystick stick = new Joystick(0);
	private Timer timer = new Timer();   
    Solenoid solenoid1 = new Solenoid(1);
    Solenoid solenoid2 = new Solenoid(2);
    Compressor c = new Compressor(0);
    DigitalInput rampaSwitch = new DigitalInput(0);
    int rampaKaydet = 0;
	public void      robotInit() {
	}
	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}
	public void autonomousPeriodic() {
		
		if (timer.get() < 3.5) {
			robotDrive.arcadeDrive(-0.75, 0.0); // drive forwards normal speed
			c.setClosedLoopControl(true);
		} else {
			robotDrive.arcadeDrive(0.0, 0.0); // stop robot
			c.setClosedLoopControl(true);
		}
	}
	public void teleopInit() {
	}
	public void teleopPeriodic() {		
		robotDrive.arcadeDrive(stick.getRawAxis(1)*1.00,stick.getRawAxis(0)*1.00);
		c.setClosedLoopControl(true);		
		vinc.set(1* stick.getRawAxis(5));
		System.out.println(leftDrive.get());
		System.out.println(rightDrive.get());

		if (stick.getRawAxis(2)>=0.75) {
			solenoid2.set(true);
		}
		else if (stick.getRawAxis(3)>=0.75) {
			solenoid1.set(true);
		}
		else {
			solenoid1.set(false);
			solenoid2.set(false);
		}		
		
		if (rampaSwitch.get()&&stick.getRawButton(4)) {
			rampaKaydet = 1 ;

		}
		else if (rampaSwitch.get()&&stick.getRawButton(1)) {
			rampaKaydet = 0;
		}		
		
		if (rampaKaydet==1) {
			if (stick.getRawButton(4)){
				vinc.set(-1);
			}
			else if (stick.getRawButton(1)){
				vinc.set(3);
			}
			else {
				vinc.set(0);
				}
		} 
		else if (rampaKaydet==0) {
			if (stick.getRawButton(4)){
				vinc.set(-1.5);
			}
			else if (stick.getRawButton(1)){
				vinc.set(2);
			}
			else {
				vinc.set(0);
				}
			}		
	}
	/**
	 * This function is called periodically during test mode.
	 */
	public void testPeriodic() {
	}
}
