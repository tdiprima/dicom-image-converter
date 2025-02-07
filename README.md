# DICOM Image Converter
![License](https://img.shields.io/github/license/tdiprima/dicom-image-converter)
![Languages](https://img.shields.io/github/languages/top/tdiprima/dicom-image-converter)
![Contributions](https://img.shields.io/badge/contributions-welcome-brightgreen)

This project aims to:

- Read DICOM files from a directory.
- Detect the image encoding (JPEG, JPEG 2000, etc.).
- Convert JPEG 2000 images to standard JPEG.
- Update the DICOM metadata to reflect the conversion.
- Save the processed DICOM files without modifying the originals.

---

## Current Status

### Java Maven Code
- **What Works**:
  - Reads DICOM files.
  - Detects some image encodings correctly.
- **Known Limitations**:
  - Re-encoding JPEG 2000 → JPEG isn't fully functional in Java due to missing codec support.
  - Additional libraries (like [jai-imageio-jpeg2000](https://github.com/jai-imageio/jai-imageio-jpeg2000) or OpenCV-based plugins) may be required, but can still cause compatibility issues.

### Getting Started

```sh
mvn clean package
java -jar target/dicom-image-converter-1.0-SNAPSHOT.jar ./img/
```

### Python Scripts

- **Scripts**:
  - `dicom_jpeg2000_to_jpeg.py` – Decodes JPEG/JPEG 2000 DICOM images to `.jpg` files.
  - `dicom_jpeg2000_to_jpeg_dicom.py` – Re-encodes JPEG 2000 DICOMs into standard JPEG DICOMs.
- **Dependencies**:
  - [pydicom](https://pydicom.github.io/)
  - [pylibjpeg](https://github.com/pydicom/pylibjpeg)
  - [pylibjpeg-openjpeg](https://github.com/pydicom/pylibjpeg-openjpeg)
  - [Pillow](https://pillow.readthedocs.io/en/stable/)

The Python solution is currently the most reliable way to re-encode JPEG 2000 DICOMs to JPEG.

---

## Future Plans

1. **Java Codec Improvements**  
   Investigate or add the necessary dcm4che Image I/O or JAI ImageIO plugins to fully support JPEG 2000 → JPEG conversion in Java.

2. **Refinement & Testing**  
   Expand test coverage with multi-frame DICOMs, 16-bit grayscale images, and color images.

3. **Documentation**  
   Provide step-by-step usage instructions for both the Java code and the Python scripts.

---

## Getting Started (Python)

1. **Install Dependencies**:

   ```bash
   pip install pydicom pillow pylibjpeg pylibjpeg-openjpeg
   ```

2. **Run the Conversion**:

   ```bash
   python dicom_jpeg2000_to_jpeg_dicom.py
   ```

   This will read `.dcm` files from `../img` by default and save re-encoded DICOM files in a new `processed/` folder.

---

## License
[MIT](LICENSE)
