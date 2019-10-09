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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * This subsystem drives a tank style robot.
 */
public class Drivetrain extends Subsystem {
  public static void Driver_Control(TalonSRX Starboard, TalonSRX Port, Joystick Driverstick)
  {
    Double thro = Driverstick.getRawAxis(RobotMap.driverStick_throaxis); //populates "thro" as the angle of the driverstick
    Double yaw = Driverstick.getRawAxis(RobotMap.driverStick_yawaxis); //populates "yow" as the angle of the driverstick again
    Port.set(ControlMode.PercentOutput, thro + yaw);
    Starboard.set(ControlMode.PercentOutput, (thro * -1) + yaw);
    if(thro >= 1){
      SmartDashboard.putString("Moving?", "VROOM!" );
    } else if(thro <= -1){
      SmartDashboard.putString("Moving?", "BEEP BEEP!");
    } else {
      SmartDashboard.putString("Moving?", "Nope");
    }
    if(yaw != 0){
      SmartDashboard.putString("Spin?", "SPIN SPIN!");
    } else {
      SmartDashboard.putString("Spin?", "What?");
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
