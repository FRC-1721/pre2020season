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

/**
 * This subsystem drives a tank style robot
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /**
   * This function drives the robot using operator imput
   */
  public static void flyByWireA(TalonSRX starboard, TalonSRX port, Joystick DriverJoystick){

    double thro = DriverJoystick.getRawAxis(1); // Populate thro with axis 1
    double yaw = DriverJoystick.getRawAxis(2); // Populate with with axis 2

    starboard.set(ControlMode.PercentOutput, (-1 * thro) - yaw);  // From the inverse of thro, subtract yaw
    port.set(ControlMode.PercentOutput, thro - yaw);  // subtract yaw from thro
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
