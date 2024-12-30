package org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera.CameraConstants;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

import java.util.List;

public class CameraIntake {
    VisionPortal portal;
    ColorBlobLocatorProcessor colorLocator;
    List<ColorBlobLocatorProcessor.Blob> blobs;

    public CameraIntake(HardwareMap hardwareMap, ColorRange colorRange){
        colorLocator = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(colorRange)
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
                .setRoi(ImageRegion.asUnityCenterCoordinates(-1, 1, 1, -1))
                .setDrawContours(true)
                .setBlurSize(5)
                .build();
        for (int i =0; i< CameraConstants.BlobFilters.length; i++){
            colorLocator.addFilter(CameraConstants.BlobFilters[i]);
        }
        colorLocator.setSort(CameraConstants.BlobSort);
        portal = new VisionPortal.Builder()
                .addProcessor(colorLocator)
                .setCameraResolution(new Size(320, 240))
                .setCamera(hardwareMap.get(WebcamName.class, CameraConstants.HMWebcam))
                .build();
    }
    public boolean GetMatchingSample(){
        blobs = colorLocator.getBlobs();

        if (!blobs.isEmpty()) {
            return true;
        }
        return false;
    }
    public Point GetBoxFitCenter(){
        if (GetMatchingSample()) {
            RotatedRect boxFit = blobs.get(0).getBoxFit();
            return boxFit.center;
        } else {
            return null;
        }
    }
    }
