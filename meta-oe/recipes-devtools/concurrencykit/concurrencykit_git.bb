DESCRIPTION = "Concurrency Kit provides a plethora of concurrency primitives, \
safe memory reclamation mechanisms and non-blocking data structures \
designed to aid in the design and implementation of high performance \
concurrent systems."

LICENSE = "BSD-2-Clause & Apache-2.0"
HOMEPAGE = "http://concurrencykit.org"
SECTION = "base"

PV = "0.5.1+git${SRCPV}"
SRCREV = "f97d3da5c375ac2fc5a9173cdd36cb828915a2e1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a0b24c1a8f9ad516a297d055b0294231"
SRC_URI = "git://github.com/concurrencykit/ck.git;branch=master;protocol=https \
           file://cross.patch \
"

S = "${WORKDIR}/git"

COMPATIBLE_HOST = "(arm|aarch64|i.86|x86_64|powerpc|powerpc64).*-linux*"

inherit autotools-brokensep

PLAT:powerpc64 = "ppc64"
PLAT:powerpc64le = "ppc64"
PLAT ?= "${HOST_ARCH}"

do_configure () {
    export PLATFORM=${PLAT}
    export COMPILER='gcc'
    ${S}/configure \
    --prefix=${prefix} \
    --includedir=${includedir} \
    --libdir=${libdir}
}

do_compile () {
    oe_runmake
}

do_install () {
    oe_runmake 'DESTDIR=${D}' install
}
