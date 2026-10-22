# Bullet-OS Architecture Specification

Bullet-OS is an open-source, performance-focused mobile operating system based on the **Android Open Source Project (AOSP)** and the **Linux Kernel**, created and maintained by **BULLET IT Solutions**.

---

## 1. System Layers

```
+-------------------------------------------------------------+
|                      Bullet-OS Applications                 |
|  BulletLauncher | BulletSettings (Bullet Lab) | BulletShell |
+-------------------------------------------------------------+
|                      Application Framework                  |
|  ActivityManager | WindowManager | PackageManager | Telephony|
+-------------------------------------------------------------+
|          Android Runtime (ART) & Core Linux Libraries       |
|    DEX / AOT Compilation | libc | libm | SQLite | OpenGL     |
+-------------------------------------------------------------+
|              Bullet-OS Daemons & Native Services            |
|       bulletd (CPU Governor / Performance / Shield IPC)     |
+-------------------------------------------------------------+
|           Hardware Abstraction Layer (HAL - Treble)         |
|      Audio | Camera | Display (DRM/KMS) | Gralloc | Sensors |
+-------------------------------------------------------------+
|                        Linux Kernel                         |
|     Linux 6.1 LTS / Android Common Kernel (ACK)             |
|       Binder IPC | Ashmem | cpufreq | ZRAM | SELinux        |
+-------------------------------------------------------------+
```

---

## 2. Key Components

### A. Linux Kernel Layer
- **Kernel Version:** Linux 6.1 LTS with Android Common Kernel (ACK) patches.
- **Inter-Process Communication:** Uses the Linux `binder` character driver for secure, zero-copy inter-process communication.
- **Memory Management:** `zRAM` with LZ4 compression algorithm for aggressive RAM optimization on low-end and high-end devices.
- **Security:** Strict SELinux policies enforced on all native daemons and user apps.

### B. Hardware Abstraction Layer (Project Treble)
- Bullet-OS adheres strictly to Google's **Project Treble** modular architecture.
- The `system` partition is fully isolated from the vendor `/vendor` partition via HIDL/AIDL stable interfaces.
- Allows Bullet-OS to boot as a **Generic System Image (GSI)** on any device launched with Android 9 (API 28) or newer without modifying kernel drivers.

### C. bulletd (Core Native Daemon)
- Developed in C++ to run as a high-priority system service (`u:r:bulletd:s0`).
- Dynamically switches Linux CPU scaling governors (`schedutil` -> `performance`) based on foreground activity demands (e.g. 3D gaming or rendering).
- Listens to IPC commands over UNIX domain socket `/dev/socket/bulletd`.

### D. Bullet User Experience
- **BulletLauncher:** Minimalist, zero-lag launcher with adaptive app grid, custom widget host, and swipe gestures.
- **Bullet Lab:** Embedded system settings suite allowing user control over hardware clocking, battery charging protection, and hardware sensor isolation.
- **BulletTerminal:** Built-in Linux shell interface with access to standard POSIX tools (`sh`, `top`, `ps`, `toybox`).
