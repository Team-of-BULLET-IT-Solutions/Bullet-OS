@echo off
TITLE Run Bullet-OS in QEMU / Emulator
COLOR 0A

echo =======================================================
echo          BULLET-OS PC EMULATOR LAUNCHER
echo         Engineered by BULLET IT Solutions
echo =======================================================
echo.

WHERE qemu-system-x86_64 >nul 2>nul
IF %ERRORLEVEL% NEQ 0 (
    echo [INFO] QEMU not found on PATH. Attempting to launch via Android Studio Emulator...
    WHERE emulator >nul 2>nul
    IF %ERRORLEVEL% NEQ 0 (
        echo [ERROR] Neither QEMU nor Android Emulator was found.
        echo Please install QEMU or Android Studio SDK Emulator to run Bullet-OS on PC.
        echo Alternatively, test Bullet-OS in the web simulator under 'simulator/index.html'.
        pause
        exit /b 1
    )
    emulator -avd BulletOS_x86_64 -show-kernel
    exit /b 0
)

echo [INFO] Starting Bullet-OS x86_64 in QEMU with KVM/WHPX acceleration...
qemu-system-x86_64 ^
    -m 4096 ^
    -smp 4 ^
    -vga virtio ^
    -display default,show-cursor=on ^
    -device virtio-net,netdev=net0 ^
    -netdev user,id=net0,hostfwd=tcp::5555-:5555 ^
    -drive file=system.img,format=raw,if=virtio ^
    -serial stdio

pause
