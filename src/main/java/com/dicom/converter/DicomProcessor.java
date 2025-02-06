package com.dicom.converter;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import java.io.File;
import java.io.IOException;

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
        return transferSyntaxUID;
    }
}
