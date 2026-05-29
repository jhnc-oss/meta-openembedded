SUMMARY = "IPv6 router advertisement daemon"
DESCRIPTION = "radvd is the router advertisement daemon for IPv6. It \
listens to router solicitations and sends router \
advertisements as described in RFC 2461, Neighbor \
Discovery for IP Version 6 (IPv6). With these \
advertisements hosts can automatically configure their \
addresses and some other parameters. They also can \
choose a default router based on these advertisements."
HOMEPAGE = "https://github.com/radvd-project/radvd"
SECTION = "net"
DEPENDS = "flex-native bison-native libdaemon libbsd"

# License is BSD-Style (with advertising clause) but also has an additional 0th clause
LICENSE = "radvd"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=73ebbf7182ae996e65e8fadc9a8c45db"

SRCREV = "a8500f4035b52028c90b0a938dfe8cd65e38fb50"
SRC_URI = "git://github.com/radvd-project/radvd.git;branch=master;tag=v${PV};protocol=https \
           file://radvd.init \
           file://radvd.service \
           file://volatiles.03_radvd \
           file://radvd.default \
           file://radvd.conf \
           "

S = "${WORKDIR}/git"

UPSTREAM_CHECK_URI = "https://github.com/radvd-project/radvd/releases"
UPSTREAM_CHECK_REGEX = "releases/tag/v(?P<pver>\d+(\.\d+)+)"

inherit autotools useradd pkgconfig systemd

SYSTEMD_SERVICE:${PN} = "radvd.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append () {
    install -m 0755 -d ${D}${sysconfdir}/default
    install -m 0644 ${UNPACKDIR}/radvd.conf ${D}${sysconfdir}/radvd.conf
    install -m 0644 ${UNPACKDIR}/radvd.default ${D}${sysconfdir}/default/radvd

    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -m 0755 -d ${D}${sysconfdir}/init.d \
            ${D}${sysconfdir}/default/volatiles

        install -m 0755 ${UNPACKDIR}/radvd.init ${D}${sysconfdir}/init.d/radvd
        sed -i 's!/usr/sbin/!${sbindir}/!g' ${D}${sysconfdir}/init.d/radvd
        sed -i 's!/etc/!${sysconfdir}/!g' ${D}${sysconfdir}/init.d/radvd
        sed -i 's!/var/!${localstatedir}/!g' ${D}${sysconfdir}/init.d/radvd
        sed -i 's!^PATH=.*!PATH=${base_sbindir}:${base_bindir}:${sbindir}:${bindir}!' ${D}${sysconfdir}/init.d/radvd

        install -m 0644 ${UNPACKDIR}/volatiles.03_radvd ${D}${sysconfdir}/default/volatiles/03_radvd
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -m 0755 -d ${D}${systemd_unitdir}/system

        install -m 0644 ${UNPACKDIR}/radvd.service ${D}${systemd_unitdir}/system
        sed -i -e 's#@SYSCONFDIR@#${sysconfdir}#g' \
            -e 's#@SBINDIR@#${sbindir}#g' \
            ${D}${systemd_unitdir}/system/radvd.service
    fi

    # Documentation
    install -m 0755 -d ${D}${docdir}/radvd
    for i in radvd.conf.example README; do \
        install -m 0644 ${S}/$i ${D}${docdir}/radvd; \
    done
}

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "--system nogroup"
USERADD_PARAM:${PN} = "--system --home ${localstatedir}/run/radvd/ -M -g nogroup --shell /sbin/nologin radvd"

pkg_postinst:${PN} () {
    if [ -z "$D" -a -x /etc/init.d/populate-volatile.sh ]; then
        /etc/init.d/populate-volatile.sh update
    fi
}
