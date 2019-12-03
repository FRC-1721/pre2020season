/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Not a lot happens in this subsystem beacuse most of the code
 * is on the coprossesor, but this is a good place to put items
 * directly controled by ROS
 */
public class ROS extends Subsystem {
  
  /**
   * Set to 1 to spin the advnced RSL
   * 0 to turn it off.
   */
  public static void spin_RSL(double spin_speed){
    RobotMap.spinningRSLSRX.set(ControlMode.PercentOutput, spin_speed); // Set to the desired speed
  }

  /**
   * Updates and syncs the ROS data
   * to the coprossor
   */
  public static void update(){
    // ROS in
    SmartDashboard.putNumber("ROS Current X Pos",(RobotMap.rosTable.getEntry("robotX")).getDouble(-1)); // The nesting here is a little funny but we're getting an entry in the table and then from that table we are getting a double.
    SmartDashboard.putNumber("ROS Current Y Pos",(RobotMap.rosTable.getEntry("robotY")).getDouble(-1));

    // ROS out
    RobotMap.starboardEncoderEntry.setDouble(Robot.drivetrain.getDriveEncoderStarboard());
    RobotMap.portEncoderEntry.setDouble(Robot.drivetrain.getDriveEncoderPort());

    // Legacy ROS
    SmartDashboard.putNumber("Port", Robot.drivetrain.getDriveEncoderPort());
    SmartDashboard.putNumber("Starboard", Robot.drivetrain.getDriveEncoderStarboard());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
