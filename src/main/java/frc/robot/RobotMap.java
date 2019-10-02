/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  public static int starboardAddress = 2; // Last Configured: Never
  public static int portAddress = 1;  // Last Configured: Never

  // TalonSRX Objects
  public static TalonSRX starboardMotor; // These objects represent the motors themselves
  public static TalonSRX portMotor;

  // Driver Stick Information
  public static Joystick driverStick; // This object represents the joystick itself
  public static int driverStick_Port = 0; // The driver station item slot this controler resides in
  public static int driverStick_throaxis = 1; // The axis for thro
  public static int driverStick_yawaxis = 2; // The axis for yaw
}
