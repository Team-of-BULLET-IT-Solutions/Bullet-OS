#!/usr/bin/env bash
#
# Copyright (C) 2026 BULLET IT Solutions
# Bullet-OS Automated Build & Packaging Pipeline
#

set -e

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
TARGET="${1:-bullet_arm64-userdebug}"
OUT_DIR="${PROJECT_ROOT}/out/target/product"

echo "================================================="
echo "   Bullet-OS Automated Compilation Pipeline       "
echo "   Team BULLET IT Solutions                      "
echo "================================================="
echo "Target: $TARGET"
echo "Root:   $PROJECT_ROOT"

# Check required tools
for tool in git python3 zip; do
    if ! command -v "$tool" >/dev/null 2>&1; then
        echo "[ERROR] Missing required host tool: $tool"
        exit 1
    fi
done

# Initialize ccache for faster incremental builds
if command -v ccache >/dev/null 2>&1; then
    export USE_CCACHE=1
    export CCACHE_DIR="${HOME}/.ccache_bullet"
    ccache -M 50G
    echo "[INFO] ccache enabled at ${CCACHE_DIR}"
fi

# Source environment
source "${PROJECT_ROOT}/build/envsetup.sh"

echo "[INFO] Initializing lunch target: $TARGET"
lunch "$TARGET"

echo "[INFO] Compiling Bullet-OS system image..."
JOBS=$(nproc --all 2>/dev/null || echo 4)
m -j"$JOBS" systemimage

echo "[INFO] Packaging flashable artifacts..."
mkdir -p "${PROJECT_ROOT}/releases"

DEVICE_NAME=$(echo "$TARGET" | cut -d'-' -f1)
SYSTEM_IMG="${OUT_DIR}/${DEVICE_NAME}/system.img"

if [ -f "$SYSTEM_IMG" ]; then
    RELEASE_ZIP="${PROJECT_ROOT}/releases/BulletOS-1.0-${DEVICE_NAME}-$(date +%Y%m%d).zip"
    zip -j "$RELEASE_ZIP" "$SYSTEM_IMG"
    echo "[SUCCESS] Bullet-OS successfully compiled!"
    echo "[OUTPUT] System Image: $SYSTEM_IMG"
    echo "[OUTPUT] Release Package: $RELEASE_ZIP"
else
    echo "[WARNING] Build completed but system.img not found at $SYSTEM_IMG"
fi
