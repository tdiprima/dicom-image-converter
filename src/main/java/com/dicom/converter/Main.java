package com.dicom.converter;

import org.dcm4che3.data.UID;
import java.io.File;
import java.io.IOException;

/**
 * Processes All DICOM Files in a Directory
 *
 * @author tdiprima
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar dicom-image-converter.jar <directory>");
            return;
        }

        File directory = new File(args[0]);
        if (!directory.isDirectory()) {
            System.out.println("Provided path is not a directory.");
            return;
        }

        File outputDir = new File(directory, "processed");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        for (File file : directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".dcm"))) {
            try {
                System.out.println("Processing: " + file.getName());
                processDicomFile(file, outputDir);
            } catch (Exception e) {
                System.err.println("Failed to process " + file.getName() + ": " + e.getMessage());
            }
        }
    }

    private static void processDicomFile(File dicomFile, File outputDir) throws IOException {
        Attributes attributes = DicomProcessor.readDicomFile(dicomFile);
        String encoding = DicomProcessor.detectEncoding(attributes);

        if (UID.JPEG2000.equals(encoding)) {
            File jpegFile = new File(outputDir, dicomFile.getName().replace(".dcm", ".jpg"));
            ImageConverter.convertToJPEG(dicomFile, jpegFile);
            DicomMetadataUpdater.updateDicomMetadata(dicomFile, jpegFile, UID.JPEGBaseline1);
            System.out.println("Converted JPEG 2000 to JPEG: " + jpegFile.getName());
        } else {
            System.out.println("No conversion needed for: " + dicomFile.getName());
        }
    }
}
