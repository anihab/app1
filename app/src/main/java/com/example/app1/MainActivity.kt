package com.example.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Accept the user's first, middle, and last names in separate EditTexts.

// Ask the user to take a profile picture using the phone camera and show a thumbnail on the app.

// When the user hits a submit button, take them to a second page. This page says "First name last name is logged in!", where "First name" and "last name" are the names entered on the previous page.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}