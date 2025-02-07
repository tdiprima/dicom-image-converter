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
            // Read file meta
            Attributes fileMeta = dis.readFileMetaInformation();

            // Read the main dataset
            Attributes dataset = dis.readDataset(-1, -1);

            // Merge file meta into the dataset (so TransferSyntaxUID is available directly)
            dataset.addAll(fileMeta);

            return dataset;
        }
    }

    public static String detectEncoding(Attributes dicomAttributes) {
        if (dicomAttributes == null) {
            System.out.println("DICOM Attributes are null!");
            return null;
        }

        // First, check TransferSyntaxUID in the file meta information
        String transferSyntaxUID = dicomAttributes.getString(Tag.TransferSyntaxUID);

        if (transferSyntaxUID == null) {
            System.out.println("Transfer Syntax UID not found in dataset. Checking file meta info...");
            Attributes fileMetaInfo = dicomAttributes.getNestedDataset(Tag.FileMetaInformationGroupLength);
            if (fileMetaInfo != null) {
                transferSyntaxUID = fileMetaInfo.getString(Tag.TransferSyntaxUID);
            }
        }

        System.out.println("Detected Transfer Syntax UID: " + transferSyntaxUID);
        return transferSyntaxUID;
    }

}
