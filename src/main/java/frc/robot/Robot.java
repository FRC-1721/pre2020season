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

  // Commands
  public static ArcadeDrive arcadeDrive = new ArcadeDrive();
  public static ROS_FullAuto ros_FullAuto = new ROS_FullAuto();
  public static ResetDrivetrainEncoders resetDrivetrainEncoders = new ResetDrivetrainEncoders();

  // OI
  public static OI m_oi;


  Command robot_autonomous; // Autonomous
  SendableChooser<Command> autoChooser = new SendableChooser<>(); // Create a new chooser for holding what auto we want to use

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

    // Define IO
    RobotMap.lapis_boot = new DigitalOutput(RobotMap.lapis_dio_port);

    // Define OI
    m_oi = new OI();  

    // Define Joysticks
    RobotMap.driverStick = new Joystick(RobotMap.driverStick_Port); // Define the joystick and attach its port to the joystick object in RobotMap

    // Define motors
    RobotMap.starboardMotor = new TalonSRX(RobotMap.starboardAddress); // Define starboard motor and attach its address to the TalonSRX object in RobotMap
    RobotMap.portMotor = new TalonSRX(RobotMap.portAddress); // Define port motor

    // Boot the coprossesor
    RobotMap.lapis_boot.set(true); // Turn the power on
    Timer.delay(0.5);
    RobotMap.lapis_boot.set(false); // Turn the power off
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    Robot.telemetry.update();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    arcadeDrive.cancel(); // Stop the arcade drive command
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run(); // Honestly i have no idea what this line does
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    robot_autonomous = autoChooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

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

  @Override
  public void teleopInit() {
    if (robot_autonomous != null) { // Stops autonomous
      robot_autonomous.cancel();
    }
    
    arcadeDrive.start(); // Start the arcade drive command
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    // Drivetrain
    SmartDashboard.putNumber("Port", RobotMap.portMotor.getSelectedSensorPosition()); // Put the encpoder values on the board
    SmartDashboard.putNumber("Starboard", RobotMap.starboardMotor.getSelectedSensorPosition());
  }
}
