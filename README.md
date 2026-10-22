<p align="center">
  <img src="https://img.shields.io/badge/OS-Bullet--OS-00F0FF?style=for-the-badge&logo=android&logoColor=black" alt="Bullet-OS" />
  <img src="https://img.shields.io/badge/Team-BULLET--IT--Solutions-FF5500?style=for-the-badge&logo=github&logoColor=white" alt="Team BULLET IT Solutions" />
  <img src="https://img.shields.io/badge/Base-Linux_6.1_+_AOSP_14-38BDF8?style=for-the-badge&logo=linux&logoColor=white" alt="Base" />
</p>

<h1 align="center">Bullet-OS</h1>
<p align="center">
  <strong>Next-Generation High-Performance Linux-Based Mobile Operating System</strong><br>
  <em>Engineered for Speed, Privacy, and Hardware Agility by Team BULLET IT Solutions</em>
</p>

---

## ⚡ Overview

**Bullet-OS** is a custom open-source mobile operating system engineered on the **Linux Kernel** and the **Android Open Source Project (AOSP)** foundation. It is tailored for ultra-low latency, pure Android app compatibility (`.apk`), and aggressive hardware optimization.

With native **Project Treble Generic System Image (GSI)** compliance and **x86_64 PC Emulator** support, Bullet-OS can be flashed onto millions of Android smartphones or tested locally on personal computers.

---

## 🌟 Key Features

| Feature | Description |
| :--- | :--- |
| **🚀 Native Android Compatibility** | Executes all standard Android apps (`.apk`) natively via optimized Android Runtime (ART) with Ahead-of-Time (AOT) compilation. |
| **🏎️ Bullet Performance Engine (`bulletd`)** | Native C++ daemon interacting directly with Linux `cpufreq` sysfs nodes to dynamically switch governors between `schedutil` and `performance`. |
| **🛡️ Bullet Shield** | Enhanced hardware privacy engine with real-time sensor isolation, network guard, and hardened SELinux policy. |
| **📱 Project Treble GSI** | Flashable system image (`system.img`) compatible with any device running Android 9 through Android 15. |
| **💻 Bullet Terminal** | Integrated root Linux shell application (`/system_ext/bin/sh`, `toybox`, `top`, `dmesg`, `ps`) for power users and developers. |
| **🎨 Cyber-Kinetic UI** | Minimalist dark theme (`#0B0E14`), neon cyan accenting (`#00F0FF`), 120Hz high refresh rate scheduler, and fluid gesture navigation. |
| **🖥️ Interactive Web Simulator** | Experience Bullet-OS directly in your web browser with a full interactive phone frame and working shell! |

---

## 🏗️ Architecture

```
+--------------------------------------------------------------------+
|                         Bullet-OS Apps                             |
|    BulletLauncher  |  BulletSettings (Bullet Lab)  | BulletTerminal|
+--------------------------------------------------------------------+
|                      Android Framework (AOSP)                      |
|       ActivityManager | WindowManager | PackageManager | HAL IPC   |
+--------------------------------------------------------------------+
|               Android Runtime (ART) & Native Daemons               |
|      DEX Bytecode -> Native Code  |  bulletd (CPU & Perf Daemon)   |
+--------------------------------------------------------------------+
|                    Hardware Abstraction Layer                      |
|        Audio | Camera | DRM Graphics | Sensors | Wi-Fi / Radio     |
+--------------------------------------------------------------------+
|                         Linux Kernel                               |
|        Linux 6.1 LTS ACK | Binder IPC | cpufreq | zRAM (LZ4)       |
+--------------------------------------------------------------------+
```

Detailed architectural specifications are documented in [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md).

---

## 🚀 Interactive Web Simulator

You can test and preview the Bullet-OS interface, apps, and terminal right now without compiling:

1. Open [`simulator/index.html`](simulator/index.html) in any web browser.
2. Experience the live status bar, quick settings pull-down, Bullet Lab performance toggles, and run real simulated Linux commands inside the **Bullet Terminal**!

---

## 🛠️ Building Bullet-OS

### Option 1: Docker (Recommended)
```bash
# 1. Build the container with all tools preconfigured
docker build -t bullet-os-builder build/docker/

# 2. Start building inside container
docker run -it -v $(pwd):/home/bullet/bullet_os bullet-os-builder
source build/envsetup.sh
lunch bullet_arm64-userdebug
make_bullet
```

### Option 2: Native Linux Host
```bash
# 1. Source build tools
source build/envsetup.sh

# 2. Select target
lunch_bullet bullet_arm64-userdebug   # For ARM64 Smartphone GSI
# or
lunch_bullet bullet_x86_64-userdebug  # For PC Emulator

# 3. Compile
make_bullet
```

Full compilation steps and requirements are in [`docs/BUILD_GUIDE.md`](docs/BUILD_GUIDE.md).

---

## 📲 Flashing to an Android Smartphone

Bullet-OS includes automated flashing scripts for both Windows and Linux:

```bash
# 1. Put phone in FastbootD mode
adb reboot fastboot

# 2. Flash using the automated script
# On Windows:
build\tools\flash-gsi.bat

# On Linux / macOS:
bash build/tools/flash-gsi.sh
```

Step-by-step instructions available in [`docs/GSI_INSTALLATION.md`](docs/GSI_INSTALLATION.md).

---

## 📂 Project Structure

```
Bullet-OS/
├── .github/workflows/       # Automated CI & GitHub Pages simulator deployment
├── build/
│   ├── docker/              # Reproducible Ubuntu 22.04 AOSP build container
│   ├── tools/               # Fastboot flasher, QEMU runner, bootanimation generator
│   ├── envsetup.sh          # Bullet build environment setup
│   └── bullet-build.sh      # Master build orchestration script
├── device/bullet/
│   ├── generic_arm64/       # Generic System Image (GSI) target tree
│   └── generic_x86_64/      # PC Emulator / QEMU target tree
├── docs/                    # Architecture, build guide, and flashing guide
├── manifest/                # Repo manifests for multi-repo AOSP synchronization
├── simulator/               # Browser-based interactive mobile simulator
└── vendor/bullet/
    ├── bootanimation/       # Bullet-OS boot animation frames & config
    ├── config/              # Product makefiles (common.mk, version.mk, branding.mk)
    ├── overlay/             # System-wide UI & Framework resource overlays
    ├── packages/apps/
    │   ├── BulletLauncher/  # Native Android desktop launcher
    │   ├── BulletSettings/  # Bullet Lab system settings extension
    │   └── BulletTerminal/  # Integrated Linux shell terminal
    ├── sepolicy/            # SELinux security rules
    └── services/bulletd/    # Native C++ performance daemon
```

---

## 👥 Organization & Credits

- **Organization:** [Team-of-BULLET-IT-Solutions](https://github.com/Team-of-BULLET-IT-Solutions)
- **Repository:** [Bullet-OS](https://github.com/Team-of-BULLET-IT-Solutions/Bullet-OS)
- **Lead Developer:** Team BULLET IT Solutions

---

## 📄 License

Bullet-OS is released under the **Apache License 2.0** and the **GNU General Public License v2 (Linux Kernel)**. See [`LICENSE`](LICENSE) for details.
