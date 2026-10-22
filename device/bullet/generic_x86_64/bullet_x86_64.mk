#
# Copyright (C) 2026 BULLET IT Solutions
# Product specification for bullet_x86_64 Emulator
#

$(call inherit-product, $(SRC_TARGET_DIR)/product/generic_system.mk)
$(call inherit-product, vendor/bullet/config/common.mk)

PRODUCT_NAME := bullet_x86_64
PRODUCT_DEVICE := generic_x86_64
PRODUCT_BRAND := BULLET
PRODUCT_MODEL := Bullet-OS x86_64 Emulator
PRODUCT_MANUFACTURER := BULLET-IT-Solutions
