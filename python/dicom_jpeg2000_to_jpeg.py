"""
Decodes all DICOMs (in the given folder) that use JPEG 2000 or JPEG, and saves them as .jpg files in a processed/ directory.
Author: tdiprima
"""
import os
import pydicom
from pydicom.filereader import dcmread
from pydicom.uid import JPEG2000, JPEG2000Lossless
from PIL import Image


def process_dicom_directory(input_dir, output_dir="processed"):
    # Make sure the output folder exists
    os.makedirs(output_dir, exist_ok=True)

    for filename in os.listdir(input_dir):
        if not filename.lower().endswith(".dcm"):
            continue

        dicom_path = os.path.join(input_dir, filename)
        ds = dcmread(dicom_path)

        # Grab the Transfer Syntax UID
        tsuid = ds.file_meta.TransferSyntaxUID

        # Check if it's JPEG 2000 or JPEG
        is_jpeg2000 = (tsuid in [JPEG2000, JPEG2000Lossless, 
                                 "1.2.840.10008.1.2.4.90", 
                                 "1.2.840.10008.1.2.4.91"])
        # Common JPEG transfer syntaxes (Baseline, Extended, Lossless, etc.)
        is_jpeg = tsuid.startswith("1.2.840.10008.1.2.4.5")

        if is_jpeg2000 or is_jpeg:
            # Decode pixel data into a NumPy array
            pixel_array = ds.pixel_array

            # Convert to a PIL Image (handles 2D single-frame only)
            img = Image.fromarray(pixel_array)

            # Build output path: same base name, .jpg extension
            base_name, _ = os.path.splitext(filename)
            out_path = os.path.join(output_dir, base_name + ".jpg")

            # Save as standard JPEG
            img.save(out_path, "JPEG")
            print(f"Saved: {out_path}")
        else:
            print(f"Skipping {filename} (not JPEG or JPEG2000)")


if __name__ == "__main__":
    input_folder = "../img"
    process_dicom_directory(input_folder, "processed")
    print("Done!")
