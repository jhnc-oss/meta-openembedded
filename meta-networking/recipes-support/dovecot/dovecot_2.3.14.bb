SUMMARY = "Dovecot is an open source IMAP and POP3 email server"
HOMEPAGE = "https://www.dovecot.org/"
DESCRIPTION = "Dovecot is an open source IMAP and POP3 email server for Linux/UNIX-like systems, written with security primarily in mind. Dovecot is an excellent choice for both small and large installations. It's fast, simple to set up, requires no special administration and it uses very little memory."
SECTION = "mail"
LICENSE = "LGPL-2.1-only & MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=2956560272e5b31d9d64f03111732048"

SRC_URI = "http://dovecot.org/releases/2.3/dovecot-${PV}.tar.gz \
           file://0001-configure.ac-convert-AC_TRY_RUN-to-AC_TRY_LINK-state.patch \
           file://dovecot.service \
           file://dovecot.socket \
           file://0001-not-check-pandoc.patch \
           file://0001-m4-Check-for-libunwind-instead-of-libunwind-generic.patch \
           "

SRC_URI[md5sum] = "2f03532cec3280ae45a101a7a55ccef5"
SRC_URI[sha256sum] = "c8b3d7f3af1e558a3ff0f970309d4013a4d3ce136f8c02a53a3b05f345b9a34a"

DEPENDS = "openssl xz zlib bzip2 libcap icu libtirpc bison-native"
CFLAGS += "-I${STAGING_INCDIR}/tirpc"
LDFLAGS += "-ltirpc"

inherit autotools pkgconfig systemd useradd gettext

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ldap pam', d)}"

PACKAGECONFIG[pam] = "--with-pam,--without-pam,libpam,"
PACKAGECONFIG[ldap] = "--with-ldap=plugin,--without-ldap,openldap,"
PACKAGECONFIG[lz4] = "--with-lz4,--without-lz4,lz4,"

# From native build in armv7a-hf/eglibc
CACHED_CONFIGUREVARS += "i_cv_signed_size_t=no \
                         i_cv_gmtime_max_time_t=32 \
                         i_cv_signed_time_t=yes \
                         i_cv_mmap_plays_with_write=yes \
                         i_cv_fd_passing=yes \
                         i_cv_c99_vsnprintf=yes \
                         lib_cv___va_copy=yes \
                         lib_cv_va_copy=yes \
                         lib_cv_va_val_copy=yes \
                        "

# hardcode epoll() to avoid running unsafe tests
# BSD needs kqueue and uclibc poll()
EXTRA_OECONF = " --with-ioloop=epoll \
                 --with-systemdsystemunitdir=${systemd_unitdir}/system"

# Uses hidden symbols
# libssl_iostream_openssl.so: undefined reference to `ssl_iostream_handshake'
LTO = ""

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "dovecot.service dovecot.socket"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append () {
    install -d 755 ${D}/etc/dovecot
    touch 644 ${D}/etc/dovecot/dovecot.conf
    install -m 0644 ${WORKDIR}/dovecot.service ${D}${systemd_unitdir}/system
    sed -i -e 's#@SYSCONFDIR@#${sysconfdir}#g' ${D}${systemd_unitdir}/system/dovecot.service
    sed -i -e 's#@SBINDIR@#${sbindir}#g' ${D}${systemd_unitdir}/system/dovecot.service
}

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -d ${libexecdir} -M -s ${base_sbindir}/nologin -g dovecot dovecot; \
                      -r -d ${libexecdir} -M -s ${base_sbindir}/nologin -g dovenull dovenull"
GROUPADD_PARAM:${PN} = "-f -r dovecot;-f -r dovenull"

FILES:${PN} += "${libdir}/dovecot/*plugin.so \
                ${libdir}/dovecot/libfs_compress.so \
                ${libdir}/dovecot/libssl_iostream_openssl.so"
FILES:${PN}-staticdev += "${libdir}/dovecot/*/*.a"
FILES:${PN}-dev += "${libdir}/dovecot/libdovecot*.so"
FILES:${PN}-dbg += "${libdir}/dovecot/*/.debug"

# CVE-2016-4983 affects only postinstall script on specific distribution
CVE_CHECK_IGNORE += "CVE-2016-4983"
