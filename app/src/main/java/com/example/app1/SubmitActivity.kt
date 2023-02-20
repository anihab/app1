package com.example.app1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SubmitActivity : AppCompatActivity() {
    // strings
    private var mFirstName: String? = null
    private var mLastName: String? = null
    private var message: String? = null

    // UI elements
    private var mTvMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        // get calling intent
        val receivedIntent = intent

        // retrieve data
        mFirstName = receivedIntent.getStringExtra("FN_DATA")
        mLastName = receivedIntent.getStringExtra("LN_DATA")

        // set the text view with the received data
        message = mFirstName.plus(" ").plus(mLastName).plus(" is logged in!")

        mTvMessage = findViewById<View>(R.id.tv_message) as TextView
        mTvMessage!!.text = message
    }
}