#!/usr/bin/env bash
#
# Copyright (C) 2026 BULLET IT Solutions
# Bullet-OS Build Environment Setup
#

function bullet_banner() {
cat << "EOF"
  ____        _ _      _         ___  ____  
 | __ ) _   _| | | ___| |_      / _ \/ ___| 
 |  _ \| | | | | |/ _ \ __|____| | | \___ \ 
 | |_) | |_| | | |  __/ ||_____| |_| |___) |
 |____/ \__,_|_|_|\___|\__|     \___/|____/ 
                                             
 :: Pure Performance Android OS by BULLET IT Solutions ::
EOF
}

# Source standard AOSP envsetup if present
if [ -f "build/make/envsetup.sh" ]; then
    source build/make/envsetup.sh
fi

bullet_banner

function lunch_bullet() {
    local target="${1:-bullet_arm64-userdebug}"
    echo "[Bullet-OS] Preparing build target: $target"
    lunch "$target"
}

function make_bullet() {
    local jobs=$(nproc --all 2>/dev/null || echo 4)
    echo "[Bullet-OS] Building Bullet-OS image using $jobs parallel jobs..."
    m -j"$jobs" systemimage
}

echo "Type 'lunch_bullet bullet_arm64-userdebug' to select ARM64 GSI target."
echo "Type 'lunch_bullet bullet_x86_64-userdebug' to select PC Emulator target."
echo "Type 'make_bullet' to start compiling."
