@echo off
TITLE Bullet-OS GSI Fastboot Flasher (BULLET IT Solutions)
COLOR 0B

echo =======================================================
echo          BULLET-OS GSI FASTBOOT FLASHER
echo         Engineered by BULLET IT Solutions
echo =======================================================
echo.

IF NOT EXIST "system.img" (
    echo [ERROR] system.img not found in the current folder!
    echo Please place your compiled Bullet-OS system.img here.
    echo.
    pause
    exit /b 1
)

echo [1/5] Checking fastboot connection...
fastboot devices
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] No device found in fastboot mode.
    echo Please reboot your device into Fastboot/Bootloader mode and connect via USB.
    pause
    exit /b 1
)

echo.
echo [2/5] Rebooting to fastbootd (Dynamic Partitions mode)...
fastboot reboot fastboot
timeout /t 5 >nul

echo.
echo [3/5] Disabling Android Verified Boot (AVB/vbmeta)...
IF EXIST "vbmeta.img" (
    fastboot --disable-verity --disable-verification flash vbmeta vbmeta.img
) ELSE (
    echo [INFO] vbmeta.img not provided, skipping vbmeta flash.
)

echo.
echo [4/5] Flashing Bullet-OS system partition (system.img)...
fastboot erase system
fastboot flash system system.img

echo.
echo [5/5] Factory resetting device data (wipe user data for clean boot)...
set /p WIPE="Do you want to format userdata? Required on first install (y/n): "
IF /I "%WIPE%"=="y" (
    fastboot -w
)

echo.
echo =======================================================
echo  Bullet-OS Flash Complete! Rebooting your device...
echo =======================================================
fastboot reboot
pause
