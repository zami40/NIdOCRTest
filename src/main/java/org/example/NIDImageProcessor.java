package org.example;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NIDImageProcessor {
    public static void main(String[] args) {
        // Set the path to the Tesseract executable (you may need to change this path).
//        System.setProperty("java.library.path", "path/to/tesseract/library");

        // Initialize Tesseract
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\works\\NID_Cropped/"); // Path to the tessdata directory

        try {
            // Load the image
            File imageFile = new File("D:\\works\\NID_Cropped/arif.jpg");

            // Perform OCR on the image
            String ocrResult = tesseract.doOCR(imageFile);

            // Extract Date of Birth (DOB)
            String dobPatternStr = "\\bDOB:\\s*(\\d{2}/\\d{2}/\\d{4})\\b";
            Pattern dobPattern = Pattern.compile(dobPatternStr);
            Matcher dobMatcher = dobPattern.matcher(ocrResult);
            String dob = dobMatcher.find() ? dobMatcher.group(1) : "Not found";

            // Extract NID No (for Smart NID)
            String nidPatternStr = "\\bNID\\s*No:\\s*(\\d{13})\\b";
            Pattern nidPattern = Pattern.compile(nidPatternStr);
            Matcher nidMatcher = nidPattern.matcher(ocrResult);
            String nidNo = nidMatcher.find() ? nidMatcher.group(1) : "Not found";

            // Extract ID No (for previous NID card)
            String idPatternStr = "\\bID\\s*No:\\s*([A-Z0-9]+)\\b";
            Pattern idPattern = Pattern.compile(idPatternStr);
            Matcher idMatcher = idPattern.matcher(ocrResult);
            String idNo = idMatcher.find() ? idMatcher.group(1) : "Not found";

            // Print the extracted information
            System.out.println("Date of Birth: " + dob);
            System.out.println("NID No (for Smart NID): " + nidNo);
            System.out.println("ID No (for previous NID card): " + idNo);

        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
