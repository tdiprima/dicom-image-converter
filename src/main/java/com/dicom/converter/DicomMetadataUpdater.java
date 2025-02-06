package com.dicom.converter;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;

import java.io.File;
import java.io.IOException;

/**
 * Updates DICOM Encoding
 *
 * @author tdiprima
 */
public class DicomMetadataUpdater {

    public static void updateDicomMetadata(File dicomFile, File jpegFile, String newTransferSyntaxUID) throws IOException {
        Attributes dicomAttributes;
        try (DicomInputStream dis = new DicomInputStream(dicomFile)) {
            dicomAttributes = dis.readDataset(-1, -1);
        }

        dicomAttributes.setString(Tag.TransferSyntaxUID, null, newTransferSyntaxUID);

        try (DicomOutputStream dos = new DicomOutputStream(dicomFile)) {
            dos.writeDataset(dicomAttributes.createFileMetaInformation(newTransferSyntaxUID), dicomAttributes);
        }
    }
}
