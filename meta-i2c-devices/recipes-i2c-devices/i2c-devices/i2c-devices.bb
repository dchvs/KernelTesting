SUMMARY = "Script to properly configure i2c-devs driver on Raspberry Pi 3 (BCM2837)"
HOMEPAGE = "https://github.com/dchvs/i2c-devs.git"
SECTION = "kernel"
LICENSE = "GPLv2"

PR = "r2"
DEPENDS += "libnl gtest ktf"
 
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "691c3eb60d7bf9a16cf8a20676dbdb3c3777633b"
SRC_URI = " git://github.com/dchvs/i2c-devs.git"

#PV = "1.1+git${SRCPV}"

S = "${WORKDIR}/git"


EXTRA_OECMAKE += " -DKERNEL_SRC=${STAGING_KERNEL_BUILDDIR}"

inherit pkgconfig cmake module-base kernel-module-split

do_install() {
    install -d ${D}${bindir}
    cp -R ${WORKDIR}/build/user/src/i2c_devs ${D}${bindir}

    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers
    cp -R ${S}/kernel/src/i2c-devs.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers

}

FILES_${PN} = " \
      ${bindir}/i2c_devs \
      /lib/modules/${KERNEL_VERSION}/kernel/drivers/i2c-devs.ko \
"

FILES_${PN}-dev = ""
FILES_${PN}-staticdev = ""
PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP_${PN} = " dev-deps"
RDEPENDS_${PN} = " python ktf"
