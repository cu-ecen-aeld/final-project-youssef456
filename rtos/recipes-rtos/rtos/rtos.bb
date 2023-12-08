SUMMARY = "rtos-layer recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/youssef456/RTOS_Embedded_Linux.git;protocol=ssh;branch=main"

SRCREV = "9cd54a384a1b80adf18642acc801e9fa74517983"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

FILES:${PN} += "${bindir}/rtos"
FILES:${PN} += "${sysconfdir}/init.d/rtos-start-stop.sh"

TARGET_LDFLAGS += "-pthread -lrt"

# Startup
inherit update-rc.d
INSANE_SKIP:${PN} += "ldflags"
INITSCRIPT_PACKAGES = "${PN}"

# Skip creating debug information for the rtos binary
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INITSCRIPT_NAME = "rtos-start-stop.sh"

do_compile() {
    oe_runmake 'CFLAGS=${CFLAGS}' 'LDFLAGS=${LDFLAGS}'
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${bindir}
    install -m 0755 ${S}/rtos ${D}${bindir}/
    install -m 0755 ${S}/rtos-start-stop.sh ${D}${sysconfdir}/init.d/
}

