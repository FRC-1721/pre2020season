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

public class ArcadeDrive extends Command {
  public ArcadeDrive() {
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putString("alert", "Entering manual mode");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Pass robotmap variables to the drivetrain manual drive method
    Drivetrain.flyByWireA(RobotMap.starboardMotor, RobotMap.portMotor, RobotMap.driverStick);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.flyWithWiresA(RobotMap.starboardMotor, RobotMap.portMotor, 0, 0); // Leave the motors with 0, so we dont race off uncontrollably
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
