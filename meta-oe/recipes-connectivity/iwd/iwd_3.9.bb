SUMMARY = "Wireless daemon for Linux"
HOMEPAGE = "https://iwd.wiki.kernel.org/"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=fb504b67c50331fc78734fed90fb0e09"

DEPENDS = "dbus"

SRC_URI = "https://www.kernel.org/pub/linux/network/wireless/${BP}.tar.xz \
           file://0001-build-Use-abs_top_srcdir-instead-of-abs_srcdir-for-e.patch \
           file://iwd \ 
           "
SRC_URI[sha256sum] = "0cd7dc9b32b9d6809a4a5e5d063b5c5fd279f5ad3a0bf03d7799da66df5cad45"

inherit autotools manpages pkgconfig python3native systemd update-rc.d

PACKAGECONFIG ??= " \
    client \
    monitor \
    ${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} \
"
PACKAGECONFIG[client] = "--enable-client,--disable-client,readline"
PACKAGECONFIG[monitor] = "--enable-monitor,--disable-monitor"
PACKAGECONFIG[manpages] = "--enable-manual-pages,--disable-manual-pages,python3-docutils-native"
PACKAGECONFIG[wired] = "--enable-wired,--disable-wired"
PACKAGECONFIG[ofono] = "--enable-ofono,--disable-ofono"
PACKAGECONFIG[systemd] = "--with-systemd-unitdir=${systemd_system_unitdir},--disable-systemd-service,systemd"

INITSCRIPT_NAME = "iwd"
INITSCRIPT_PARAMS = "start 04 5 2 3 . stop 23 0 1 6 ."

SYSTEMD_SERVICE:${PN} = " \
    iwd.service \
    ${@bb.utils.contains('PACKAGECONFIG', 'wired', 'ead.service', '', d)} \
"

do_configure:prepend() {
    install -d ${S}/build-aux
}

do_install:append() {
    # If client and monitor are disabled, bindir is empty, causing a QA error
    rmdir --ignore-fail-on-non-empty ${D}/${bindir}

    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${UNPACKDIR}/iwd ${D}${sysconfdir}/init.d/iwd
    fi
}

FILES:${PN} += " \
    ${datadir}/dbus-1 \
    ${nonarch_libdir}/modules-load.d \
    ${systemd_unitdir}/network \
"

RDEPENDS:${PN} = "${VIRTUAL-RUNTIME_dbus}"

RRECOMMENDS:${PN} = "\
    kernel-module-pkcs7-message \
    kernel-module-pkcs8-key-parser \
    kernel-module-x509-key-parser \
"
