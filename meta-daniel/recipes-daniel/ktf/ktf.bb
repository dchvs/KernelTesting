SUMMARY = "driver installation"
#HOMEPAGE = "http://www.jumpnowtek.com"
LICENSE = "HP"
#LICENSE = "MIT"
#LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#SRCREV = "152b8da9f88a674f4e2dcbd9e8b38c4c497241d9"
#SRC_URI = "git://github.com/scottellis/pytouch.git"


KERNEL_MODULE_AUTOLOAD += "led_driver"


S = "${WORKDIR}/git"


_TOPDIR="${TOPDIR}/.."
_KT=" ${_TOPDIR}"
#"/home/${USER}/KernelTesting"

_YOCTO_KERNEL_VERSION="yocto-4.14.98"
_KERNEL_DIR="${_KT}/yocto-rpi3/tmp/work/raspberrypi3-poky-linux-gnueabi/linux-raspberrypi/1_4.14.98+gitAUTOINC+5d63a4595d-r0/linux-raspberrypi3-standard-build/"


builddir = "${_KT}/build/yocto-4.14.98"
ktf_script_dir = "${_KT}/scripts/newsuite_scripts"

# // Framework paths on board
include_path = "/usr/include"
lib_path     = "/usr/lib"
bin_path     = "/usr/bin"
drivers_path  = "/lib/modules/4.14.98/kernel/drivers/"
#gtest_path   = "${builddir}/gtest/user"

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

# modules-path
modules-load_path = "/etc/modules-load.d/"
kvm  = "${_KT}"

_WORK="${_KT}/tmp"
_WORK_KTF="${_WORK}/ktf/work"

inherit autotools pkgconfig

#PACKAGES_append = " ${gtest_lib}/libgtest.so "

#PACKAGES_append = " ${PN}-gtest"

do_configure_prepend(){
    ${ktf_script_dir}/build_gtest.sh
    ${ktf_script_dir}/build_ktf.sh
}

do_compile(){
  cd ${_WORK_KTF}

  . /opt/poky/2.6.1/environment-setup-cortexa7t2hf-neon-vfpv4-poky-linux-gnueabi
  make
  make install
}

do_install() {
  # GTEST
    install -d ${D}${include_path}
    cp -R ${gtest_include}/* ${D}${include_path}
    install -d ${D}${lib_path}
    cp -R ${gtest_lib}/libgtest* ${D}${lib_path}
    install -d ${D}${lib_path}
    cp -R ${gtest_lib}/libgmock* ${D}${lib_path}

    # KTF
    install -d ${D}${include_path}
    cp -R ${ktf_include}/* ${D}${include_path}

    install -d ${D}${lib_path}
    cp -R ${ktf_lib}/libktf* ${D}${lib_path}

    install -d ${D}${bin_path}
    cp -R ${ktf_bin}/k* ${D}${bin_path}

    install -d ${D}${drivers_path}
    install -m 0755 ${ktf_kernel}/ktf.ko ${D}${drivers_path}

    # Load modules at boot
    install -d ${D}${modules-load_path}
    install -m 0755 ${kvm}/kvm.conf ${D}${modules-load_path}
    # /etc/modules-load.d/kvm.conf
}


FILES_${PN} = " \
		${drivers_path} \
		${bindir}   	\
		${bin_path} 	\
		${include_path}		 \
    /etc/modules-load.d/kvm.conf \
    ${lib_path}/libgmock_main.so \
    ${lib_path}/libgmock.so \
    ${lib_path}/libgtest_main.so \
    ${lib_path}/libgtest.so \  "

FILES_${PN}-dev = " \
		${lib_path}/libktf.so.0.0.0 \
		${lib_path}/libktf.so.0 \
		${lib_path}/libktf.so \ "

FILES_${PN}-staticdev = " \
      ${lib_path}/libgmock.a  \
      ${lib_path}/libgtest.a   \
      ${lib_path}/libktf.a \ "


# FILES_${PN}-gtest = "                       ${gtest_lib}/libgtest.so "
# ${PN}-gtest

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale "

INSANE_SKIP_${PN} += "dev-deps gtest"

RDEPENDS_${PN} = "python libnl-genl automake "


# Dependencies
#DEPENDS = "gtest"
#RDEPENDS_${PN}_append_gtest = "gtest"

#RDEPENDS_gtest = "python libnl-genl automake  "
