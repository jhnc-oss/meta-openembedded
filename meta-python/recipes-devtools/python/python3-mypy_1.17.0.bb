SUMMARY = "Optional static typing for Python 3 and 2 (PEP 484)"
HOMEPAGE = "https://github.com/python/mypy"
LICENSE = "MIT & Python-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8d62fd8f8648cb018e52857347e340b9"

inherit pypi python_setuptools_build_meta

SRC_URI[sha256sum] = "e5d7ccc08ba089c06e2f5629c660388ef1fee708444f1dee0b9203fa031dee03"

BBCLASSEXTEND = "native nativesdk"

DEPENDS += " \
    python3-mypy-extensions-native \
    python3-types-psutil-native \
    python3-types-setuptools-native \
    python3-typing-extensions-native \
    python3-pathspec-native \
"

RDEPENDS:${PN} += " \
    python3-modules \
    python3-mypy-extensions \
    python3-typing-extensions \
"
