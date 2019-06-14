SUMMARY = "Script to properly configure i2c-devs driver on Raspberry Pi 3 (BCM2837)"
HOMEPAGE = "https://github.com/dchvs/i2c-devs.git"
SECTION = "kernel"
LICENSE = "GPL"

PR = "r2"
DEPENDS += "libnl gtest ktf"

BB_STRICT_CHECKSUM = "0"
LIC_FILES_CHKSUM = "file:///home/daniel/Downloads/googletest-master/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a"

SRCREV = "468691509aa6781ee349f971ec134ca6b05ada36"
SRC_URI = "\
    git://github.com/dchvs/i2c-devs.git \
"

#PV = "1.1+git${SRCPV}"

S = "${WORKDIR}/git"

EXTRA_OECONF += "${STAGING_INCDIR}"

EXTRA_OECMAKE += " -DKERNEL_SRC=${STAGING_KERNEL_BUILDDIR} \
		               -DARCH=arm \
		               -DCROSS_COMPILE=arm-poky-linux-gnueabi- \
                   -DPATH2KTFINCLUDE=/home/${USER}/ktf \
"

inherit pkgconfig cmake module-base kernel-module-split


do_install() {
    install -d ${D}${bindir}
    cp -R ${WORKDIR}/build/user/src/i2c_devs ${D}${bindir}

    install -d ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers
    cp -R ${S}/kernel/src/i2c-devs.ko ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers

}

FILES_${PN} = " \
      ${bindir}/i2c_devs \
      /lib/modules/4.19.30-yocto-standard/kernel/drivers/i2c-devs.ko \
"

FILES_${PN}-dev = " \
"
FILES_${PN}-staticdev = " \
"
PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP_${PN} = " dev-deps"
RDEPENDS_${PN} = " python ktf"
