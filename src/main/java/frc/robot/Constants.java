package frc.robot;

public class Constants {
    /*this class contains all constants in relation to the robot. 
    These values NEED TO BE EDITED based off port numbers on the actual robot
    (or you can change the the wiring but that isnt suggested)
    */

    //arcade drive constants
    public final static int leftDriveLeadPort=0;
    public final static int leftDriveFollowPort=1;
    public final static int rightDriveLeadPort=2;
    public final static int rightDriveFollowPort=3;

    public final static double drive_speed_max=0.7;

    //elevator constants
    public final static int elevLeftPort=4;
    public final static int elevRightPort=5;
    public final static int elevControlButtonNum=0;

    public final static double elev_speed=0.7;
    public final static double AngleLiftElev=100;
    public final static double timeToLiftElev=2; 


    //motor stop constant
    public final static double motorStopPower=0.0;



    //joystick constants
    public final static int joystickPort=1;
    
    public final static int FwdBckwdAxis=1;
    public final static int turnAxis=4;

    //PigeonIMU port
    public final static int imuPort=1;

    
    
    
}
