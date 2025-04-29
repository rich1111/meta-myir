FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://20-br0.netdev \
            file://30-br0.network \
            file://10-eth0.network \
            file://10-eth1.network \
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
}

