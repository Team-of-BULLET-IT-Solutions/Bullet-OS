#
# Copyright (C) 2026 BULLET IT Solutions
# Bullet-OS Core Product Definition
#

# Enable Bullet Build flag
BULLET_BUILD := true

# Include Versioning & Branding
include vendor/bullet/config/version.mk
include vendor/bullet/config/branding.mk

# Bullet Core Packages
PRODUCT_PACKAGES += \
    BulletLauncher \
    BulletSettings \
    BulletTerminal \
    BulletUpdater \
    bulletd \
    bootanimation.zip

# Essential Open-Source Utilities & Audio
PRODUCT_PACKAGES += \
    nano \
    bash \
    htop \
    toybox \
    e2fsprogs \
    curl \
    ca-certificates

# System Resource Overlays
PRODUCT_PACKAGE_OVERLAYS += \
    vendor/bullet/overlay/common

# Boot Animation
PRODUCT_COPY_FILES += \
    vendor/bullet/bootanimation/bootanimation.zip:$(TARGET_COPY_OUT_SYSTEM)/media/bootanimation.zip

# Bullet Daemon init script
PRODUCT_COPY_FILES += \
    vendor/bullet/services/bulletd/bulletd.rc:$(TARGET_COPY_OUT_SYSTEM)/etc/init/bulletd.rc

# Default System Properties for Performance & Battery Tuning
PRODUCT_SYSTEM_PROPERTIES += \
    dalvik.vm.dex2oat64.enabled=true \
    dalvik.vm.image-dex2oat-filter=speed \
    dalvik.vm.dex2oat-filter=speed \
    pm.dexopt.install=speed-profile \
    pm.dexopt.bg-dexopt=speed \
    debug.sf.latch_unsignaled=1 \
    debug.sf.enable_gl_backpressure=1 \
    ro.surface_flinger.has_wide_color_display=true \
    ro.surface_flinger.has_HDR_display=true
