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

    starboard.set(ControlMode.PercentOutput, (-1 * thro) - yaw);  // From the inverse of thro, subtract yaw
    port.set(ControlMode.PercentOutput, thro - yaw);  // subtract yaw from thro
  }

  // Quick Items
  public void zeroEncoders(){
    // zero encoders
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
