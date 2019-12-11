/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.FBWA;
import frc.robot.commands.FBWB;
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
  public static final Drivetrain drivetrain = new Drivetrain();
  public static final Telemetry telemetry = new Telemetry();
  public static final ROS ros = new ROS();

  // Commands
  public static FBWA FBWA = new FBWA();
  public static FBWB FBWB = new FBWB();
  public static ROS_FullAuto ros_FullAuto = new ROS_FullAuto();
  public static ResetDrivetrainEncoders resetDrivetrainEncoders = new ResetDrivetrainEncoders();

  // OI
  public static OI m_oi;

  // Selectors
  Command robot_autonomous; // Autonomous object, will be populated later by the contents of the sendable chooser
  SendableChooser<Command> autoChooser = new SendableChooser<>(); // Create a new chooser for holding what autonomous we want to use

  Command handling_mode; // Command object, will be populated by handling chooser
  SendableChooser<Command> handlingChooser = new SendableChooser<>(); // Create a new chooser for holding the handling mode

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // Subsystems
    drivetrain.init(); // Init Drivetrain
    ros.init(); // Init ROS
    telemetry.init(); // Init telemetry

    // Define SmartDashboard widgets
    autoChooser.setDefaultOption("ROS Full Auto", new ROS_FullAuto());
    autoChooser.addOption("Do nothing", null); // Send null
    SmartDashboard.putData("Auto mode", autoChooser);

    handlingChooser.setDefaultOption("FBWA", new FBWA());
    handlingChooser.addOption("FBWB (FWW)", new FBWB());
    SmartDashboard.putData("Handling Mode", handlingChooser);

    // Define OI
    m_oi = new OI();  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   */
  @Override
  public void robotPeriodic() {
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
    FBWA.cancel(); // Stop the arcade drive command
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
    handling_mode = handlingChooser.getSelected();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    if (Drivetrain.operatorIsOveride(RobotMap.driverStick)){ // If the operator takes control of the stick
      handling_mode.start(); // Start the arcade drive command
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
