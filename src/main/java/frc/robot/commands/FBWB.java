/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Telemetry;

public class FBWB extends Command {
  public FBWB() {
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Telemetry.alert("Entering manual mode B");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Drivetrain.flyByWireB(RobotMap.driverStick);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.flyWithWiresA(0, 0); // Leave the motors with 0, so we dont race off uncontrollably
    Telemetry.alert("Exiting manual mode B");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Telemetry.alert("FBWB Was interrupted");
  }
}
