package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.SortOrder;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import java.util.List;
@TeleOp(name = "Concept: Vision Color-Locator", group = "Concept")
public class camera extends LinearOpMode
{
    // Função para calcular o ângulo de rotação em relação ao ponto central da blob
    public static double calcRotationAngleInDegrees(Point centerPt, Point targetPt)
    {
        double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
        theta += Math.PI / 2.0;
        double angle = Math.toDegrees(theta);

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    @Override
    public void runOpMode()
    {
        // Filtros para os blobs
        ColorBlobLocatorProcessor.BlobFilter myAreaFilter = new ColorBlobLocatorProcessor.BlobFilter(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, 20000, 50000);
        ColorBlobLocatorProcessor.BlobFilter myDensityFilter = new ColorBlobLocatorProcessor.BlobFilter(ColorBlobLocatorProcessor.BlobCriteria.BY_DENSITY, 0.1, 1.0);
        ColorBlobLocatorProcessor.BlobFilter myRatioFilter = new ColorBlobLocatorProcessor.BlobFilter(ColorBlobLocatorProcessor.BlobCriteria.BY_ASPECT_RATIO, 0.0, 10.0);
        ColorBlobLocatorProcessor.BlobSort mySort = new ColorBlobLocatorProcessor.BlobSort(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, SortOrder.ASCENDING);

        ColorBlobLocatorProcessor colorLocator = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(ColorRange.BLUE)  // Cor azul
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // Excluir blobs dentro de outros blobs
                .setRoi(ImageRegion.asUnityCenterCoordinates(-1, 1, 1, -1))  // Área central da imagem
                .setDrawContours(true)  // Mostrar contornos na visualização
                .setBlurSize(5)  // Suavização da imagem
                .build();

        Servo servoRot = hardwareMap.get(Servo.class, "servoRot");
        double rotPosition = 0.5;

        colorLocator.addFilter(myAreaFilter);
        colorLocator.addFilter(myDensityFilter);
        colorLocator.addFilter(myRatioFilter);
        colorLocator.setSort(mySort);

        // Adicionar o processador de blobs à VisionPortal
        VisionPortal portal = new VisionPortal.Builder()
                .addProcessor(colorLocator)
                .setCameraResolution(new Size(320, 240))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        telemetry.setMsTransmissionInterval(50);  // Atualizações rápidas do telemetry para depuração
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        double currentSampleAngle = 0.0;

        // Variáveis de controle de clique
        boolean xButtonPressed = false;
        boolean yButtonPressed = false;
        boolean xButtonLastState = false;
        boolean yButtonLastState = false;

        // Execução durante a fase de inicialização (INIT)
        while (opModeInInit())
        {
            telemetry.addData("preview on/off", "... Camera Stream\n");

            // Obter a lista de blobs detectados
            List<ColorBlobLocatorProcessor.Blob> blobs = colorLocator.getBlobs();

            // Exibir as informações dos blobs, incluindo a maior blob
            telemetry.addLine("Area Density Aspect  Center");

            if (!blobs.isEmpty()) {
                // Selecionar a maior blob
                ColorBlobLocatorProcessor.Blob largestBlob = blobs.get(0);  // Maior blob após ordenação
                RotatedRect boxFit = largestBlob.getBoxFit();
                telemetry.addLine(String.format("%5d  %4.2f   %5.2f  (%3d,%3d)",
                        largestBlob.getContourArea(), largestBlob.getDensity(), largestBlob.getAspectRatio(), (int) boxFit.center.x, (int) boxFit.center.y));

                // Calcular o ângulo da maior blob
                Point[] myContourPoints = largestBlob.getContourPoints();
                currentSampleAngle = boxFit.angle;

                telemetry.addData("sampleAngle", currentSampleAngle);
                telemetry.addData("sampleSize", boxFit.size);
            }

            // Verificando o estado do botão X para clique
            if (gamepad1.x && !xButtonLastState) {
                xButtonPressed = !xButtonPressed;  // Alternar o estado de pressionado
                if (xButtonPressed) {
                    rotPosition = currentSampleAngle / 180;
                } else {
                    rotPosition = 0.5;  // Retorna a posição inicial
                }
            }
            xButtonLastState = gamepad1.x;  // Atualiza o estado anterior do botão X

            // Verificando o estado do botão Y para clique
            if (gamepad1.y && !yButtonLastState) {
                yButtonPressed = !yButtonPressed;  // Alternar o estado de pressionado
                if (yButtonPressed) {
                    rotPosition = 0.5;  // Retorna a posição inicial
                }
            }
            yButtonLastState = gamepad1.y;  // Atualiza o estado anterior do botão Y

            servoRot.setPosition(rotPosition);
            telemetry.update(); // Atualização rápida do telemetry
        }

        // Loop principal durante o TELEOP
        while (opModeIsActive()) {
            // Aqui você pode adicionar outras operações, se necessário
        }
    }
}
