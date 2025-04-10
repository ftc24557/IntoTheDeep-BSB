package org.firstinspires.ftc.teamcode.config.Subsystems.Distance;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Distance {

    Rev2mDistanceSensor distance;

    public Distance(HardwareMap hardwareMap){
        distance = hardwareMap.get(Rev2mDistanceSensor.class, DistanceConstants.HMDistanceSensor);
    }

    public double GetCenterDistanceInches(){
        return (distance.getDistance(DistanceUnit.INCH)+DistanceConstants.SensorOffset);
    }



}
