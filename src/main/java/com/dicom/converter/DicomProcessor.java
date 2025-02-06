package com.dicom.converter;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import java.io.File;
import java.io.IOException;
import org.dcm4che3.data.UID;

/**
 * Reads DICOM Files and Extracts Encoding
 *
 * @author tdiprima
 */
public class DicomProcessor {

    public static Attributes readDicomFile(File dicomFile) throws IOException {
        try (DicomInputStream dis = new DicomInputStream(dicomFile)) {
            return dis.readDataset(-1, -1);
        }
    }

    public static String detectEncoding(Attributes dicomAttributes) {
        String transferSyntaxUID = dicomAttributes.getString(Tag.TransferSyntaxUID);
        System.out.println("Detected Transfer Syntax UID: " + transferSyntaxUID);

        if (UID.JPEG2000.equals(transferSyntaxUID) || UID.JPEG2000Lossless.equals(transferSyntaxUID)) {
            return UID.JPEG2000; // Treat both as JPEG2000
        } else {
            return transferSyntaxUID;
        }
    }

}
