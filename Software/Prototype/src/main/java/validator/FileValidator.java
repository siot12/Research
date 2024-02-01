package validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileValidator {
    public static void main(String[] args) {
        // Ensure two file paths are provided as arguments
        if (args.length != 2) {
            System.out.println("Usage: java FileValidator <file_to_test> <template_file>");
            return;
        }

        String testFilePath = args[0];
        String templateFilePath = args[1];

        validateFile(testFilePath, templateFilePath);
    }

    private static void validateFile(String testFilePath, String templateFilePath) {
        try {
            // Read all lines from both files
            Path testPath = Paths.get(testFilePath);
            Path templatePath = Paths.get(templateFilePath);
            BufferedReader testReader = Files.newBufferedReader(testPath);
            BufferedReader templateReader = Files.newBufferedReader(templatePath);

            String testLine = testReader.readLine();
            String templateLine = templateReader.readLine();

            int lineNum = 1;
            boolean isValid = true;

            while (testLine != null || templateLine != null) {
                if (testLine == null || templateLine == null || !testLine.equals(templateLine)) {
                    System.out.println("Mismatch found at line " + lineNum + ":");
                    System.out.println("Test File: " + (testLine != null ? testLine : "End of file reached"));
                    System.out.println("Template File: " + (templateLine != null ? templateLine : "End of file reached"));
                    System.out.println();
                    isValid = false;
                }
                testLine = testReader.readLine();
                templateLine = templateReader.readLine();
                lineNum++;
            }

            if (isValid) {
                System.out.println("The test file matches the template.");
            } else {
                System.out.println("The test file does not match the template.");
            }

            testReader.close();
            templateReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
