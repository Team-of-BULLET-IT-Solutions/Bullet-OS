/*
 * Copyright (C) 2026 BULLET IT Solutions
 * Bullet-OS Sleek Desktop Launcher
 */

package org.bulletos.launcher

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class BulletLauncherActivity : Activity() {

    private lateinit var packageManagerHelper: PackageManager
    private var installedApps: List<ResolveInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        packageManagerHelper = packageManager
        loadApps()
        setupUI()
    }

    private fun loadApps() {
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        installedApps = packageManagerHelper.queryIntentActivities(mainIntent, 0)
            .sortedBy { it.loadLabel(packageManagerHelper).toString() }
    }

    private fun setupUI() {
        val gridView = GridView(this).apply {
            numColumns = 4
            horizontalSpacing = 24
            verticalSpacing = 36
            setPadding(32, 64, 32, 64)
            clipToPadding = false
        }

        gridView.adapter = AppAdapter()
        gridView.setOnItemClickListener { _, _, position, _ ->
            val app = installedApps[position]
            val intent = packageManagerHelper.getLaunchIntentForPackage(app.activityInfo.packageName)
            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Unable to launch " + app.loadLabel(packageManagerHelper), Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(gridView)
    }

    inner class AppAdapter : BaseAdapter() {
        override fun getCount(): Int = installedApps.size
        override fun getItem(position: Int): Any = installedApps[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val appInfo = installedApps[position]
            val context = parent?.context ?: this@BulletLauncherActivity
            val linearLayout = android.widget.LinearLayout(context).apply {
                orientation = android.widget.LinearLayout.VERTICAL
                gravity = android.view.Gravity.CENTER
            }

            val iconView = ImageView(context).apply {
                setImageDrawable(appInfo.loadIcon(packageManagerHelper))
                layoutParams = android.widget.LinearLayout.LayoutParams(140, 140)
            }

            val labelView = TextView(context).apply {
                text = appInfo.loadLabel(packageManagerHelper)
                setTextColor(android.graphics.Color.WHITE)
                textSize = 12f
                gravity = android.view.Gravity.CENTER
                setSingleLine(true)
                ellipsize = android.text.TextUtils.TruncateAt.END
                setPadding(0, 10, 0, 0)
            }

            linearLayout.addView(iconView)
            linearLayout.addView(labelView)
            return linearLayout
        }
    }

    override fun onBackPressed() {
        // Prevent exiting launcher home screen
    }
}
