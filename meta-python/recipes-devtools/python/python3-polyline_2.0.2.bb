SUMMARY = "A Python implementation of Google's Encoded Polyline Algorithm Format"
HOMEPAGE = "https://github.com/frederickjansen/polyline"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1fb8d1dc685695195bb3c1e48adfef48"

SRC_URI[sha256sum] = "10541e759c5fd51f746ee304e9af94744089a4055b6257b293b3afd1df64e369"

inherit pypi python_setuptools_build_meta ptest-python-pytest

RDEPENDS:${PN} += "python3-six"

BBCLASSEXTEND = "native nativesdk"

