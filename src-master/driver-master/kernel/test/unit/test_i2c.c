#include "test_i2c.h"

#include "ktf/ktf.h"

#include "shared.h"

#include "gpio.h"
#include "i2c.h"
#include "lcd.h"
#include "rtc.h"


TEST(SUCCESS_FAILURE, I2C_TEST) {
    int ret = 0 ;

    int rand_pin = (int)prandom_u32_max(40) ;  // pin range
    int rand_state = (int)prandom_u32_max(1) ; // logical states range

    int rand_bit = (int)prandom_u32_max(32) ;
    int rand_bit_value = (int)prandom_u32_max(1) ;
    uint32_t rand_i2c_register = 0 ; //i2cRegsp->I2C_CTRL ; // aletorizarlo

    uint8_t rand_addr = (int)prandom_u32_max(128) ;
    int rand_reg = (int)prandom_u32_max(10) ; // ten registers
    int rand_block_size = (int)prandom_u32_max(3) ; // reading 3 registers

    uint8_t rand_byte = (uint8_t)prandom_u32_max(255) ;
    uint8_t rand_datalength = (uint8_t)prandom_u32_max(255) ;

    uint rand_enable = (int)prandom_u32_max(1) ;
    uint rand_transfermode = (int)prandom_u32_max(1) ;

    // COMO HACER ESTA TUPLA MAS DINAMICA
    int rand_size = 3 ;
    uint8_t rand_content[3] = {(uint8_t)prandom_u32_max(255),
                             (uint8_t)prandom_u32_max(255),
                             (uint8_t)prandom_u32_max(255)} ;

//-----------------------------------------
//------------ Success cases --------------
//-----------------------------------------

    ret = i2c_enable(rand_enable) ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_clearfifo() ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_transfermode(rand_transfermode) ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_slaveaddress(rand_addr) ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_fifo(rand_byte) ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_datalength(rand_datalength) ;
    EXPECT_INT_EQ(0, ret) ;

    // COMO CHEQUEAR ESTA FUNC. QUE DEPENDE DEL HARDWARE
    //ret = i2c_check_ack() ;
    //EXPECT_INT_EQ(0, ret) ;

    //ret = i2c_wait_tdone() ;
    //EXPECT_INT_EQ(0, ret) ;

    ret = i2c_startTransfer() ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_init() ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_read_block(rand_addr, rand_reg, rand_block_size) ;
    EXPECT_INT_EQ(0, ret) ;

    ret = i2c_write_block(rand_addr, rand_size, rand_content) ;
    EXPECT_INT_EQ(0, ret) ;

//-----------------------------------------
// ------------ Error cases --------------
//-----------------------------------------
    int rand_enable_fail = (int)get_random_int() | 0x2 ;
    int rand_transfermode_fail = (int)get_random_int() | 0x2 ;
    int rand_addr_fail = (int)get_random_int() | 0xff ; // addr is 7 bits, so 8 bits Or
    int rand_byte_fail = (int)get_random_int() | 0x1ff ;
    int rand_datalength_fail = (int)get_random_int() | 0x1ffff ;

    int rand_reg_fail = (int)get_random_int() | 0x1ff ;
    int rand_block_size_fail = (int)get_random_int() | 0x1ff ;
    int rand_size_fail = (int)get_random_int() | 0x1ff ;
    //uint8_t *rand_content_fail = {(int)get_random_int() | 0x1ff };


    ret = i2c_enable(rand_enable_fail) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;

    // Parametro void
    // ret = i2c_clearfifo() ;
    // EXPECT_INT_EQ(0, ret) ;

    ret = i2c_transfermode(rand_transfermode_fail) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;

    ret = i2c_slaveaddress(rand_addr_fail) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;

    ret = i2c_fifo(rand_byte_fail) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;

    ret = i2c_datalength(rand_datalength_fail) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;

    // COMO CHEQUEAR ESTA FUNC. QUE DEPENDE DEL HARDWARE
    //ret = i2c_check_ack() ;
    //EXPECT_INT_EQ(0, ret) ;

    //ret = i2c_wait_tdone() ;
    //EXPECT_INT_EQ(0, ret) ;

    // ret = i2c_startTransfer() ;
    // EXPECT_INT_EQ(0, ret) ;

    // ret = i2c_init() ;
    // EXPECT_INT_EQ(0, ret) ;

    ret = i2c_read_block(rand_addr_fail, rand_reg, rand_block_size) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;
    ret = i2c_read_block(rand_addr, rand_reg_fail, rand_block_size) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;
    ret = i2c_read_block(rand_addr, rand_reg, rand_block_size_fail) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;


    ret = i2c_write_block(rand_addr_fail, rand_size, rand_content) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;
    ret = i2c_write_block(rand_addr, rand_size_fail, rand_content) ;
    EXPECT_INT_EQ(-ERANGE, ret) ;
}


void add_test_i2c(void){
    ADD_TEST(I2C_TEST) ;
}
