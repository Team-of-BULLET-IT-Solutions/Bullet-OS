#
# Copyright (C) 2026 BULLET IT Solutions
# Bullet-OS Version Definition
#

BULLET_VERSION_MAJOR := 1
BULLET_VERSION_MINOR := 0
BULLET_VERSION_PATCH := 0
BULLET_CODENAME := Kinetic

# Build Type: OFFICIAL, UNOFFICIAL, EXPERIMENTAL, NIGHTLY
ifndef BULLET_BUILD_TYPE
    BULLET_BUILD_TYPE := OFFICIAL
endif

BULLET_DATE := $(shell date -u +%Y%m%d)
BULLET_DISPLAY_VERSION := BulletOS-$(BULLET_VERSION_MAJOR).$(BULLET_VERSION_MINOR)-$(BULLET_CODENAME)-$(BULLET_DATE)-$(BULLET_BUILD_TYPE)

PRODUCT_SYSTEM_PROPERTIES += \
    ro.bullet.version=$(BULLET_DISPLAY_VERSION) \
    ro.bullet.releasetype=$(BULLET_BUILD_TYPE) \
    ro.bullet.build.date=$(BULLET_DATE) \
    ro.bullet.codename=$(BULLET_CODENAME) \
    ro.bullet.api_level=34 \
    ro.modversion=$(BULLET_DISPLAY_VERSION)
