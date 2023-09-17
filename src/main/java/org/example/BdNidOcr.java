package org.example;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;

import java.awt.image.BufferedImage;


import java.awt.image.BufferedImage;
import java.io.File;

public class BdNidOcr {
    static {
        nu.pattern.OpenCV.loadLocally();
//        System.loadLibrary("opencv_java4"); // Use "opencv_java4" for OpenCV 4.x
    }

    public static void main(String[] args) {
        // Initialize Tesseract
        ITesseract tesseract = new Tesseract();

        // Set the path to your Tesseract installation (you may need to change this)
        tesseract.setDatapath("D:\\works\\NID_Cropped/");

        try {
            // Load the image containing the NID
            File imageFile = new File("D:\\works\\NID_Cropped/AbdullahNew.jpg");

            // Load the image using OpenCV
            Mat image = Imgcodecs.imread(imageFile.getAbsolutePath());

            // Convert the OpenCV Mat to BufferedImage
            BufferedImage bufferedImage = matToBufferedImage(image);
            tesseract.setTessVariable("tessedit_char_whitelist", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
            tesseract.setPageSegMode(6);
            // Perform OCR to extract text from the BufferedImage
            String extractedText = tesseract.doOCR(bufferedImage);

            // Print the extracted text
            System.out.println("Extracted Text:");
            System.out.println(extractedText);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;

        matrix.get(0, 0, data);

        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;

                // BGR to RGB conversion
                for (int i = 0; i < data.length; i += 3) {
                    byte blue = data[i];
                    data[i] = data[i + 2];
                    data[i + 2] = blue;
                }
                break;
            default:
                return null;
        }

        BufferedImage image = new BufferedImage(cols, rows, type);
        image.getRaster().setDataElements(0, 0, cols, rows, data);

        return image;
    }

}
