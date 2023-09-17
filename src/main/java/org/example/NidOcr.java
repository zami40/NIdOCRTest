package org.example;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class NidOcr {
    public static void main(String[] args) {
        // Set the path to the Tesseract executable (change this path to match your system)
        String tesseractPath = "D:\\works\\NID_Cropped/";

        // Initialize Tesseract OCR engine
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tesseractPath);

        // Set the path to your NID image file
        String imagePath = "D:\\works\\NID_Cropped/Arif.jpg";

        try {
            // Perform OCR on the NID image
            String extractedText = tesseract.doOCR(new File(imagePath));

            // Print the extracted text
            System.out.println("Extracted Text:");
            System.out.println(extractedText);
        } catch (TesseractException e) {
            System.err.println("Error during OCR: " + e.getMessage());
        }
    }
}
