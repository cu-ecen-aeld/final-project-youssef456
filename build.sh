#!/bin/bash

# Script to build image for qemu.

# Author: Siddhant Jajoo.

git submodule init
git submodule sync
git submodule update

# local.conf won't exist until this step on first execution
source poky/oe-init-build-env

CONFLINE="MACHINE = \"qemuarm64\""

# Check if the CONFLINE already exists in local.conf
if ! grep -q "${CONFLINE}" conf/local.conf; then
    echo "Append ${CONFLINE} in the local.conf file"
    echo ${CONFLINE} >> conf/local.conf
else
    echo "${CONFLINE} already exists in the local.conf file"
fi

# Check if meta-aesd layer is already added
##bitbake-layers show-layers | grep "meta-aesd" > /dev/null
##layer_aesd_info=$?

##if [ $layer_aesd_info -ne 0 ]; then
##    echo "Adding meta-aesd layer"
##    bitbake-layers add-layer ../meta-aesd
##else
##    echo "meta-aesd layer already exists"
##fi

# Check if RTOS-layer is already added
bitbake-layers show-layers | grep "rtos" > /dev/null
layer_rtos_info=$?

if [ $layer_rtos_info -ne 0 ]; then
    echo "Adding rtos_layer"
    bitbake-layers add-layer ../rtos
else
    echo "rtos_layer already exists"
fi

set -e
bitbake core-image-rtos
### Build both core-image-aesd and your RTOS-layer image
##bitbake core-image-aesd core-image-rtos

