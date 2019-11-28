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
import edu.wpi.first.wpilibj.DigitalOutput;
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

  // ROS
  public static int ROSTimeout = 8;

  // Jetson Controls
  public static DigitalOutput lapis_boot;
  public static int lapis_dio_port = 0;

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

  // Funny
  public static String startupSound = "[:phoneme on][dah<500,26>dah<180,14>dah<180,21>dah<500,19>dah<180,26>dah<500,21>]";
  public static String daisy = "[:phoneme on][dey<600,24>ziy<600,21>dey<600,17>ziy<600,12>gih<200,14>vmiy<200,16>yurr<200,17>ah<400,14>nsrr<200,17>duw<1200,12>][ay<600,19>mhxah<600,24>fkrey<600,21>ziy<600,17>ah<400,14>llfow<100,16>rthah<100,17>llah<400,19>vah<200,21>vyu<1200,19>][ih<200,21>twow<200,22>ntbiy<200,21>ah<200,19>stay<400,24>llih<200,21>shmae<200,19>rih<600,17>jh][ay<200,19>keh<400,21>ntah<200,17>fow<400,14>rdah<200,17>keh<200,14>rih<800,12>jh][buh<200,12>tyu<400,17>lluh<200,21>kswiy<400,19>tah<200,12>pah<400,17>nthah<200,21>siy<200,19>t][ah<100,21>vah<100,22>bay<200,24>sih<200,21>kuh<200,17>llbih<400,19>lltfow<200,12>rtuw<1200,17>]";
}
