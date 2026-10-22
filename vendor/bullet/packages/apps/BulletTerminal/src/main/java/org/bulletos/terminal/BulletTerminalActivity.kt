/*
 * Copyright (C) 2026 BULLET IT Solutions
 * Bullet-OS Embedded Linux Shell & Diagnostic Terminal
 */

package org.bulletos.terminal

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader

class BulletTerminalActivity : Activity() {

    private lateinit var outputTextView: TextView
    private lateinit var commandInput: EditText
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTerminalUI()
        appendOutput("==========================================\n")
        appendOutput(" Bullet-OS Terminal v1.0 (Kinetic)\n")
        appendOutput(" Linux Kernel shell interface\n")
        appendOutput(" Team BULLET IT Solutions\n")
        appendOutput(" Type 'help' or any Linux command.\n")
        appendOutput("==========================================\n\nroot@bullet-os:/# ")
    }

    private fun setupTerminalUI() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(android.graphics.Color.parseColor("#05080E"))
            setPadding(24, 48, 24, 24)
        }

        scrollView = ScrollView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f
            )
        }

        outputTextView = TextView(this).apply {
            setTextColor(android.graphics.Color.parseColor("#00F0FF"))
            typeface = android.graphics.Typeface.MONOSPACE
            textSize = 13f
        }
        scrollView.addView(outputTextView)
        root.addView(scrollView)

        commandInput = EditText(this).apply {
            setTextColor(android.graphics.Color.WHITE)
            typeface = android.graphics.Typeface.MONOSPACE
            hint = "Enter command..."
            setHintTextColor(android.graphics.Color.GRAY)
            imeOptions = EditorInfo.IME_ACTION_DONE
            setSingleLine()
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    val cmd = text.toString().trim()
                    if (cmd.isNotEmpty()) {
                        executeCommand(cmd)
                        text.clear()
                    }
                    true
                } else false
            }
        }
        root.addView(commandInput)
        setContentView(root)
    }

    private fun appendOutput(text: String) {
        outputTextView.append(text)
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun executeCommand(cmd: String) {
        appendOutput(cmd + "\n")
        if (cmd == "clear") {
            outputTextView.text = ""
            appendOutput("root@bullet-os:/# ")
            return
        }

        Thread {
            try {
                val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", cmd))
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                val errReader = BufferedReader(InputStreamReader(process.errorStream))
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    val out = line + "\n"
                    runOnUiThread { appendOutput(out) }
                }
                while (errReader.readLine().also { line = it } != null) {
                    val out = line + "\n"
                    runOnUiThread { appendOutput(out) }
                }
                process.waitFor()
            } catch (e: Exception) {
                runOnUiThread { appendOutput("Execution error: " + e.message + "\n") }
            }
            runOnUiThread { appendOutput("root@bullet-os:/# ") }
        }.start()
    }
}
