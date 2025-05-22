SUMMARY = "Auto wifi ap and sta"
DESCRIPTION = "Script to connect wifi sta and ap"
LICENSE = "PD"
PR = "r3"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=1c3a7fb45253c11c74434676d84fe7dd"

SRC_URI = " \
    file://ifup_wifi_ap \
    file://COPYING \
    file://ifup_wifi_sta \
    file://myir_hostapd.conf \
    file://myir_hostapd.conf.tpl \
    file://myir_udhcpd.conf \
    file://wifi-ap.service \
"

inherit systemd

# Tell systemd which unit(s) this package provides
SYSTEMD_SERVICE:${PN} = "wifi-ap.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"


do_install () {
    install -d ${D}/usr/bin
		install -d ${D}/etc
		
    cp -r ${WORKDIR}/ifup_wifi*  ${D}/usr/bin
    cp -r ${WORKDIR}/myir_* ${D}/etc
    
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/wifi-ap.service ${D}${systemd_system_unitdir}/wifi-ap.service
}



#inherit allarch systemd

FILES:${PN} = "\
	     /usr/bin   \
	     /etc/myir_hostapd.conf \
	     /etc/myir_hostapd.conf.tpl \
	     /etc/myir_udhcpd.conf \
	     ${systemd_system_unitdir}/wifi-ap.service \
"
