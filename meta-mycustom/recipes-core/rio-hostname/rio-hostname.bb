SUMMARY = "Set hostname from MAC address"
LICENSE = "CLOSED"

SRC_URI += "file://set-rio-hostname.sh \
            file://rio-hostname.service"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "rio-hostname.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/set-rio-hostname.sh ${D}${bindir}/

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/rio-hostname.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} += "${bindir}/set-rio-hostname.sh"
