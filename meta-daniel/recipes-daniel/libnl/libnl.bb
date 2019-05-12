SUMMARY = "driver installation"
#HOMEPAGE = "http://www.jumpnowtek.com"
LICENSE = "HP"
#LICENSE = "MIT"
#LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#SRCREV = "152b8da9f88a674f4e2dcbd9e8b38c4c497241d9"
#SRC_URI = "git://github.com/scottellis/pytouch.git"



S = "${WORKDIR}/git"

_TOPDIR="${TOPDIR}/.."
_KT="${_TOPDIR}"

_YOCTO_KERNEL_VERSION="yocto-4.14.98"
_KERNEL_DIR="${_KT}/yocto-rpi3/tmp/work/raspberrypi3-poky-linux-gnueabi/linux-raspberrypi/1_4.14.98+gitAUTOINC+5d63a4595d-r0/linux-raspberrypi3-standard-build/"


builddir="${_KT}/build/yocto-4.14.98"
driver_script_dir = "${_KT}/suite1"
ktf_script_dir = "${_KT}/scripts/newsuite_scripts"

do_compile () {
        ${ktf_script_dir}/build_lnl.sh
}

do_install() {

}

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"

INSANE_SKIP_${PN} += "dev-deps"
RDEPENDS_${PN} = "python libnl-genl"
