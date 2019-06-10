SUMMARY = "Script to properly configure i2c-devs driver on Raspberry Pi 3 (BCM2837)"
HOMEPAGE = "https://github.com/dchvs/i2c-devs.git"
SECTION = "kernel"
LICENSE = "GPL"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "r1"
DEPENDS += "libnl gtest ktf"

BB_STRICT_CHECKSUM = "0"
LIC_FILES_CHKSUM = "file:///home/daniel/Downloads/googletest-master/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a"

SRCREV = "5015b58c45719f016596e87fc59c2f509b74af1d"
SRC_URI = "\
    git://github.com/dchvs/i2c-devs.git \
"

#PV = "1.1+git${SRCPV}"

S = "${WORKDIR}/git"


EXTRA_OECMAKE += " \
   	   -GNinja \
		   -DARCH=arm \
		   -DCROSS_COMPILE=arm-poky-linux-gnueabi- \
		   -DCMAKE_INCLUDE_PATH=${WORKDIR}/recipe-sysroot/usr/include \
       -DKDIR=/home/daniel/KernelTesting/yocto-rpi3-qemu/tmp/work/qemuarm-poky-linux-gnueabi/linux-yocto/4.19.30+gitAUTOINC+55b7409654_3df49db2ea-r0/linux-qemuarm-standard-build \
"

inherit pkgconfig cmake


do_install() {

    install -d ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/i2c-devs
    install -m 0755 ${S}/kernel/src/i2c-devs.ko ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/i2c-devs

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/build/user/src/i2c_devs ${D}${bindir}

}

FILES_${PN} = " \
		/lib/modules/4.19.30-yocto-standard/kernel/drivers/i2c-devs/i2c-devs.ko \
    ${bindir}/i2c_devs     \
"
FILES_${PN}-dev = " \
"
FILES_${PN}-staticdev = " \ "
PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP_${PN} = " dev-deps"
RDEPENDS_${PN} = " python ktf"
