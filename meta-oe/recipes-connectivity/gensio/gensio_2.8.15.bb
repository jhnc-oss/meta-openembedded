SUMMARY = "A library to abstract stream I/O like serial port, TCP, telnet, etc"
HOMEPAGE = "https://github.com/cminyard/gensio"
LICENSE = "GPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    "

SRCREV = "5ffe39f4a247ff001661a4c283305a3abe823fe3"

SRC_URI = "git://github.com/cminyard/gensio;protocol=https;branch=2.8.x;tag=v${PV}"


inherit autotools

PACKAGECONFIG ??= "openssl tcp-wrappers"

PACKAGECONFIG[openssl] = "--with-openssl=${STAGING_DIR_HOST}${prefix},--without-openssl, openssl"
PACKAGECONFIG[tcp-wrappers] = "--with-tcp-wrappers,--without-tcp-wrappers, tcp-wrappers"
PACKAGECONFIG[swig] = "--with-swig,--without-swig, swig"

EXTRA_OECONF = "--without-python"

RDEPENDS:${PN} += "bash"

FILES:${PN}-staticdev += "${libexecdir}/gensio/${PV}/libgensio_*.a"
