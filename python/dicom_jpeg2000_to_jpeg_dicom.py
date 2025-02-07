"""
Re-encodes a JPEG2000 DICOM as a new DICOM with standard JPEG (baseline) compression.
Author: tdiprima
Version: 1.0
License: MIT
"""

__author__ = 'tdiprima'
__version__ = '1.0'
__license__ = 'MIT'

import os
import io
import pydicom
from pydicom import dcmread
from pydicom.uid import JPEGBaseline, JPEG2000, JPEG2000Lossless
from pydicom.encaps import encapsulate
from PIL import Image


def reencode_jpeg2000_as_jpeg_dicom(input_dir, output_dir="processed"):
    os.makedirs(output_dir, exist_ok=True)

    for filename in os.listdir(input_dir):
        if not filename.lower().endswith(".dcm"):
            continue

        dicom_path = os.path.join(input_dir, filename)
        ds = dcmread(dicom_path)

        tsuid = ds.file_meta.TransferSyntaxUID
        is_jpeg2000 = tsuid in [JPEG2000, JPEG2000Lossless,
                                "1.2.840.10008.1.2.4.90",
                                "1.2.840.10008.1.2.4.91"]
        is_jpeg = tsuid.startswith("1.2.840.10008.1.2.4.5")

        # Only re-encode if it's JPEG 2000
        if not is_jpeg2000:
            print(f"Skipping {filename}: Not JPEG2000 Transfer Syntax")
            continue

        # Decode
        arr = ds.pixel_array

        # Re-compress to baseline JPEG in memory
        with io.BytesIO() as buff:
            Image.fromarray(arr).save(buff, format='JPEG')
            jpeg_bytes = buff.getvalue()

        # Create a copy or modify original
        new_ds = ds.copy()

        # Update the file meta to JPEG Baseline
        new_ds.file_meta.TransferSyntaxUID = JPEGBaseline

        # Encapsulate new JPEG data
        new_ds.PixelData = encapsulate([jpeg_bytes])

        new_ds.is_little_endian = True
        new_ds.is_implicit_VR = False

        # Save to output
        base_name, _ = os.path.splitext(filename)
        out_path = os.path.join(output_dir, base_name + "_jpeg.dcm")
        new_ds.save_as(out_path, write_like_original=False)
        print(f"Re-encoded and saved: {out_path}")


if __name__ == "__main__":
    input_folder = "../img"  # or whatever path
    reencode_jpeg2000_as_jpeg_dicom(input_folder)
    print("Done!")
