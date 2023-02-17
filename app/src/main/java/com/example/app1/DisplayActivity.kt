package com.example.app1

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayActivity : AppCompatActivity() {
    // strings
    private var mFirstName: String? = null
    private var mLastName: String? = null
    private var message: String? = null

    // UI elements
    private var mTvMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        // get calling intent
        val receivedIntent = intent

        // retrieve data
        mFirstName = receivedIntent.getStringExtra("FIRST_NAME")
        mLastName = receivedIntent.getStringExtra("LAST_NAME")

        // set the text view with the received data
        message = mFirstName.plus(" ").plus(mLastName).plus(" is logged in!")
        mTvMessage!!.text = message
    }
}