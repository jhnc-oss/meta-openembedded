#
# Copyright (C) 2014 - 2017 Wind River Systems, Inc.
#
SUMMARY = "Base policy for CFEngine"

DESCRIPTION = "CFEngine is an IT infrastructure automation framework \
that helps engineers, system administrators and other stakeholders \
in an IT system to manage and understand IT infrastructure throughout \
its lifecycle. CFEngine takes systems from Build to Deploy, Manage and Audit. \
 \
This package is intended to provide a stable base policy for \
installations and upgrades, and is used by CFEngine 3.6 and newer. \
 \
The contents of this packge are intended to live in `/var/cfengine/masterfiles` \
or wherever `$(sys.masterdir)` points. \
"

HOMEPAGE = "http://cfengine.com"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=22b3189b9b79f1de35860dcc299bb6bc"

SRC_URI = "https://cfengine-package-repos.s3.amazonaws.com/tarballs/${BP}.tar.gz \
           "
SRC_URI[sha256sum] = "fc8b0ad20e803e0d1e9dcda7afb2f3f5110357ac0e553ed95cf2cbea9652451d"

UPSTREAM_CHECK_URI = "https://github.com/cfengine/masterfiles/releases"
UPSTREAM_CHECK_REGEX = "(?P<pver>\d+(\.\d+)+)"

inherit autotools

export EXPLICIT_VERSION = "${PV}"

EXTRA_OECONF = "--prefix=${datadir}/cfengine"

do_install:append() {
    rm -rf ${D}${datadir}/cfengine/modules/packages/zypper ${D}${datadir}/cfengine/modules/packages/yum
}

FILES:${PN} = "${datadir}/cfengine"

RDEPENDS:${PN} += "python3-core"
