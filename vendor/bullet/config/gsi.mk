#
# Copyright (C) 2026 BULLET IT Solutions
# Bullet-OS GSI (Generic System Image) Configuration
#

$(call inherit-product, $(SRC_TARGET_DIR)/product/generic_system.mk)
$(call inherit-product, vendor/bullet/config/common.mk)

PRODUCT_NAME := bullet_gsi
PRODUCT_DEVICE := generic
PRODUCT_BRAND := BULLET
PRODUCT_MODEL := Bullet-OS GSI ARM64

# Treble & System-As-Root settings
PRODUCT_FULL_TREBLE_OVERRIDE := true
PRODUCT_COMPATIBILITY_MATRIX := device/bullet/generic_arm64/compatibility_matrix.xml

# GSI Build configuration
PRODUCT_BUILD_SYSTEM_IMAGE := true
BOARD_SYSTEMIMAGE_PARTITION_RESERVED_SIZE := 104857600
BOARD_SYSTEMIMAGE_FILE_SYSTEM_TYPE := ext4
TARGET_USERIMAGES_USE_EXT4 := true
