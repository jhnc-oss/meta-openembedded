DESCRIPTION = "ifplugd is a Linux daemon which will automatically configure your ethernet device \
when a cable is plugged in and automatically unconfigure it if the cable is pulled."
HOMEPAGE = "http://0pointer.de/lennart/projects/ifplugd/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "libdaemon"

SRC_URI = "http://0pointer.de/lennart/projects/ifplugd/ifplugd-${PV}.tar.gz \
           file://0001-src-interface.h-Make-declarations-as-extern.patch \
          "

SRC_URI:append:libc-musl = " file://Fix-build-with-musl.patch"

SRC_URI[sha256sum] = "474754ac4ab32d738cbf2a4a3e87ee0a2c71b9048a38bdcd7df1e4f9fd6541f0"

inherit autotools update-rc.d pkgconfig

EXTRA_OECONF = "--disable-lynx --with-initdir=${sysconfdir}/init.d"

INITSCRIPT_NAME = "ifplugd"
INITSCRIPT_PARAMS = "defaults"

CONFFILES:${PN} = "${sysconfdir}/ifplugd/ifplugd.conf"

RDEPENDS:${PN} += "bash"
