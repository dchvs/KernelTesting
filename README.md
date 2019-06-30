# KernelTesting

Yocto-Project distribution for [KTF](https://github.com/dchvs/ktf)  & [I²C devices](https://github.com/dchvs/i2c-devs) integration.

## Build

Clone repository:

```js
git clone --recursive git@github.com:dchvs/yocto-i2c-devs.git
```

### QEMU simulation
```js
cd layer/poky

<layers/poky>$ source oe-init-build-env ../../<yocto-rpi3-qemu>

bitbake <image>

runqemu qemuarm
```


### Raspberry Pi 3 image deploy
```js
cd layer/poky

<layers/poky>$ source oe-init-build-env ../../<yocto-rpi3>

bitbake <image>
```


image:
- core-image-base
