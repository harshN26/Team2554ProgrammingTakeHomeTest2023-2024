package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {

    private CANSparkMax leftElev = new CANSparkMax(Constants.elevLeftPort, MotorType.kBrushless);
    private CANSparkMax rightElev = new CANSparkMax(Constants.elevRightPort, MotorType.kBrushless);
    double time=0.0;
    double timeMark=0.0;
    int buttonPresses=0;
    public void elevUpDown(double speed) {
        leftElev.set(speed);
        rightElev.set(-speed);
    }
    public void moveElevTele(Joystick joy1){
        time= Timer.getFPGATimestamp();
        if(joy1.getRawButtonPressed(Constants.elevControlButtonNum)){
            buttonPresses++;
          }

        //assume the elevator starts in the down position as there is no way to confirm without sensor

          if(buttonPresses%4==1){
            //go up
            elevUpDown(Constants.elev_speed);
          }
          else if(buttonPresses%4==3){
          //go down
            elevUpDown(-Constants.elev_speed);
          }else{
            //if button is pressed 0 times, or multiple of 2 or 4 times, stop motors
            elevUpDown(Constants.motorStopPower);
          }
    }
    public void moveElevAuto(int buttonPressCount){
        time= Timer.getFPGATimestamp();
        //assume the elevator starts in the down position as there is no way to confirm without sensor
        buttonPresses=buttonPressCount;
          if(buttonPresses%4==1){
            //go up
            timeMark=time;
            elevUpDown(Constants.elev_speed);
          }
          else if(buttonPresses%4==3){
            timeMark=time;
          //go down
            elevUpDown(-Constants.elev_speed);
          }
          if(timeMark+Constants.timeToLiftElev==time){
            buttonPresses++;
          }
          
          if(buttonPresses%2==0){
            //if button is pressed 0 times, or multiple of 2 or 4 times, stop motors
            elevUpDown(Constants.motorStopPower);
          }
    }
    
    public void displaySmartDashboard(ArcadeDrive drive){
        SmartDashboard.putBoolean("At auto elev up angle",drive.getHeading()==Constants.AngleLiftElev);
    }

}
