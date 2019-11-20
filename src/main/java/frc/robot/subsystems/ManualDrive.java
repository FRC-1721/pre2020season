/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
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
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class ManualDrive extends Subsystem {
  public static void Manual (TalonSRX starboard, TalonSRX port, Joystick driverStick)
  {
    Double yaw = driverStick.getRawAxis(2);
    
    
    Double thro = driverStick.getRawAxis(1);
    starboard.set(ControlMode.PercentOutput, -thro + yaw);
    port.set(ControlMode.PercentOutput, (thro + yaw));

    if(thro > 0)
    {
      SmartDashboard.putString("Moving?", "BACKING UP!");
    } 
    else if(thro < 0) 
    {
      SmartDashboard.putString("Moving?", "OUT OF THE WAY!");
    } 
    else
    {
      SmartDashboard.putString("Moving?", "WHAT IS MY PURPOSE?");
    }
    if(yaw < 0)
    {
      SmartDashboard.putString("Spin?", "IM SPINNING LEFT!");
    } 
    else if(yaw > 0)
    {
      SmartDashboard.putString("Spin?", "IM SPINNING RIGHT!");
    } 
    else 
    {
      SmartDashboard.putString("Spin?", "IM NOT SPINNING!");
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
