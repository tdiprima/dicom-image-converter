package com.dicom.converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Converts JPEG 2000 to Standard JPEG
 *
 * @author tdiprima
 */
public class ImageConverter {

    public static void convertToJPEG(File inputFile, File outputFile) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        if (image == null) {
            throw new IOException("Failed to decode JPEG 2000 image: " + inputFile.getName());
        }
        ImageIO.write(image, "JPEG", outputFile);
    }
}
