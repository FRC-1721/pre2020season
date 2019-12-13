/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ROS;
import frc.robot.subsystems.Telemetry;

/**
 * Slaves all control to the jetson
 * @author Joe
 */
public class ROS_FullAuto extends Command {

  // ROS Variables
  //NetworkTableEntry robotX; // Current x, y and heading of the robot
  //NetworkTableEntry robotY;
  //NetworkTableEntry robotHeading;
  NetworkTableEntry coprocessorPort; // For tank drive
  NetworkTableEntry coprocessorStarboard;
  NetworkTableEntry rosTime; // Is ros time (slow estimate)
  double watchdog = 0;

  public ROS_FullAuto() {
    requires(Robot.drivetrain);
    requires(Robot.ros);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    RobotMap.CommandTimer = new Timer();
    // Define ROS vars
    //robotX = RobotMap.rosTable.getEntry("robotX");
    //robotY = RobotMap.rosTable.getEntry("robotY");
    //robotHeading = RobotMap.rosTable.getEntry("robotHeading");
    coprocessorPort = RobotMap.rosTable.getEntry("coprocessorPort");
    coprocessorStarboard = RobotMap.rosTable.getEntry("coprocessorStarboard");
    rosTime = RobotMap.rosTable.getEntry("rosTime");
    Telemetry.alert("lapis control started");
    ROS.spin_RSL(1); // Start RSL
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double starboard = coprocessorStarboard.getDouble(0);
    double port = coprocessorPort.getDouble(0);
    Drivetrain.flyWithWiresA(starboard, port);
    if(watchdog != (starboard + port)){ // Is the command stale?
      RobotMap.CommandTimer.reset(); // Reset the timer to 0
    }
    if(RobotMap.CommandTimer.get() > RobotMap.ROSTimeout){ // If we've been waiting for over 8 seconds
      coprocessorPort.setDouble(0); // Reset the values back to 0
      coprocessorStarboard.setDouble(0);
      SmartDashboard.putString("timeout", "yes");
    }
    watchdog = starboard + port; // This value will hold the previous command
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Telemetry.alert("lapis control was stopped");
    RobotMap.CommandTimer.stop(); // Stops the timer
    ROS.spin_RSL(0); // Stop RSL
    Drivetrain.flyWithWiresA(0, 0); // Shut off motors
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Telemetry.alert("lapis control was interrupted");
    end();
  }
}
