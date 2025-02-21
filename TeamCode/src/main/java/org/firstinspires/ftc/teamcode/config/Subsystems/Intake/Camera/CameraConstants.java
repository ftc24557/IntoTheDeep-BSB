package org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera;

import com.qualcomm.robotcore.util.SortOrder;

import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ColorSpace;
import org.opencv.core.Scalar;

public class CameraConstants {
    public static String HMWebcam = "Webcam 1";
    public static ColorBlobLocatorProcessor.BlobFilter[] BlobFilters = {
            new ColorBlobLocatorProcessor.BlobFilter(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, 10000, 80000),
            new ColorBlobLocatorProcessor.BlobFilter(ColorBlobLocatorProcessor.BlobCriteria.BY_DENSITY, 0.005, 1.0),
            new ColorBlobLocatorProcessor.BlobFilter(ColorBlobLocatorProcessor.BlobCriteria.BY_ASPECT_RATIO, 0.0, 10.0)
    };
    public static ColorBlobLocatorProcessor.BlobSort BlobSort = new ColorBlobLocatorProcessor.BlobSort(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, SortOrder.ASCENDING);
    public static ColorRange RedRange = new ColorRange(
            ColorSpace.HSV,
            new Scalar(0, 15, 15),
            new Scalar(20, 255, 255)
    );
}
