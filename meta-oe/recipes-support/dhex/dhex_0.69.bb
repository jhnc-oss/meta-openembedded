SUMMARY = "dhex is a hex editor that includes a diff mode"
SECTION = "console/utils"
HOMEPAGE = "http://www.dettus.net/dhex/"

DEPENDS = "ncurses"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://README.txt;beginline=229;endline=241;md5=6f252a421b65bcecf624382ba3c899da"

SRC_URI = " \
	http://www.dettus.net/dhex/dhex_0.69.tar.gz \
	file://0001-Fix-function-declaration-conflict-error-with-empty-p.patch \
"
SRC_URI[sha256sum] = "52730bcd1cf16bd4dae0de42531be9a4057535ec61ca38c0804eb8246ea6c41b"

S = "${UNPACKDIR}/dhex_${PV}"

EXTRA_OEMAKE += "'CC=${CC}' 'LDFLAGS=${LDFLAGS}' 'CPPFLAGS=${CPPFLAGS}'"

do_compile() {
	oe_runmake
}

do_install() {
	install -m 0755 -d ${D}${bindir}
	install -m 0755 ${S}/dhex ${D}${bindir}/
}
