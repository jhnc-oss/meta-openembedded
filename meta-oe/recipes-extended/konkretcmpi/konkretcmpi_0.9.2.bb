SUMMARY = "Tool for rapid CMPI providers development"
DESCRIPTION = "\
KonkretCMPI makes CMPI provider development easier by generating type-safe \
concrete CIM interfaces from MOF definitions and by providing default \
implementations for many of the provider operations."
HOMEPAGE = "https://github.com/rnovacek/konkretcmpi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=f673270bfc350d9ce1efc8724c6c1873"
DEPENDS:append:class-target = " swig-native sblim-cmpi-devel python3"
DEPENDS:append:class-native = " cmpi-bindings-native"

SRC_URI = "git://github.com/rnovacek/konkretcmpi.git;branch=master;protocol=https \
           file://0001-CMakeLists.txt-fix-lib64-can-not-be-shiped-in-64bit-.patch \
           file://0001-drop-including-rpath-cmake-module.patch \
           "

SRCREV = "ad28225e6eceff88417a60c1ba8896c8e40f21a7"

inherit cmake python3-dir

EXTRA_OECMAKE = "-DWITH_PYTHON=ON \
                 ${@oe.utils.conditional("libdir", "/usr/lib64", "-DLIB_SUFFIX=64", "", d)} \
                 ${@oe.utils.conditional("libdir", "/usr/lib32", "-DLIB_SUFFIX=32", "", d)} \
                "

do_install:append() {
    rm -rf ${D}${datadir}
}

PACKAGES =+ "${PN}-python"

RPROVIDES:${PN}-dbg += "${PN}-python-dbg"

FILES:${PN}-python = "${PYTHON_SITEPACKAGES_DIR}/konkretmof.py* ${PYTHON_SITEPACKAGES_DIR}/_konkretmof.so"

BBCLASSEXTEND = "native"
