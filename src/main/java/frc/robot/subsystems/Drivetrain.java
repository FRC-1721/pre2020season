/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * This subsystem drives a tank style robot.
 */
public class Drivetrain extends Subsystem {
  /**
   * This function uses manual control from a joystick.
   */
  public static void flyByWireA(TalonSRX starboard, TalonSRX port, Joystick DriverJoystick){
    double thro = DriverJoystick.getRawAxis(RobotMap.driverStick_throaxis); // Populate thro with axis 1
    double yaw = DriverJoystick.getRawAxis(RobotMap.driverStick_yawaxis); // Populate with with axis 2

    starboard.set(ControlMode.PercentOutput, (-1 * thro) + yaw);  // From the inverse of thro, subtract yaw
    port.set(ControlMode.PercentOutput, thro + yaw);  // subtract yaw from thro
  }

  /**
   * This function uses digital imput to control the robot from a coprosser.
   */
  public static void flyWithWiresA(TalonSRX starboardMotor, TalonSRX portMotor, double starboardThro, double portThro){
    starboardMotor.set(ControlMode.PercentOutput, starboardThro);  // Set to the double starboardThro
    portMotor.set(ControlMode.PercentOutput, portThro);  // Set to the double portThro
  }

  /**
   * Zero the drive encoders.
   */
  public void zeroEncoders(){
    // zero encoders
  }

  public double getDriveEncoderPort(){return RobotMap.portMotor.getSelectedSensorPosition();} // Returns the encoder value of the port motor
  public double getDriveEncoderStarboard(){return RobotMap.starboardMotor.getSelectedSensorPosition();} // Returns the encoder value of the starboard motor

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
