/*
 * Copyright (C) 2026 BULLET IT Solutions
 * Bullet-OS Settings Extension - Bullet AquaFlow Design Language
 */

package org.bulletos.settings

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.net.LocalSocket
import android.net.LocalSocketAddress
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import java.io.OutputStream

class BulletLabActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#000000"))
            setPadding(36, 50, 36, 40)
        }

        // Header Title (Bullet Flow large title)
        val title = TextView(this).apply {
            text = "⚡ Bullet Lab"
            textSize = 34f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, 6)
        }

        val subtitle = TextView(this).apply {
            text = "Engineered by BULLET IT Solutions (Bullet Flow UI)"
            textSize = 13f
            setTextColor(Color.parseColor("#8E8E93"))
            setPadding(0, 0, 0, 24)
        }

        root.addView(title)
        root.addView(subtitle)

        // BULLET ID Profile Card
        val profileCard = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setBackgroundColor(Color.parseColor("#1C1C1E"))
            setPadding(30, 24, 30, 24)
            gravity = Gravity.CENTER_VERTICAL
            val avatar = TextView(this@BulletLabActivity).apply {
                text = "⚡"
                textSize = 24f
                setTextColor(Color.BLACK)
                setBackgroundColor(Color.parseColor("#00F0FF"))
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(110, 110).apply {
                    rightMargin = 28
                }
            }
            val details = LinearLayout(this@BulletLabActivity).apply {
                orientation = LinearLayout.VERTICAL
                val name = TextView(this@BulletLabActivity).apply {
                    text = "BULLET IT Solutions"
                    textSize = 17f
                    setTextColor(Color.WHITE)
                    typeface = Typeface.DEFAULT_BOLD
                }
                val sub = TextView(this@BulletLabActivity).apply {
                    text = "BULLET ID, Cloud & Security"
                    textSize = 12f
                    setTextColor(Color.parseColor("#8E8E93"))
                }
                addView(name)
                addView(sub)
            }
            addView(avatar)
            addView(details)
        }
        root.addView(profileCard)

        // Section 1: Performance (Bullet Card Container)
        val perfGroup = createSection("PERFORMANCE & KERNEL")
        val boostSwitch = Switch(this).apply {
            text = "🚀 Bullet Boost (Gaming Overclock)"
            setTextColor(Color.WHITE)
            textSize = 15f
            setPadding(0, 14, 0, 14)
            setOnCheckedChangeListener { _, isChecked ->
                val cmd = if (isChecked) "PROFILE_BOOST" else "PROFILE_BALANCED"
                sendCommand(cmd)
                Toast.makeText(this@BulletLabActivity, "Governor: " + if (isChecked) "Performance" else "Balanced", Toast.LENGTH_SHORT).show()
            }
        }
        perfGroup.addView(boostSwitch)
        root.addView(perfGroup)

        // Section 2: Privacy Shield
        val shieldGroup = createSection("BULLET SHIELD SECURITY")
        val shieldSwitch = Switch(this).apply {
            text = "🛡️ Sensor Hardware Kill-switch"
            setTextColor(Color.WHITE)
            textSize = 15f
            isChecked = true
            setPadding(0, 14, 0, 14)
            setOnCheckedChangeListener { _, isChecked ->
                Toast.makeText(this@BulletLabActivity, "Shield: " + if (isChecked) "Hardened" else "Standard", Toast.LENGTH_SHORT).show()
            }
        }
        shieldGroup.addView(shieldSwitch)
        root.addView(shieldGroup)

        // Section 3: About
        val aboutGroup = createSection("SYSTEM SPECIFICATIONS")
        val infoText = TextView(this).apply {
            text = "Operating System: Bullet-OS 1.0 (Kinetic)\nUI Experience: Bullet Flow UI (Aquamorphic Engine)\nBase Foundation: Linux 6.1 LTS + AOSP 14\nOwner & Maintainer: BULLET IT Solutions"
            setTextColor(Color.parseColor("#C7C7CC"))
            textSize = 13f
            setLineSpacing(12f, 1f)
            setPadding(0, 8, 0, 8)
        }
        aboutGroup.addView(infoText)
        root.addView(aboutGroup)

        val scroll = ScrollView(this).apply {
            addView(root)
        }
        setContentView(scroll)
    }

    private fun createSection(headerTitle: String): LinearLayout {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 24, 0, 0)
        }
        val header = TextView(this).apply {
            text = headerTitle
            textSize = 11f
            setTextColor(Color.parseColor("#00F0FF"))
            typeface = Typeface.DEFAULT_BOLD
            setPadding(12, 0, 0, 10)
        }
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#1C1C1E"))
            setPadding(30, 16, 30, 16)
        }
        container.addView(header)
        container.addView(card)
        return card
    }

    private fun sendCommand(cmd: String) {
        Thread {
            try {
                val socket = LocalSocket()
                socket.connect(LocalSocketAddress("/dev/socket/bulletd", LocalSocketAddress.Namespace.FILESYSTEM))
                val out: OutputStream = socket.outputStream
                out.write(cmd.toByteArray())
                out.flush()
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
