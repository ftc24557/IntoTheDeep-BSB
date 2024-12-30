package org.firstinspires.ftc.teamcode.config.Subsystems.Drive.Tank;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class TankOperator {
    TankLocalizer localizer;

    Pose2D targetPose;
    public TankOperator(TankLocalizer tankLocalizer){
        localizer = tankLocalizer;
    }

    private double GetAngleToPose(Pose2D pose){
        return Math.atan2(pose.getX(DistanceUnit.CM)-localizer.x, pose.getY(DistanceUnit.CM)- localizer.y);
    };
    private double GetTargetAngle(Pose2D pose){

        double deltaAngle = GetAngleToPose(pose)-localizer.drive.GetCurrentAngleRadians(true);
        return localizer.drive.GetCurrentAngleRadians(false)+deltaAngle;
    }

    private double CalcTurningPower(double delta){
        return delta * TankOperatorConstants.kPHeading;
    }

    private double CalcLinearFollowingPower(double delta){
        return delta*TankOperatorConstants.kPLinear;
    }
    public void AsyncFollow(){
        localizer.UpdateLocalizer();
        double deltaAngle = -localizer.drive.GetCurrentAngleRadians(false)+GetTargetAngle(targetPose);
        double turningPower = CalcTurningPower(deltaAngle);
        double linearPower = 0;
        if (Math.abs(deltaAngle)<TankOperatorConstants.angleTreshold){
            double deltaLinear = Math.sqrt(Math.pow(targetPose.getX(DistanceUnit.CM)-localizer.x,2)+Math.pow(targetPose.getY(DistanceUnit.CM) - localizer.y, 2));
            linearPower = CalcLinearFollowingPower(deltaLinear);
        }
        localizer.drive.SetDrivePowers(linearPower-turningPower, linearPower+turningPower);



    }





}
