# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,k
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
	ARCH_PREFIX := armv7-a
else ifeq ($(TARGET_ARCH_ABI),armeabi)
	ARCH_PREFIX := armv6
else ifeq ($(TARGET_ARCH_ABI),x86)
	ARCH_PREFIX := i686
else ifeq ($(TARGET_ARCH_ABI),mips)
	ARCH_PREFIX := mips32
else
	ARCH_PREFIX := $(TARGET_ARCH)
endif

SODIUM_LIB_DIR := $(LOCAL_PATH)/libsodium/libsodium-android-$(ARCH_PREFIX)
SODIUM_INC_DIR := $(SODIUM_LIB_DIR)/include
SODIUM_LIB := $(SODIUM_LIB_DIR)/lib/libsodium.a

LOCAL_MODULE := sodium
LOCAL_SRC_FILES := $(SODIUM_LIB)
LOCAL_EXPORT_C_INCLUDES := $(SODIUM_INC_DIR) $(SODIUM_INC_DIR)/sodium
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := sodiumjni
LOCAL_SRC_FILES := $(LOCAL_PATH)/sodium_wrap.c 
LOCAL_CFLAGS   += -Wall -g -pedantic -std=c99

LOCAL_STATIC_LIBRARIES := sodium 

include $(BUILD_SHARED_LIBRARY)

