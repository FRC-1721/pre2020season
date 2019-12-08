/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ROS_FullAuto;
import frc.robot.commands.ResetDrivetrainEncoders;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ROS;
import frc.robot.subsystems.Telemetry;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Subsystems
  public static Drivetrain drivetrain = new Drivetrain();
  public static Telemetry telemetry = new Telemetry();
  public static ROS ros = new ROS();

  // Commands
  public static ArcadeDrive arcadeDrive = new ArcadeDrive();
  public static ROS_FullAuto ros_FullAuto = new ROS_FullAuto();
  public static ResetDrivetrainEncoders resetDrivetrainEncoders = new ResetDrivetrainEncoders();

  // OI
  public static OI m_oi;

  Command robot_autonomous; // Autonomous object, will be populated later by the contents of the sendable chooser
  SendableChooser<Command> autoChooser = new SendableChooser<>(); // Create a new chooser for holding what autonomous we want to use

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // Define SmartDashboard widgets
    autoChooser.setDefaultOption("ROS Full Auto", new ROS_FullAuto());
    autoChooser.addOption("Do nothing", null); // Send null
    SmartDashboard.putData("Auto mode", autoChooser);

    // Setup networkTables
    RobotMap.networkTableInst = NetworkTableInstance.getDefault(); // Get the default instance of network tables on the rio
    RobotMap.rosTable = RobotMap.networkTableInst.getTable(RobotMap.rosTablename); // Get the table ros
    RobotMap.starboardEncoderEntry = RobotMap.rosTable.getEntry(RobotMap.starboardEncoderName); // Get the writable entries
    RobotMap.portEncoderEntry = RobotMap.rosTable.getEntry(RobotMap.portEncoderName);
    RobotMap.rosIndex = RobotMap.rosTable.getEntry(RobotMap.rosIndexName);

    // Define IO
    RobotMap.lapis_boot = new DigitalOutput(RobotMap.lapis_dio_port);

    // Define OI
    m_oi = new OI();  

    // Define Joysticks
    RobotMap.driverStick = new Joystick(RobotMap.driverStick_Port); // Define the joystick and attach its port to the joystick object in RobotMap

    // Define motors
    RobotMap.starboardMotor = new TalonSRX(RobotMap.starboardAddress); // Define starboard motor and attach its address to the TalonSRX object in RobotMap
    RobotMap.portMotor = new TalonSRX(RobotMap.portAddress); // Define port motor
    RobotMap.spinningRSLSRX = new TalonSRX(RobotMap.spinningRSLAddress); // Define spinning RSL light

    // Boot the coprossesor
    RobotMap.lapis_boot.set(true); // Turn the power on
    Timer.delay(0.5);
    RobotMap.lapis_boot.set(false); // Turn the power off
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   */
  @Override
  public void robotPeriodic() {
    Telemetry.update(); // Update all the telemetry
    ROS.update(); // Update all the ROS
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    // Send alert
    Telemetry.alert("Robot disabled");

    // Cancel running commands
    arcadeDrive.cancel(); // Stop the arcade drive command
    //robot_autonomous.cancel(); // Stops the current running autonomous if it was running

    // Saftey
    ROS.spin_RSL(0); // stop the saftey light
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run(); // Honestly i have no idea what this line does
  }

  /**
   * Runs only once when autonomous starts
   * Retreives the current selected auto only
   * when autonomousInit starts
   */
  @Override
  public void autonomousInit() {
    robot_autonomous = autoChooser.getSelected();

    // schedule the autonomous command
    if (robot_autonomous != null) {
      robot_autonomous.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * Runs once when teleop starts
   */
  @Override
  public void teleopInit() {
    //if (robot_autonomous != null) { // Stops autonomous
    //  robot_autonomous.cancel();
    //}
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    if (Drivetrain.operatorIsOveride(RobotMap.driverStick)){ // If the operator takes control of the stick
      arcadeDrive.start(); // Start the arcade drive command
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Telemetry.debug();

    // Saftey
    ROS.spin_RSL(1); // Spin the saftey light
  }
}
