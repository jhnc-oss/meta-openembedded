SUMMARY = "XML::SAX::Writer - SAX2 Writer"
DESCRIPTION = "\
XML::SAX::Writer helps to serialize SAX2 representations of XML documents to \
strings, files, and other flat representations. It handles charset encodings, \
XML escaping conventions, and so forth. It is still considered alpha, \
although it has been put to limited use in settings such as XML::LibXML and \
the AxKit XML Application Server. \
"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0-or-later"
HOMEPAGE = "https://metacpan.org/dist/XML-SAX-Writer"
DEPENDS += "libxml-filter-buffertext-perl-native"
RDEPENDS:${PN} += "libxml-filter-buffertext-perl"

SRC_URI = "${CPAN_MIRROR}/authors/id/P/PE/PERIGRIN/XML-SAX-Writer-${PV}.tar.gz"
SRC_URI[sha256sum] = "3d61d07ef43b0126f5b4de4f415a256fa859fa88dc4fdabaad70b7be7c682cf0"

LIC_FILES_CHKSUM = "file://README;beginline=45;endline=46;md5=d41d8cd98f00b204e9800998ecf8427e"

S = "${UNPACKDIR}/XML-SAX-Writer-${PV}"

inherit cpan

BBCLASSEXTEND = "native"
