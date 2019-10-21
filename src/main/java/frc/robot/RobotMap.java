/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // NetworkTables
  public static NetworkTableInstance networkTableInst; // The instance of networktables (system wide)
  public static NetworkTable rosTable; // The table object
  public static NetworkTableEntry starboardEncoderEntry; // An entry objecy
  public static NetworkTableEntry portEncoderEntry;
  public static String rosTablename = "ROS"; // The string name of the table
  public static String starboardEncoderName = "Starboard";
  public static String portEncoderName = "Port";

  // CAN Addresses
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

  // Other
  public static int ticksPerMeter = 1000;
}
