FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://20-br0.netdev \
            file://30-br0.network \
            file://10-eth0.network \
            file://10-eth1.network \
            file://rio-hostname.service \
            file://set-rio-hostname.sh \
            "

do_install:append() {
    echo "Installing custom networkd bridge config"
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/20-br0.netdev ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/30-br0.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/10-eth0.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/10-eth1.network ${D}${sysconfdir}/systemd/network/

    # Optional: remove default ones if present
    rm -f ${D}${sysconfdir}/systemd/network/10-eth.network || true
    rm -f ${D}${sysconfdir}/systemd/network/15-eth.network || true
    
    echo "Change host name from eth0 mac address"
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/rio-hostname.service ${D}${systemd_system_unitdir}/
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/set-rio-hostname.sh ${D}${bindir}/
}

SYSTEMD_SERVICE:${PN} += "rio-hostname.service"
FILES:${PN} += "/usr/bin/set-rio-hostname.sh"

