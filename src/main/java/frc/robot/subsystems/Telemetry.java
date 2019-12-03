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
import frc.robot.commands.ResetDrivetrainEncoders;
import frc.robot.commands.sing;

/**
 * Telemetry is used for delivering data to the drivestation
 */
public class Telemetry extends Subsystem {

  public Telemetry() {
    // Drivetrain
    SmartDashboard.putData("Reset Encoders", new ResetDrivetrainEncoders());
    SmartDashboard.putData("Sing", new sing());
  }

  public static void update() {
    // Drivetrain
    SmartDashboard.putNumber("Port Encoder Count", Robot.drivetrain.getDriveEncoderPort()); // Put the encpoder values on the board
    SmartDashboard.putNumber("Starboard Encoder Count", Robot.drivetrain.getDriveEncoderStarboard());
    SmartDashboard.putNumber("Speed", Robot.drivetrain.getOverallSpeed());
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
