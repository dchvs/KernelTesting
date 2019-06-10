SUMMARY = "driver installation"
#HOMEPAGE = "http://www.jumpnowtek.com"
LICENSE = "MIT"

_TOPDIR="${TOPDIR}/.."
_KT="${_TOPDIR}"

# modules-path
modules-load_path = "/etc/modules-load.d/"

boot_load_file_path = "${_KT}"
boot_load_file  = "modules_boot_load"

inherit pkgconfig


do_configure_prepend(){
    true
    echo "do_configure_prepend"
}
do_compile(){
  true
  echo "do_configure"
}

do_install() {

    #Loadmodulesatboot(/etc/modules-load.d/modules_boot_load.conf)
    install -d ${D}${modules-load_path}
    install -m 0755 ${boot_load_file_path}/${boot_load_file}.conf ${D}${modules-load_path}
}

FILES_${PN} += " \
		${modules-load_path} \
		${boot_load_file_path}/modules_boot_load.conf \ "
FILES_${PN}-dev += " \ "

FILES_${PN}-staticdev += " \  "

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP_${PN} = "dev-deps"
RDEPENDS_${PN} = " python "
