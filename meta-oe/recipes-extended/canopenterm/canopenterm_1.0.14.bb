
SUMMARY = "CANopenTerm is a versatile software tool to analyse and \
          configure CANopen devices. \
          "
DESCRIPTION = "CANopenTerm is an open-source software tool designed for the \
              development, testing, and analysis of CANopen CC networks and \
              devices.  It extends its capabilities to support other CAN CC \
              protocols, including SAE J1939 and OBD-II. \
              "
HOMEPAGE = "https://canopenterm.de"
BUGTRACKER = "https://github.com/CANopenTerm/CANopenTerm/issues"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=bd2edc721b4a0289efe949bdbe7dda79"

DEPENDS = "cjson libinih libsdl3 lua libsocketcan pocketpy alsa-lib"

SRC_URI = "git://github.com/CANopenTerm/CANopenTerm.git;protocol=https;branch=main;tag=v${PV}"

SRCREV  = "012e48af26b483ead9df1004cfc92e6908b66b24"

inherit cmake pkgconfig ptest

# Does not compile with clang yes
TOOLCHAIN = "gcc"

FILES:${PN} += "${bindir}/CANopenTerm ${bindir}/codb2json ${datadir}"

RDEPENDS:${PN} = "cjson libinih libsdl3 lua libsocketcan pocketpy"
