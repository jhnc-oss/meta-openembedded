SUMMARY = "The dump DAQ test the various inline mode features "
HOMEPAGE = "http://www.snort.org"
SECTION = "libs"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=f9ce51a65dd738dc1ae631d8b21c40e0"

PARALLEL_MAKE = ""

DEPENDS = "libpcap libpcre libdnet bison-native libnetfilter-queue"

SRC_URI = "https://www.snort.org/downloads/snort/${BPN}-${PV}.tar.gz;downloadfilename=${BPN}-${PV}_snort_org.tar.gz \
           file://disable-run-test-program-while-cross-compiling.patch \
           file://0001-correct-the-location-of-unistd.h.patch \
           file://daq-fix-incompatible-pointer-type-error.patch \
           "
SRC_URI[sha256sum] = "d1f6709bc5dbddee3fdf170cdc1e49fb926e2031d4869ecf367a8c47efc87279"
# these 2 create undeclared dependency on libdnet and libnetfilter-queue from meta-networking
# this error from test-dependencies script:
# daq/daq/latest lost dependency on  libdnet libmnl libnetfilter-queue libnfnetlink
#
# never look to /usr/local lib while cross compiling

EXTRA_OECONF = "--enable-nfq-module --disable-ipq-module --includedir=${includedir} \
    --with-libpcap-includes=${STAGING_INCDIR} --with-dnet-includes=${STAGING_LIBDIR}"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)}"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"

inherit autotools multilib_script
MULTILIB_SCRIPTS += "${PN}:${bindir}/daq-modules-config "

DISABLE_STATIC = ""

BBCLASSEXTEND = "native"
