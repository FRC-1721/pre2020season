/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Not a lot happens in this subsystem beacuse most of the code
 * is on the coprossesor, but this is a good place to put items
 * directly controled by ROS
 */
public class ROS extends Subsystem {
  
  public static void spin_RSL(double spin_speed){
    RobotMap.spinningRSLSRX.set(ControlMode.PercentOutput, spin_speed); // Set to the desired speed
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
