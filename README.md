# KernelTesting

## Installation

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


image are: 
	* core-image-base 
