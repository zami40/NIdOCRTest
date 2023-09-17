package org.example;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NidOcrRegexp {
    public static void main(String[] args) {
        // Set the path to the Tesseract executable (change this path to match your system)
        String tesseractPath = "D:\\works\\NID_Cropped";

        // Initialize Tesseract OCR engine
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tesseractPath);

        // Set the path to your NID image file
        String imagePath = "D:\\works\\NID_Cropped/tanvir.jpg";

        try {
            // Perform OCR on the NID image
            String extractedText = tesseract.doOCR(new File(imagePath));

            // Define regular expressions to match Date of Birth and NID/ID number patterns
            Pattern dobPattern = Pattern.compile("Date of Birth: ([0-9]{2} ([A-Z]|[a-z]){3} [0-9]{4})");
            Pattern nidPattern = Pattern.compile("NID No: ([0-9]+)|ID NO: ([0-9]+)");

            // Match Date of Birth
            Matcher dobMatcher = dobPattern.matcher(extractedText);
            if (dobMatcher.find()) {
                String dob = dobMatcher.group(1);
                System.out.println("Date Of Birth: " + dob);
            }

            // Match NID/ID number
            Matcher nidMatcher = nidPattern.matcher(extractedText);
            if (nidMatcher.find()) {
                String nid = nidMatcher.group(1);
                if (nid == null) {
                    // If NID number is not found, try the alternative pattern
                    nid = nidMatcher.group(2);
                }
                System.out.println("NID/ID Number: " + nid);
            }
        } catch (TesseractException e) {
            System.err.println("Error during OCR: " + e.getMessage());
        }
    }
}
