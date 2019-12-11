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
   */
  public void init(){
    // Joysticks
    RobotMap.driverStick = new Joystick(RobotMap.driverStick_Port); // Define the joystick and attach its port to the joystick object in RobotMap
    
    // Motors
    starboardMotor = new TalonSRX(RobotMap.starboardAddress); // Define starboard motor and attach its address to the TalonSRX object in RobotMap
    portMotor = new TalonSRX(RobotMap.portAddress); // Define port motor
    starboardMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative); // Select the feedback sensor
    portMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    portMotor.setInverted(true); // Set inverted (does not affect sensor phase)
    starboardMotor.setInverted(false);
  }

  /**
   * This function uses manual control from a joystick.
   * @author Joe
   */
  public static void flyByWireA(Joystick DriverJoystick){
    double thro = DriverJoystick.getRawAxis(RobotMap.driverStick_throaxis); // Populate thro with axis 1
    double yaw = DriverJoystick.getRawAxis(RobotMap.driverStick_yawaxis); // Populate with with axis 2

    starboardMotor.set(ControlMode.PercentOutput, (-1 * thro) + yaw);  // From the inverse of thro, subtract yaw
    portMotor.set(ControlMode.PercentOutput, -(thro + yaw));  // subtract yaw from thro

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

    flyWithWiresA(((-1 * thro) + yaw), (thro + yaw)); // Send values to FlyWithWiresA
  }

  /**
   * This function uses digital imput to control the robot from a coprosser.
   * @author Joe
   */
  public static void flyWithWiresA(double starboardSpeed, double portSpeed){
    starboardMotor.set(ControlMode.Velocity, starboardSpeed);  // Set to the target speed
    portMotor.set(ControlMode.Velocity, portSpeed);  // Set to the target speed
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
  public double getOverallSpeed(){return (((starboardMotor.getSelectedSensorVelocity() + portMotor.getSelectedSensorVelocity()) / 2) / Constants.rpmPerMeter) * 1.944;} // Returns the average speed of the robot in knots

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
