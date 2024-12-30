package org.firstinspires.ftc.teamcode.config.Subsystems.Drive.Tank;

public class TankLocalizer {
    public double x = 0; //CM
    public double y = 0;


    public double lastX = 0;
    public double lastY = 0;
    public double lastEncoderPosLeft = 0;
    public double lastEncoderPosRight= 0;
    public double lastNormalAngle= 0;
    public double currentEncoderPosLeft= 0;
    public double currentEncoderPosRight= 0;
    public double currentNormalAngle= 0;
    
    public double currentAngle= 0;
    public double lastAngle= 0;
    
    
    public TankDrive drive;
    public TankLocalizer(TankDrive tankDrive){
        drive = tankDrive;
        drive.ResetEncoders();
        drive.ResetIMU();

    }
    public void UpdateLocalizer(){
        lastEncoderPosLeft = currentEncoderPosLeft;
        lastEncoderPosRight = currentEncoderPosRight;
        currentEncoderPosLeft = drive.GetSideEncoderPosCM(TankDrive.Side.LEFT);
        currentEncoderPosRight = drive.GetSideEncoderPosCM(TankDrive.Side.RIGHT);

        lastAngle = currentAngle;
        currentAngle = drive.GetCurrentAngleRadians(false);

        lastNormalAngle = currentNormalAngle;
        currentNormalAngle = drive.GetCurrentAngleRadians(true);

        lastX = x;
        lastY = y;
        double d = (currentEncoderPosLeft+currentEncoderPosRight)/2;
        x = lastX + (d*Math.cos(currentNormalAngle));
        y = lastY + (d*Math.sin(currentNormalAngle));
    }
}
