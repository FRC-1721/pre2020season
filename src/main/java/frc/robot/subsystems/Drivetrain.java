/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.RobotMap;

/**
 * This subsystem drives a tank style robot.
 */
public class Drivetrain extends Subsystem {
  private static TalonSRX starboardMotor; // These objects represent the motors themselves
  private static TalonSRX portMotor;

  /**
   * Put commands here that are specific to the drivetrain
   * @author Joe
   */
  public void init(){
    // Joysticks
    RobotMap.driverStick = new Joystick(RobotMap.driverStick_Port); // Define the joystick and attach its port to the joystick object in RobotMap
    
    // Motors
    starboardMotor = new TalonSRX(RobotMap.starboardAddress); // Define starboard motor and attach its address to the TalonSRX object in RobotMap
    portMotor = new TalonSRX(RobotMap.portAddress); // Define port motor

    // Direction
    portMotor.setInverted(true); // Set inverted (does not affect sensor phase)
    starboardMotor.setInverted(false);

    // Phase
    portMotor.setSensorPhase(true);
    starboardMotor.setSensorPhase(false);
    
    // PID Values
    starboardMotor.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    starboardMotor.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    starboardMotor.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    starboardMotor.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    starboardMotor.config_IntegralZone(Constants.kPIDLoopIdx, Constants.kGains.kIzone, Constants.kTimeoutMs);

    portMotor.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    portMotor.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    portMotor.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    portMotor.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    portMotor.config_IntegralZone(Constants.kPIDLoopIdx, Constants.kGains.kIzone, Constants.kTimeoutMs);

    // Init Encoders
    portMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    starboardMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
  }

  /**
   * This function uses manual control from a joystick.
   * @author Joe
   * @author Travis
   * @author Khan
   */
  public static void flyByWireA(Joystick DriverJoystick){
    double thro = DriverJoystick.getRawAxis(RobotMap.driverStick_throaxis); // Populate thro with axis 1
    double yaw = DriverJoystick.getRawAxis(RobotMap.driverStick_yawaxis); // Populate with with axis 2

    starboardMotor.set(ControlMode.PercentOutput, (-1 * thro) + yaw);  // From the inverse of thro, subtract yaw
    portMotor.set(ControlMode.PercentOutput, (thro + yaw) * -1);  // subtract yaw from thro

    // Spin RSL if reversing
    if (thro >= 0.1) {
      ROS.spin_RSL(1);
    } else {
      ROS.spin_RSL(0);
    }
  }
  
  /**
   * This function turns joystick commands into commands for FWWA to use
   * @author Joe
   */
  public static void flyByWireB(Joystick DriverJoystick){
    double thro = DriverJoystick.getRawAxis(RobotMap.driverStick_throaxis); // Populate thro with axis 1
    double yaw = DriverJoystick.getRawAxis(RobotMap.driverStick_yawaxis); // Populate with with axis 2

    flyWithWiresA(((-1 * thro) + yaw), (-1 * thro - yaw)); // Send values to FlyWithWiresA
  }

  /**
   * This function uses digital imput to control the robot from a coprosser.
   * @author Joe
   */
  public static void flyWithWiresA(double starboardSpeed, double portSpeed){
    SmartDashboard.putNumber("Starboard Velocity Control", starboardSpeed);
    SmartDashboard.putNumber("Port Velocity Control", portSpeed);
    starboardMotor.set(ControlMode.Velocity, (starboardSpeed * Constants.ticksPerMeter) / 10);  // Number of ticks per 
    SmartDashboard.putNumber("Target(starboard)", (starboardSpeed * Constants.ticksPerMeter) / 10);
    SmartDashboard.putNumber("Error", (starboardMotor.getClosedLoopError()));
    portMotor.set(ControlMode.Velocity, (portSpeed * Constants.ticksPerMeter) / 10);  // Set to the target speed
  }

  /**
   * This function uses digital imput to control the robot from a coprosser (percentage output)
   * @author Joe
   */
  public static void flyWithWiresB(double starboardSpeed, double portSpeed){
    starboardMotor.set(ControlMode.PercentOutput, starboardSpeed);  // Set to the target speed
    portMotor.set(ControlMode.PercentOutput, portSpeed);  // Set to the target speed
  }

  /**
   * Zero the drive encoders.
   * @author Joe
   */
  public void zeroEncoders(){
    portMotor.setSelectedSensorPosition(0, 0, 30); // Set encoder value, set loop (0 for primary, 1 for aux), set timeout to report error (in ms)
    starboardMotor.setSelectedSensorPosition(0, 0, 30);
  }

  public static boolean operatorIsOveride(Joystick DriverJoystick){return ((Math.abs(DriverJoystick.getRawAxis(RobotMap.driverStick_throaxis)) > RobotMap.overideSensitivity) || (Math.abs(DriverJoystick.getRawAxis(RobotMap.driverStick_yawaxis)) > RobotMap.overideSensitivity));} // Returns true if the joystick is being moved
  public double getDriveEncoderPort(){return portMotor.getSelectedSensorPosition();} // Returns the encoder value of the port motor
  public double getDriveEncoderStarboard(){return starboardMotor.getSelectedSensorPosition();} // Returns the encoder value of the starboard motor
  public double getOverallSpeed(){return (((starboardMotor.getSelectedSensorVelocity() + portMotor.getSelectedSensorVelocity()) / 2) * 10) / Constants.ticksPerMeter;} // Returns the average speed of the robot in knots

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
