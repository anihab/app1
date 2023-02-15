package com.example.app1

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.EditText
import android.widget.ImageView
import android.graphics.Bitmap
import android.widget.Button
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // strings
    private var mFirstName: String? = null
    private var mMiddleName: String? = null
    private var mLastName: String? = null

    // UI elements
    private var mTvFirstName: TextView? = null
    private var mTvMiddleName: TextView? = null
    private var mTvLastName: TextView? = null
    private var mButtonSubmit: Button? = null
    private var mButtonCamera: Button? = null
    private var mIvProfilePicture: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize buttons
        mButtonSubmit = findViewById<View>(R.id.button_submit) as Button
        mButtonCamera = findViewById<View>(R.id.button_camera) as Button

        mButtonSubmit!!.setOnClickListener(this)
        mButtonCamera!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            // when the camera button is clicked
            R.id.button_camera -> {
                // open the camera
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraActivity.launch(cameraIntent)
                } catch (ex: ActivityNotFoundException) {
                    // do nothing
                }
            }

            // when the submit button is clicked
            R.id.button_submit -> {

            }
        }
    }

    private var cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            mIvProfilePicture = findViewById<View>(R.id.iv_pfp) as ImageView
            val previewImage =  result.data!!.getParcelableExtra<Bitmap>("data")
            mIvProfilePicture!!.setImageBitmap(previewImage)
        }
    }
}