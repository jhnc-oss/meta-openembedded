SUMMARY = "Port of http_parser to llvm's libFuzzer, and a rewrite of the HTTP parser used in Node.js"
DESCRIPTION = "llhttp is a HTTP/1.x parser generated from a bytecode-like \
               instruction set that is smaller and faster to maintain than \
               the legacy http_parser it replaces. It is used by restinio \
               (and Node.js itself) as the HTTP request/response parser."
HOMEPAGE = "https://github.com/nodejs/llhttp"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5e274d60596dd59be0a1d1b19af7978"

SRC_URI = "git://github.com/nodejs/llhttp.git;protocol=https;branch=main;tag=release/v${PV}"
SRCREV = "86b83a59786caebd581f38d613c64c9e8c52c79e"

inherit cmake

BBCLASSEXTEND = "native nativesdk"
