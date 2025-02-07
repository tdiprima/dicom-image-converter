In the DICOM standard:

- **1.2.840.10008.1.2.4.90** = JPEG 2000 *Lossless only*  
- **1.2.840.10008.1.2.4.91** = JPEG 2000 *Lossy or Lossless*

But in pydicom:

```python
from pydicom.uid import JPEG2000, JPEG2000Lossless

JPEG2000        # is 1.2.840.10008.1.2.4.91
JPEG2000Lossless  # is 1.2.840.10008.1.2.4.90
```

Here's how to set it as .91:

```python
file_meta.TransferSyntaxUID = pydicom.uid.JPEG2000
```
