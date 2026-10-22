#
# Copyright (C) 2026 BULLET IT Solutions
# SPDX-License-Identifier: Apache-2.0
#

LOCAL_PATH := $(call my-dir)

ifeq ($(BULLET_BUILD),true)
include $(call all-makefiles-under,$(LOCAL_PATH))
endif
