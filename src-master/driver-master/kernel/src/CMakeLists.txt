cmake_minimum_required (VERSION 3.1)

include(CPack)



set (EXTRA_CFLAGS ${CMAKE_CURRENT_SOURCE_DIR}/../include
                  ${CMAKE_CURRENT_SOURCE_DIR}/../../shared)

include_directories (${EXTRA_CFLAGS})

set(cmake_generated ${CMAKE_CURRENT_SOURCE_DIR}/${MODULENAME}.ko
                    ${CMAKE_CURRENT_SOURCE_DIR}/${MODULENAME}.mod.c
                    ${CMAKE_CURRENT_SOURCE_DIR}/${MODULENAME}.mod.o
                    ${CMAKE_CURRENT_SOURCE_DIR}/${MODULENAME}.o
                    ${CMAKE_CURRENT_SOURCE_DIR}/Module.symvers
                    ${CMAKE_CURRENT_SOURCE_DIR}/modules.order
                    ${CMAKE_CURRENT_SOURCE_DIR}/.${MODULENAME}.ko.cmd
                    ${CMAKE_CURRENT_SOURCE_DIR}/.${MODULENAME}.mod.o.cmd
                    ${CMAKE_CURRENT_SOURCE_DIR}/.${MODULENAME}.o.cmd
                    ${CMAKE_CURRENT_SOURCE_DIR}/.tmp_versions
)

set( KBUILD_CMD ${CMAKE_MAKE_PROGRAM}
                -C ${KDIR}
                M=${CMAKE_CURRENT_SOURCE_DIR} modules
   )

set ( kofile ${CMAKE_CURRENT_SOURCE_DIR}/${MODULENAME}.ko )
add_custom_command ( OUTPUT ${kofile}
                     COMMAND ${KBUILD_CMD}
                     WORKING_DIRECTORY ${WORKING_DIR}
	             DEPENDS Kbuild
	           )
add_custom_target ( driver ALL DEPENDS ${kofile} )


#install the binary
install (FILES ${CMAKE_CURRENT_SOURCE_DIR}/${DRIVERNAME}.ko DESTINATION bin)


## cleaning
# Clean in current source dir as it has been installed and work elsewhere


set (CLEAN REMOVE i2c.o)

	foreach(file ${cmake_generated})
		if (EXISTS ${file})
		file(REMOVE_RECURSE ${file})
		endif()
	endforeach(file)


#add_custom_command ( COMMAND rm i2c.o )
