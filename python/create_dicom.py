"""
Create DICOM file with JPEG 2000 encoding

Author: tdiprima
"""
__author__ = 'tdiprima'

import pydicom
from pydicom.dataset import Dataset, FileDataset
from pydicom.uid import JPEG2000Lossless
from pydicom.encaps import encapsulate

# Paths
jpeg2000_image_path = "output_image.jp2"  # Pre-encoded JPEG 2000 image
output_dicom_path = "test_jpeg2000.dcm"

# Step 1: Read JPEG 2000 bytes
with open(jpeg2000_image_path, "rb") as jp2_file:
    jpeg2000_bytes = jp2_file.read()

# Step 2: Encapsulate the JPEG 2000 data
encapsulated_data = encapsulate([jpeg2000_bytes])  # Encapsulate into fragments

# Step 3: Create File Meta Information
file_meta = pydicom.dataset.FileMetaDataset()
file_meta.MediaStorageSOPClassUID = pydicom.uid.SecondaryCaptureImageStorage
file_meta.MediaStorageSOPInstanceUID = pydicom.uid.generate_uid()

# Transfer Syntax UIDs
file_meta.TransferSyntaxUID = pydicom.uid.JPEG2000  # 1.2.840.10008.1.2.4.91

file_meta.ImplementationClassUID = "1.2.3.4.5.6.7.8.9"  # Dummy UID

# Step 4: Create DICOM dataset
ds = FileDataset(output_dicom_path, {}, file_meta=file_meta, preamble=b"\0" * 128)

# Set DICOM Metadata
ds.PatientName = "Test^Patient"
ds.PatientID = "123456"
ds.Modality = "OT"  # Other
ds.StudyInstanceUID = pydicom.uid.generate_uid()
ds.SeriesInstanceUID = pydicom.uid.generate_uid()
ds.SOPInstanceUID = file_meta.MediaStorageSOPInstanceUID  # Match File Meta
ds.SOPClassUID = file_meta.MediaStorageSOPClassUID

# Step 5: Set Image Pixel Data Attributes
ds.PixelData = encapsulated_data  # Use encapsulated data
ds.Rows = 256
ds.Columns = 256
ds.BitsAllocated = 8
ds.BitsStored = 8
ds.HighBit = 7
ds.SamplesPerPixel = 1
ds.PhotometricInterpretation = "MONOCHROME2"
ds.PixelRepresentation = 0  # unsigned

# Step 6: Save the DICOM file
ds.is_little_endian = True
ds.is_implicit_VR = False  # Explicit VR is required for JPEG 2000
ds.save_as(output_dicom_path, write_like_original=False)

print(f"DICOM file '{output_dicom_path}' created successfully!")
