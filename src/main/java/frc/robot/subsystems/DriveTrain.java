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
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
  public static void FlyByWire (TalonSRX starboard, TalonSRX port, Joystick driverstick)
  {
    // Gets the position of the joystick and moves the motors accordingly.
    Double thro = driverstick.getRawAxis(1);
    starboard.set(ControlMode.PercentOutput, thro);
    port.set(ControlMode.PercentOutput, thro * -1);
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
