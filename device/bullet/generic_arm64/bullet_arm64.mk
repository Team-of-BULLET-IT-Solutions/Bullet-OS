#
# Copyright (C) 2026 BULLET IT Solutions
# Product specification for bullet_arm64 GSI
#

$(call inherit-product, $(SRC_TARGET_DIR)/product/generic_system.mk)
$(call inherit-product, vendor/bullet/config/common.mk)

PRODUCT_NAME := bullet_arm64
PRODUCT_DEVICE := generic_arm64
PRODUCT_BRAND := BULLET
PRODUCT_MODEL := Bullet-OS ARM64 GSI
PRODUCT_MANUFACTURER := BULLET-IT-Solutions

# Treble GSI Support Flags
PRODUCT_SHIPPING_API_LEVEL := 34
PRODUCT_TARGET_VNDK_VERSION := current
