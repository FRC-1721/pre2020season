/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.RobotMap;
import frc.robot.subsystems.Telemetry;

/**
 * Add your docs here.
 */
public class sing extends InstantCommand {
  /**
   * Add your docs here.
   */
  public sing() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Telemetry.alert(RobotMap.daisy); // Pass daisy to alert
  }

}
