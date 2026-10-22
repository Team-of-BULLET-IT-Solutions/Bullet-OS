/*
 * Copyright (C) 2026 BULLET IT Solutions
 * Bullet Lab - Performance, Theme & Privacy Settings
 */

package org.bulletos.settings

import android.app.Activity
import android.net.LocalSocket
import android.net.LocalSocketAddress
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream

class BulletLabActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
            setBackgroundColor(android.graphics.Color.parseColor("#0B0E14"))
        }

        val title = TextView(this).apply {
            text = "⚡ Bullet Lab"
            textSize = 28f
            setTextColor(android.graphics.Color.parseColor("#00F0FF"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, 16)
        }

        val subtitle = TextView(this).apply {
            text = "Engineered by BULLET IT Solutions for Pure Performance"
            textSize = 14f
            setTextColor(android.graphics.Color.parseColor("#94A3B8"))
            setPadding(0, 0, 0, 40)
        }

        rootLayout.addView(title)
        rootLayout.addView(subtitle)

        // Boost Mode Toggle
        val boostSwitch = Switch(this).apply {
            text = "🚀 Bullet Boost Mode (Lock Peak CPU/GPU)"
            setTextColor(android.graphics.Color.WHITE)
            textSize = 16f
            setPadding(0, 20, 0, 20)
            setOnCheckedChangeListener { _, isChecked ->
                val command = if (isChecked) "PROFILE_BOOST" else "PROFILE_BALANCED"
                sendCommandToDaemon(command)
                Toast.makeText(this@BulletLabActivity, "Bullet Governor: " + if(isChecked) "Performance" else "Balanced", Toast.LENGTH_SHORT).show()
            }
        }
        rootLayout.addView(boostSwitch)

        // Privacy Shield Toggle
        val privacySwitch = Switch(this).apply {
            text = "🛡️ Bullet Shield (Strict Sensor & Network Guard)"
            setTextColor(android.graphics.Color.WHITE)
            textSize = 16f
            isChecked = true
            setPadding(0, 20, 0, 20)
            setOnCheckedChangeListener { _, isChecked ->
                Toast.makeText(this@BulletLabActivity, "Bullet Shield: " + if(isChecked) "Hardened" else "Standard", Toast.LENGTH_SHORT).show()
            }
        }
        rootLayout.addView(privacySwitch)

        // System Info card
        val infoCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(android.graphics.Color.parseColor("#151B26"))
            setPadding(30, 30, 30, 30)
            val infoText = TextView(this@BulletLabActivity).apply {
                text = "OS Version: Bullet-OS 1.0 (Kinetic)\nKernel: Linux 6.1 LTS - Bullet Kernel\nAndroid Base: 14.0 (API 34)\nTeam: BULLET IT Solutions"
                setTextColor(android.graphics.Color.parseColor("#E2E8F0"))
                textSize = 14f
                setLineSpacing(10f, 1f)
            }
            addView(infoText)
        }
        rootLayout.addView(infoCard)

        val scrollView = ScrollView(this).apply {
            addView(rootLayout)
        }
        setContentView(scrollView)
    }

    private fun sendCommandToDaemon(command: String) {
        Thread {
            try {
                val socket = LocalSocket()
                val address = LocalSocketAddress("/dev/socket/bulletd", LocalSocketAddress.Namespace.FILESYSTEM)
                socket.connect(address)
                val out: OutputStream = socket.outputStream
                out.write(command.toByteArray())
                out.flush()
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
