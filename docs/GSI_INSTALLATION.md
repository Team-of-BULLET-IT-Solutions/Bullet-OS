# Bullet-OS GSI Installation Guide

Bullet-OS Generic System Image (GSI) can be flashed on **any Project Treble compatible Android phone** with an unlocked bootloader.

---

## Prerequisites

1. An unlocked bootloader on your device.
2. A PC with `adb` and `fastboot` drivers installed.
3. The compiled `system.img` from Bullet-OS.
4. Your device's stock `vbmeta.img` (recommended to disable Android Verified Boot).

---

## Installation Steps (Windows & Linux)

### Automated Flashing (Easiest Method)
1. Place `system.img` in the `build/tools/` directory.
2. If on Windows:
   - Double click `build\tools\flash-gsi.bat`.
3. If on Linux / macOS:
   - Run `bash build/tools/flash-gsi.sh`.

---

### Manual Fastboot Commands

```bash
# 1. Reboot your phone into Bootloader / Fastboot Mode
adb reboot bootloader

# 2. Reboot into FastbootD (Required for Android 10+ dynamic partitions)
fastboot reboot fastboot

# 3. Disable AVB / Verity Check
fastboot --disable-verity --disable-verification flash vbmeta vbmeta.img

# 4. Erase & Flash Bullet-OS System Partition
fastboot erase system
fastboot flash system system.img

# 5. Factory Reset / Wipe Userdata (Essential for first boot)
fastboot -w

# 6. Reboot into Bullet-OS
fastboot reboot
```

---

## Troubleshooting

- **Bootloop into Recovery:** Make sure you ran `fastboot -w` to format userdata. Stock encrypted userdata partitions from other OSes cannot be read directly without formatting.
- **Verification failed error:** Flash `vbmeta.img` with `--disable-verity --disable-verification`.
- **Dynamic partition error:** Ensure you are in `fastbootd` (the screen will say Fastbootd with yellow text on most phones) instead of legacy bootloader mode.
