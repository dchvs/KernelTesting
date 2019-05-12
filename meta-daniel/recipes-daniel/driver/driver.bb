SUMMARY = "driver installation"
#HOMEPAGE = "http://www.jumpnowtek.com"
LICENSE = "HP"
#LICENSE = "MIT"
#LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#SRCREV = "152b8da9f88a674f4e2dcbd9e8b38c4c497241d9"
#SRC_URI = "git://github.com/scottellis/pytouch.git"


module="led_driver"
app="led_driver_app"

S = "${WORKDIR}/git"

_TOPDIR="${TOPDIR}/.."
_KT="${_TOPDIR}"

_YOCTO_KERNEL_VERSION="yocto-4.14.98"
_KERNEL_DIR="${_KT}/yocto-rpi3/tmp/work/raspberrypi3-poky-linux-gnueabi/linux-raspberrypi/1_4.14.98+gitAUTOINC+5d63a4595d-r0/linux-raspberrypi3-standard-build/"


builddir="${_KT}/build/yocto-4.14.98"
driver_script_dir = "${_KT}/suite1"
ktf_script_dir = "${_KT}/scripts/newsuite_scripts"

# // Framework paths on board
include_path = "/usr/include"
lib_path     = "/usr/lib"
bin_path     = "/usr/bin"
drivers_path  = "/lib/modules/4.14.98/kernel/drivers/"

# GTEST
gtest_include = "${builddir}/gtest/usr/include"
gtest_lib     = "${builddir}/gtest/usr/lib"
# KTF
ktf_include = "${builddir}/ktf/usr/include/ktf"
ktf_lib     = "${builddir}/ktf/usr/lib"
ktf_bin     = "${builddir}/ktf/usr/bin"
ktf_kernel  = "${builddir}/ktf/usr/kernel"
# Driver
driver_build_dir = "${builddir}/driver/usr/bin"


do_compile () {
    ${ktf_script_dir}/build_driver.sh
}

do_install() {

  # Driver
  install -d ${D}${drivers_path}/daniels-i2c
  install -m 0755 ${driver_build_dir}/${module}.ko ${D}${drivers_path}/daniels-i2c

  # App
  install -d ${D}${bindir}
  install -m 0755 ${driver_build_dir}/${app}	 ${D}${bindir}

}


FILES_${PN} = " \
		${drivers_path} \
		${bindir}   	\
		${bin_path} 	\
		${include_path}		 \ "
FILES_${PN}-dev = " "

FILES_${PN}-staticdev = " \ "

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP_${PN} += "dev-deps"
RDEPENDS_${PN} = "python "
