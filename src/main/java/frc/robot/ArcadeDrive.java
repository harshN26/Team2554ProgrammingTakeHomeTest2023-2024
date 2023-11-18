package frc.robot;


import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;


public class ArcadeDrive {
  private CANSparkMax leftDriveLead = new CANSparkMax(Constants.leftDriveLeadPort, MotorType.kBrushless);
  private CANSparkMax rightDriveLead = new CANSparkMax(Constants.rightDriveLeadPort, MotorType.kBrushless);
  private CANSparkMax leftDriveFollow = new CANSparkMax(Constants.leftDriveFollowPort, MotorType.kBrushless);
  private CANSparkMax rightDriveFollow = new CANSparkMax(Constants.rightDriveFollowPort, MotorType.kBrushless);


  public PigeonIMU imu=new PigeonIMU(Constants.imuPort);
  

  double speed,turn,leftSpeed,rightSpeed;

  double curr_angle=0.0;

  public void setDrivePowers(double leftPower, double rightPower){
    leftDriveLead.set(Constants.drive_speed_max*leftPower);
    leftDriveFollow.set(Constants.drive_speed_max*leftPower);
    rightDriveLead.set(-rightPower*Constants.drive_speed_max);
    rightDriveFollow.set(-rightPower*Constants.drive_speed_max);
  }

  public void teleOpForArcade(Joystick joy1){
    speed=-joy1.getRawAxis(Constants.FwdBckwdAxis)*0.8;
    turn=joy1.getRawAxis(Constants.turnAxis)*0.6;
    leftSpeed=speed+turn;
    rightSpeed=speed-turn;
     
    setDrivePowers(leftSpeed,rightSpeed);
  }

  public Rotation2d headingFromIMU(){
    double[] ypr = new double[3];
    imu.getYawPitchRoll(ypr);

    return Rotation2d.fromDegrees(Math.IEEEremainder(ypr[0], 360));
  }

  public double getHeading(){
    double heading= headingFromIMU().getDegrees();
    return heading%360;
  }


  public void turnToAngle(double dir_speed,double targetAngleAsDegree){
      setDrivePowers(dir_speed, -dir_speed);
      //get error and update angle
      curr_angle=getHeading();
      

      if(curr_angle>=(targetAngleAsDegree-2)&&curr_angle<=(targetAngleAsDegree+2))dir_speed=0.0;// give 2 degree tolerance

      if(curr_angle>(targetAngleAsDegree+2)||(curr_angle<(targetAngleAsDegree-2)&&dir_speed<0))
        dir_speed*=-1;

  }
}
