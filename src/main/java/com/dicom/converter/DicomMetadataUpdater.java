package com.dicom.converter;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.data.UID;
import org.dcm4che3.data.VR;
import java.nio.file.Files;

import java.io.File;
import java.io.IOException;

/**
 * Updates DICOM Encoding
 *
 * @author tdiprima
 */
public class DicomMetadataUpdater {

    public static void updateDicomWithJPEG(File dicomFile, File jpegFile, File outputDicomFile) throws IOException {
        Attributes dicomAttributes;

        // Read the existing DICOM file
        try (DicomInputStream dis = new DicomInputStream(dicomFile)) {
            dicomAttributes = dis.readDataset(-1, -1);
        }

        // Update Transfer Syntax UID to indicate standard JPEG encoding
        dicomAttributes.setString(Tag.TransferSyntaxUID, null, UID.JPEGBaseline8Bit);

        // Add the new JPEG image data
        byte[] jpegData = Files.readAllBytes(jpegFile.toPath()); // Read JPEG image as byte array
        dicomAttributes.setBytes(Tag.PixelData, VR.OB, jpegData); // Replace PixelData in DICOM attributes

        // Write the updated DICOM file to the specified output path
        try (DicomOutputStream dos = new DicomOutputStream(outputDicomFile)) {
            dos.writeDataset(dicomAttributes.createFileMetaInformation(UID.JPEGBaseline8Bit), dicomAttributes);
        }
    }

}
