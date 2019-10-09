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

/**
 * Add your docs here. make it move forwards and backwords 
 */
public class Drivetrain extends Subsystem {
public static void manualcontrol (TalonSRX StarBoard, TalonSRX port, Joystick JoyStickLever) 
{
  Double thro = JoyStickLever.getRawAxis(1);
  Double yaw = JoyStickLever.getRawAxis(2);
  port.set(ControlMode.PercentOutput, (thro * -1) - yaw); 
  StarBoard.set(ControlMode.PercentOutput, thro - yaw);

  if(thro >= 0.1){
    SmartDashboard.putString("Moving?" , "TUUUUUUUUUUUUURRRRRRRRRRRNNNNNNNNN" );
  }else{
    SmartDashboard.putString("Moving?" , "scurt" );
  }
}


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
