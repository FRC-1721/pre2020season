/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Not a lot happens in this subsystem beacuse most of the code
 * is on the coprossesor, but this is a good place to put items
 * directly controled by ROS
 */
public class ROS extends Subsystem {
  // Initilize counters
  private static int rosIntex = 1;

  // Initilize dio ports
  private static DigitalOutput lapis_boot;

  // TalonSRX Objects
  private static TalonSRX spinningRSLSRX;

  // Setup networkTables
  private static NetworkTableEntry starboardEncoderEntry; // An entry objecy
  private static NetworkTableEntry portEncoderEntry;
  private static NetworkTableEntry rosIndex;

  // Initialize noifiers
  private static Notifier ros_notifier;

  /**
   * Setup things related to ROS
   */
  public void init() {
    // Saftey
    spinningRSLSRX = new TalonSRX(RobotMap.spinningRSLAddress); // Define spinning RSL light

    // Jetson Boot
    lapis_boot = new DigitalOutput(RobotMap.lapis_dio_port);
    lapis_boot.set(true); // Turn the power on
    Timer.delay(0.5);
    lapis_boot.set(false); // Turn the power off

    // Network tables
    RobotMap.networkTableInst = NetworkTableInstance.getDefault(); // Get the default instance of network tables on the rio
    RobotMap.rosTable = RobotMap.networkTableInst.getTable(Constants.rosTablename); // Get the table ros
    starboardEncoderEntry = RobotMap.rosTable.getEntry(Constants.starboardEncoderName); // Get the writable entries
    portEncoderEntry = RobotMap.rosTable.getEntry(Constants.portEncoderName);
    rosIndex = RobotMap.rosTable.getEntry(Constants.rosIndexName);

    // Notifier
    ros_notifier = new Notifier(ROS::update);
    ros_notifier.startPeriodic(0.02); // Start a notifier witch will run the ros update
  }

  /**
   * Set to 1 to spin the advnced RSL
   * 0 to turn it off.
   */
  public static void spin_RSL(double spin_speed){
    spinningRSLSRX.set(ControlMode.PercentOutput, spin_speed); // Set to the desired speed
  }

  /**
   * Updates and syncs the ROS data
   * to the coprossor
   */
  public static void update(){
    // ROS in
    SmartDashboard.putNumber("ROS Current X Pos",(RobotMap.rosTable.getEntry("robotX")).getDouble(-1)); // The nesting here is a little funny but we're getting an entry in the table and then from that table we are getting a double.
    SmartDashboard.putNumber("ROS Current Y Pos",(RobotMap.rosTable.getEntry("robotY")).getDouble(-1));

    // ROS out
    starboardEncoderEntry.setDouble(Robot.drivetrain.getDriveEncoderStarboard());
    portEncoderEntry.setDouble(Robot.drivetrain.getDriveEncoderPort());
    rosIndex.setNumber(rosIntex);

    // Legacy ROS
    SmartDashboard.putNumber("Port", Robot.drivetrain.getDriveEncoderPort());
    SmartDashboard.putNumber("Starboard", Robot.drivetrain.getDriveEncoderStarboard());

    // Increase the Index value
    rosIntex = rosIntex + 1;
    if (rosIntex > 255) {
      rosIntex = 1;
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
