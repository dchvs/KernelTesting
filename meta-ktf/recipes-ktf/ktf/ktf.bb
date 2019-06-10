DESCRIPTION = "Kernel Test Framework"
HOMEPAGE = "https://github.com/dchvs/ktfx.git"
LICENSE = "GPL-2.0"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "r1"
DEPENDS += "libnl gtest"

BB_STRICT_CHECKSUM = "0"
LIC_FILES_CHKSUM = "file:///home/daniel/Downloads/googletest-master/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a"

SRCREV = "41e38ed61716007c8fc8ad13cf7f6695ed3149d3"
SRC_URI = " \
          git://github.com/dchvs/ktfx.git;protocol=git \
"

S = "${WORKDIR}/git"


EXTRA_OECMAKE += " \
   	   -GNinja \
		   -DARCH=arm \
		   -DCROSS_COMPILE=arm-poky-linux-gnueabi- \
		   -DCMAKE_INCLUDE_PATH=${WORKDIR}/recipe-sysroot/usr/include/libnl3 \
       -DKDIR=/home/daniel/KernelTesting/yocto-rpi3-qemu/tmp/work/qemuarm-poky-linux-gnueabi/linux-yocto/4.19.30+gitAUTOINC+55b7409654_3df49db2ea-r0/linux-qemuarm-standard-build \
"

inherit pkgconfig cmake


do_install() {
    ##KTF

    # include
    install -d ${D}${includedir}/ktf
    cp -R ${WORKDIR}/git/kernel/*.h ${D}${includedir}/ktf

    #ktf.ko
    install -d ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/
    install -m 0755 ${S}/kernel/ktf.ko ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/

    #lib/libktf.so
    install -d ${D}${libdir}
    cp -R ${WORKDIR}/build/shared/libk*.so* ${D}${libdir}

    # bin/ktfrun
    install -d ${D}${bindir}
    cp -R ${WORKDIR}/build/user/ktfrun ${D}${bindir}
    chrpath -d ${D}${bindir}/ktfrun

}

FILES_${PN} = " \
    ${includedir}/ktf \
    ${libdir}/libktf.so \
    ${bindir}/ktfrun \
    /lib/modules/4.19.30-yocto-standard/kernel/drivers/ktf.ko \
"
FILES_${PN}-dev = " \
"
FILES_${PN}-staticdev = " \
"

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale "

INSANE_SKIP_${PN} = "dev-deps"

#Dependencies
DEPENDS_${PN} = " libnl libnl-genl libktf libgmock libgtest libnl-3 libnl-genl-3"
RDEPENDS_${PN} = " python libnl libnl-genl gtest "
