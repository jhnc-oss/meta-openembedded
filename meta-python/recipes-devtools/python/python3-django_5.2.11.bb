require python3-django.inc
inherit python_setuptools_build_meta

SRC_URI[sha256sum] = "7f2d292ad8b9ee35e405d965fbbad293758b858c34bbf7f3df551aeeac6f02d3"

RDEPENDS:${PN} += "\
    python3-sqlparse \
    python3-asgiref \
"
