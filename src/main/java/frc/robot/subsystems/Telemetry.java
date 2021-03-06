/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ResetDrivetrainEncoders;
import frc.robot.commands.sing;

/**
 * Telemetry is used for delivering data to the drivestation
 */
public class Telemetry extends Subsystem {
  private static Notifier telemetry_notifier;

  public void init() {
    // Notifier
    telemetry_notifier = new Notifier(Telemetry::update); // Create a notifier that will update the update command in package Telemetry
    telemetry_notifier.startPeriodic(0.2); // Start a notifier witch will run telemetry every 0.02 seconds

    // Drivetrain
    SmartDashboard.putData("Reset Encoders", new ResetDrivetrainEncoders());

    //Other
    SmartDashboard.putData("Sing", new sing());
  }

  public static void update() {
    // Drivetrain
    SmartDashboard.putNumber("Port Encoder Count", Robot.drivetrain.getDriveEncoderPort()); // Put the encpoder values on the board
    SmartDashboard.putNumber("Starboard Encoder Count", Robot.drivetrain.getDriveEncoderStarboard());
    SmartDashboard.putNumber("Speed", Robot.drivetrain.getOverallSpeed());
  }

  /**
   * Put things to be displayed during debug here
   */
  public static void debug() {
    // Debug
    SmartDashboard.putBoolean("Is Overide", Drivetrain.operatorIsOveride(RobotMap.driverStick));
    SmartDashboard.putNumber("Driver Thro", (RobotMap.driverStick.getRawAxis(RobotMap.driverStick_throaxis) * -1 ));
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
