/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;

public class ROS_FullAuto extends Command {

  // ROS Variables
  //NetworkTableEntry robotX; // Current x, y and heading of the robot
  //NetworkTableEntry robotY;
  //NetworkTableEntry robotHeading;
  NetworkTableEntry coprocessorPort; // For tank drive
  NetworkTableEntry coprocessorStarboard;
  NetworkTableEntry rosTime; // Is ros time (slow estimate)

  public ROS_FullAuto() {
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Define ROS vars
    //robotX = RobotMap.rosTable.getEntry("robotX");
    //robotY = RobotMap.rosTable.getEntry("robotY");
    //robotHeading = RobotMap.rosTable.getEntry("robotHeading");
    coprocessorPort = RobotMap.rosTable.getEntry("coprocessorPort");
    coprocessorStarboard = RobotMap.rosTable.getEntry("coprocessorStarboard");
    rosTime = RobotMap.rosTable.getEntry("rosTime");
    SmartDashboard.putString("alert", "Starting ROS full auto");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Drivetrain.flyWithWiresA(RobotMap.starboardMotor, RobotMap.portMotor, coprocessorStarboard.getDouble(0), coprocessorPort.getDouble(0));
    SmartDashboard.putNumber("Current Ros Time", rosTime.getDouble(-1));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.flyWithWiresA(RobotMap.starboardMotor, RobotMap.portMotor, 0, 0); // Shut off motors
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
