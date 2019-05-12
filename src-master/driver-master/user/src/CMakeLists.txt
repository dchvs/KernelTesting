cmake_minimum_required (VERSION 2.6)

set(SRC_NAME ${MODULENAME})

# Include directories
include_directories (${CMAKE_CURRENT_SOURCE_DIR}/../include
                      ${CMAKE_CURRENT_SOURCE_DIR}/../../shared)
# Add the executable
add_executable (${SRC_NAME} ${SRC_NAME}.c)

# Link required libraries
target_link_libraries(${SRC_NAME}
  "-I${CMAKE_CURRENT_SOURCE_DIR}/../include -I${CMAKE_CURRENT_SOURCE_DIR}/../../shared"
  ${CMAKE_CURRENT_SOURCE_DIR}/i2c.c
  ${CMAKE_CURRENT_SOURCE_DIR}/rtc.c
  ${CMAKE_CURRENT_SOURCE_DIR}/lcd.c)


# Install the binary
install (TARGETS ${SRC_NAME} DESTINATION bin)

include(CPack)
