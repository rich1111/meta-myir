SUMMARY = "ISC DHCP Client (dhclient)"
DESCRIPTION = "The ISC DHCP client for dynamically assigning IP addresses."
HOMEPAGE = "https://www.isc.org/dhcp/"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c463f4afde26d9eb60f14f50aeb85f8f"

DEPENDS = "openssl libcap zlib"

SRC_URI = "https://ftp.isc.org/isc/dhcp/4.4.3/dhcp-4.4.3.tar.gz \
	file://dhclient.conf \
	file://dhclient-script \
          "
SRC_URI[sha256sum] = "0e3ec6b4c2a05ec0148874bcd999a66d05518378d77421f607fb0bc9d0135818"

S = "${WORKDIR}/dhcp-4.4.3"

inherit autotools-brokensep

EXTRA_OECONF += "--with-randomdev=/dev/random"

do_configure:prepend() {
    autoreconf -fi
}

do_install:append() {
    install -d ${D}${sbindir}
    install -m 0755 ${B}/client/dhclient ${D}${sbindir}/dhclient
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/dhclient.conf ${D}${sysconfdir}/dhclient.conf
    install -d ${D}/sbin/; \
    install -m 0755 ${WORKDIR}/dhclient-script ${D}/sbin/; \
    install -d ${D}/var/db
    touch ${D}/var/db/dhclient.leases
}

FILES:${PN} += "${sbindir}/dhclient ${sysconfdir}/dhclient.conf /var/db/dhclient.leases"
