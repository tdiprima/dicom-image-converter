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

    // Decode JPEG 2000 to a BufferedImage
    public static BufferedImage decodeJPEG2000(File jpeg2000File) throws IOException {
        BufferedImage image = ImageIO.read(jpeg2000File); // Requires JPEG 2000 ImageIO plugin
        if (image == null) {
            throw new IOException("Failed to decode JPEG 2000 image: " + jpeg2000File.getName());
        }
        return image;
    }

    // Encode a BufferedImage as standard JPEG
    public static void encodeAsJPEG(BufferedImage image, File outputFile) throws IOException {
        ImageIO.write(image, "JPEG", outputFile);
    }
}
