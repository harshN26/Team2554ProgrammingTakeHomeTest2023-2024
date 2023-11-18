// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  private Elevator elev=new Elevator();
  private ArcadeDrive drive=new ArcadeDrive();

  private Joystick joy1=new Joystick(Constants.joystickPort);
 

  private double time;

  

  @Override
  public void robotInit() {

  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {

    //plan to move forward at full speed for 10 seconds
    time=Timer.getFPGATimestamp();
    if(time>=0&&time<10){
      drive.setDrivePowers(1.0, 1.0);
    }else if (time<11){
      //one second of pause before turn
      drive.setDrivePowers(Constants.motorStopPower, Constants.motorStopPower);
      //reset angle so robot doesnt turn more than 90 degrees after possible drift from stopping
      drive.imu.setYaw(0.0);
      
    }else if(time<15){
      //allocate rest of available auto time to turning to position of 90 degrees
      drive.turnToAngle(Constants.drive_speed_max,90.0);
    }else{
      //stop robot until mode switch to teleop
      drive.setDrivePowers(Constants.motorStopPower, Constants.motorStopPower);
    }
    if(drive.getHeading()==Constants.AngleLiftElev&&elev.buttonPresses%4==0){
      elev.moveElevAuto(elev.buttonPresses++);
    }
    else if(drive.getHeading()!=Constants.AngleLiftElev&&elev.buttonPresses%4==2){
      elev.moveElevAuto(elev.buttonPresses++);
    }

    elev.displaySmartDashboard(drive);

  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    
    //set powers to drivtrain based off joystick
    drive.teleOpForArcade(joy1);

    elev.moveElevTele(joy1);

    if(drive.getHeading()==Constants.AngleLiftElev&&elev.buttonPresses%4==0){

      elev.moveElevAuto(elev.buttonPresses++);
    }

    else if(drive.getHeading()!=Constants.AngleLiftElev&&elev.buttonPresses%4==2){

      elev.moveElevAuto(elev.buttonPresses++);
    }
    
    elev.displaySmartDashboard(drive);

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
