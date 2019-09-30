/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    //SmartDashboard.putNumber("Port Encoder Count", Robot.drivetrain.getDriveEncoderCount());
    //SmartDashboard.putNumber("Starboard Encoder Count", Robot.drivetrain.getDriveEncoderDistance());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
