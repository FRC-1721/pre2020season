/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Telemetry;

/**
 * Resets the drivetrain encoders back to 0
 */
public class ResetDrivetrainEncoders extends InstantCommand {
  /**
   * Resets the drivetrain encoders back to 0
   */
  public ResetDrivetrainEncoders() {
    super();
    requires(Robot.drivetrain);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Telemetry.alert("Resetting encoders");
    Robot.drivetrain.zeroEncoders();
  }

}
