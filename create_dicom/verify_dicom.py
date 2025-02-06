"""
Verify Transfer Syntax UID

Author: tdiprima
"""
import pydicom

# Load the DICOM file
ds = pydicom.dcmread("../img/test_jpeg2000.dcm")

# Print the Transfer Syntax UID
print("Transfer Syntax UID:", ds.file_meta.TransferSyntaxUID)

