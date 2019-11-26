/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ResetDrivetrainEncoders;

/**
 * Telemetry is used for delivering data to the drivestation
 */
public class Telemetry extends Subsystem {

  public Telemetry() {
    // Drivetrain
    SmartDashboard.putData("Reset Encoders", new ResetDrivetrainEncoders());
  }

  public void update() {
    // Drivetrain
    SmartDashboard.putNumber("Port Encoder Count", Robot.drivetrain.getDriveEncoderPort()); // Put the encpoder values on the board
    SmartDashboard.putNumber("Starboard Encoder Count", Robot.drivetrain.getDriveEncoderStarboard());
    SmartDashboard.putNumber("Speed", Robot.drivetrain.getOverallSpeed());

    // ROS in
    SmartDashboard.putNumber("ROS Current X Pos",(RobotMap.rosTable.getEntry("robotX")).getDouble(-1)); // The nesting here is a little funny but we're getting an entry in the table and then from that table we are getting a double.
    SmartDashboard.putNumber("ROS Current Y Pos",(RobotMap.rosTable.getEntry("robotY")).getDouble(-1));

    // ROS out
    RobotMap.starboardEncoderEntry.setDouble(Robot.drivetrain.getDriveEncoderStarboard());
    RobotMap.portEncoderEntry.setDouble(Robot.drivetrain.getDriveEncoderPort());

    // Legacy ROS
    SmartDashboard.putNumber("Port", Robot.drivetrain.getDriveEncoderPort());
    SmartDashboard.putNumber("Starboard", Robot.drivetrain.getDriveEncoderStarboard());

    //System.out.println("Ran Telemetry.update"); // This is only for debugging!
  }

/**
 * Call this function to publish an alert to the drivestation
 */
  public static void alert(String message) {
    SmartDashboard.putString("alert", message); // Publish whatever string is passed to key "alert"
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
