package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CommunityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        // Post 1 buttons
        val btnRate1 = findViewById<Button>(R.id.btnRate1)
        val btnReply1 = findViewById<Button>(R.id.btnReply1)

        btnRate1.setOnClickListener { showPopup("Rate Post 1") }
        btnReply1.setOnClickListener { showPopup("Reply to Post 1") }

        // Post 2 buttons
        val btnRate2 = findViewById<Button>(R.id.btnRate2)
        val btnReply2 = findViewById<Button>(R.id.btnReply2)

        btnRate2.setOnClickListener { showPopup("Rate Post 2") }
        btnReply2.setOnClickListener { showPopup("Reply to Post 2") }
    }

    private fun showPopup(message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setMessage("This is a placeholder popup.")
            .setPositiveButton("OK", null)
            .show()
    }
}

