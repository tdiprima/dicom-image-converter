#!/bin/bash
#
# Description: Converts jpg to png, then png to jp2.
# Author: tdiprima
# Date: 2024-02-06
#

# Convert the JPG file to PNG format using ImageMagick
convert input_image.jpg input_image.png

# Compress the PNG file to JP2 format using opj_compress
opj_compress -i input_image.png -o output_image.jp2
