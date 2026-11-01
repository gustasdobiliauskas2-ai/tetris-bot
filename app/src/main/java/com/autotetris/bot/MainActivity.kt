
package com.autotetris.bot

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.rgb(15, 18, 30))
        }

        val title = TextView(this).apply {
            text = "AUTO TETRIS BOT"
            textSize = 28f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        status = TextView(this).apply {
            text = "Bot is ready"
            textSize = 18f
            setTextColor(Color.GREEN)
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 40)
        }

        val startButton = Button(this).apply {
            text = "START BOT"
            setOnClickListener {
                status.text = "Bot started - automation not yet enabled"
            }
        }

        val stopButton = Button(this).apply {
            text = "STOP BOT"
            setOnClickListener {
                status.text = "Bot stopped"
            }
        }

        layout.addView(title)
        layout.addView(status)
        layout.addView(startButton)
        layout.addView(stopButton)

        setContentView(layout)
    }
}
