SUMMARY = "DataIO Daemon"
DESCRIPTION = "Installs the precompiled DataIO daemon and its shared libraries"
LICENSE = "CLOSED"
PR = "r1"

SRC_URI = "file://dataio \
           file://dataio_16di \
           file://dataio_16do \
           file://libCIP.so \
           file://libENET_ENCAP.so \
           file://libNVDATA.so \
           file://libOpENer.so \
           file://libPLATFORM_GENERIC.so \
           file://libPOSIXPLATFORM.so \
           file://libSAMPLE_APP.so \
           file://libUtils.so \
           file://libdemo_cn_console.so \
           file://libopen62541.so \
           file://liboplkcn.so \
           file://libpn_dev.so \
           file://libprofinet.so \
           file://libserver_rio.so \
           file://PRU_16DI.out \
           file://PRU_16DO.out \
           file://dataio.service"

S = "${WORKDIR}"

do_install() {
    # Install the binary
    install -d ${D}${bindir}
    install -m 0755 ${S}/dataio_* ${D}${bindir}/

    # Install all libraries
    install -d ${D}${libdir}
    install -m 0755 ${S}/lib*.so ${D}${libdir}/

    # Optional: create symlinks if needed
    ln -s ${D}${libdir}/libopen62541.so ${D}${libdir}/libopen62541.so.1
    
    # Install PRU firmware
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 644 ${S}/PRU_*.out \
                   ${D}${nonarch_base_libdir}/firmware/
    

    # Install the systemd service
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/dataio.service ${D}${systemd_system_unitdir}/dataio.service
}

FILES:${PN} += "/usr/bin/dataio_* /usr/lib/*.so /usr/lib/*.so.* /lib/firmware/*.out ${systemd_system_unitdir}/dataio.service"
INSANE_SKIP:${PN} += "already-stripped"
INSANE_SKIP:${PN} += "arch"
RDEPENDS:${PN} += "libssl libcrypto"

# If you want to disable dev package
ALLOW_EMPTY:${PN}-dev = "1"
FILES:${PN}-dev = ""

# Enable systemd service
SYSTEMD_SERVICE:${PN} = "dataio.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
INSANE_SKIP:${PN} += "already-stripped"

inherit systemd



