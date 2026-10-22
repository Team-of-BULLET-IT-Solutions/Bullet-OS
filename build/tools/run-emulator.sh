#!/usr/bin/env bash
#
# Copyright (C) 2026 BULLET IT Solutions
# Launch Bullet-OS x86_64 in QEMU with KVM
#

set -e

if [ ! -f "system.img" ]; then
    echo "[ERROR] system.img not found! Please compile or place Bullet-OS x86_64 image here."
    exit 1
fi

KVM_FLAG=""
if [ -w /dev/kvm ]; then
    KVM_FLAG="-enable-kvm"
    echo "[INFO] Hardware virtualization (KVM) enabled."
fi

qemu-system-x86_64 \
    $KVM_FLAG \
    -m 4096 \
    -smp 4 \
    -vga virtio \
    -display default,show-cursor=on \
    -device virtio-net,netdev=net0 \
    -netdev user,id=net0,hostfwd=tcp::5555-:5555 \
    -drive file=system.img,format=raw,if=virtio \
    -serial stdio
