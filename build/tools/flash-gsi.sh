#!/usr/bin/env bash
#
# Copyright (C) 2026 BULLET IT Solutions
# Bullet-OS GSI Fastboot Flasher for Linux / macOS
#

set -e

echo "======================================================="
echo "         BULLET-OS GSI FASTBOOT FLASHER               "
echo "        Engineered by BULLET IT Solutions              "
echo "======================================================="

if [ ! -f "system.img" ]; then
    echo "[ERROR] system.img not found! Please place your compiled Bullet-OS image here."
    exit 1
fi

echo "[1/5] Checking connected fastboot devices..."
fastboot devices

echo "[2/5] Rebooting to fastbootd (Dynamic Partitions)..."
fastboot reboot fastboot
sleep 4

if [ -f "vbmeta.img" ]; then
    echo "[3/5] Disabling AVB verification..."
    fastboot --disable-verity --disable-verification flash vbmeta vbmeta.img
fi

echo "[4/5] Flashing Bullet-OS system image..."
fastboot erase system
fastboot flash system system.img

read -p "Format userdata for clean install? (y/n): " wipe_choice
if [ "$wipe_choice" = "y" ] || [ "$wipe_choice" = "Y" ]; then
    echo "[INFO] Wiping userdata..."
    fastboot -w
fi

echo "[5/5] Flashing complete! Rebooting into Bullet-OS..."
fastboot reboot
