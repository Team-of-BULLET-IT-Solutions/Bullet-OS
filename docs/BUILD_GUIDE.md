# Bullet-OS Compilation & Build Guide

This document guides you through setting up your build environment and compiling Bullet-OS from source.

---

## 1. System Requirements

- **Operating System:** Ubuntu 22.04 LTS (recommended) or any modern Linux distribution / WSL2
- **CPU:** 8 cores minimum (16+ cores recommended)
- **RAM:** 32 GB minimum (64 GB or 32 GB + swap recommended)
- **Disk Space:** 300 GB free SSD storage

---

## 2. Option A: Build with Docker (Recommended for Windows / Mac / Linux)

Bullet-OS includes a production-grade Dockerfile with all build tools pre-installed.

### Step 1: Build Docker Container
```bash
docker build -t bullet-os-builder build/docker/
```

### Step 2: Run Container
```bash
docker run -it -v $(pwd):/home/bullet/bullet_os bullet-os-builder
```

### Step 3: Compile
```bash
source build/envsetup.sh
lunch bullet_arm64-userdebug
make_bullet
```

---

## 3. Option B: Native Linux Compilation

### Step 1: Install Dependencies
```bash
sudo apt update && sudo apt install -y \
    git-core gnupg flex bison build-essential zip curl zlib1g-dev \
    gcc-multilib g++-multilib libc6-dev-i386 lib32ncurses5-dev \
    x11proto-core-dev libx11-dev lib32z1-dev libgl1-mesa-dev \
    libxml2-utils xsltproc unzip fontconfig python3 ccache openjdk-17-jdk
```

### Step 2: Initialize Repo Manifest
```bash
# Create working directory
mkdir ~/bullet_workspace && cd ~/bullet_workspace

# Initialize manifest
repo init -u https://github.com/Team-of-BULLET-IT-Solutions/Bullet-OS.git -b main -m manifest/default.xml

# Synchronize sources
repo sync -c -j$(nproc --all) --no-clone-bundle --no-tags
```

### Step 3: Run Master Build Script
```bash
# Build ARM64 Generic System Image
./build/bullet-build.sh bullet_arm64-userdebug

# Or build PC x86_64 Emulator Image
./build/bullet-build.sh bullet_x86_64-userdebug
```

The resulting `system.img` will be generated in `out/target/product/generic_arm64/system.img`.
