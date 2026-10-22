#!/usr/bin/env python3
"""
Copyright (C) 2026 BULLET IT Solutions
Bootanimation generator for Bullet-OS
"""

import os
import zipfile
from PIL import Image, ImageDraw, ImageFont

def generate_frames():
    os.makedirs("vendor/bullet/bootanimation/part0", exist_ok=True)
    os.makedirs("vendor/bullet/bootanimation/part1", exist_ok=True)
    
    width, height = 720, 1600
    total_frames = 30
    
    for i in range(total_frames):
        img = Image.new("RGB", (width, height), color=(11, 14, 20))
        draw = ImageDraw.Draw(img)
        
        # Draw tech glowing circle / logo animation
        progress = i / float(total_frames)
        cx, cy = width // 2, height // 2 - 100
        radius = int(80 + progress * 20)
        
        # Glowing accent circles
        draw.ellipse([cx - radius, cy - radius, cx + radius, cy + radius], outline=(0, 240, 255), width=4)
        draw.ellipse([cx - radius + 15, cy - radius + 15, cx + radius - 15, cy + radius - 15], outline=(255, 85, 0), width=2)
        
        # Text
        draw.text((cx - 110, cy + 140), "BULLET-OS", fill=(0, 240, 255))
        draw.text((cx - 150, cy + 180), "Powered by BULLET IT Solutions", fill=(148, 163, 184))
        
        frame_name = f"vendor/bullet/bootanimation/part0/frame_{i:04d}.png"
        img.save(frame_name)
    
    # Create zero-compression zip (Android bootanimation requirement)
    zip_path = "vendor/bullet/bootanimation/bootanimation.zip"
    with zipfile.ZipFile(zip_path, 'w', compression=zipfile.ZIP_STORED) as zipf:
        zipf.write("vendor/bullet/bootanimation/desc.txt", "desc.txt")
        for f in sorted(os.listdir("vendor/bullet/bootanimation/part0")):
            zipf.write(os.path.join("vendor/bullet/bootanimation/part0", f), os.path.join("part0", f))
            
    print(f"[OK] Generated {zip_path}")

if __name__ == "__main__":
    try:
        generate_frames()
    except Exception as e:
        print(f"PIL not available or error: {e}, creating placeholder zip.")
        zip_path = "vendor/bullet/bootanimation/bootanimation.zip"
        with zipfile.ZipFile(zip_path, 'w', compression=zipfile.ZIP_STORED) as zipf:
            zipf.write("vendor/bullet/bootanimation/desc.txt", "desc.txt")
        print(f"[OK] Created stub {zip_path}")
