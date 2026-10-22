#
# Copyright (C) 2026 BULLET IT Solutions
# BoardConfig for Bullet-OS Generic ARM64 GSI (Treble Compliant)
#

include build/make/target/board/generic_arm64/BoardConfig.mk

# Architecture
TARGET_ARCH := arm64
TARGET_ARCH_VARIANT := armv8-a
TARGET_CPU_ABI := arm64-v8a
TARGET_CPU_VARIANT := generic

# 2nd Architecture (32-bit compatibility)
TARGET_2ND_ARCH := arm
TARGET_2ND_ARCH_VARIANT := armv7-a-neon
TARGET_2ND_CPU_ABI := armeabi-v7a
TARGET_2ND_CPU_VARIANT := generic

# System Image & Dynamic Partitions
BOARD_USES_GENERIC_KERNEL_IMAGE := true
BOARD_SYSTEMIMAGE_PARTITION_RESERVED_SIZE := 268435456
BOARD_SYSTEMIMAGE_FILE_SYSTEM_TYPE := ext4
TARGET_USERIMAGES_USE_EXT4 := true

# Treble GSI compliance
BOARD_VNDK_VERSION := current
PRODUCT_FULL_TREBLE_OVERRIDE := true
BOARD_BUILD_SYSTEM_ROOT_IMAGE := false
