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
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // strings
    private var mFirstName: String? = null
    private var mMiddleName: String? = null
    private var mLastName: String? = null

    // UI elements
    private var mTvFirstName: TextView? = null
    private var mTvMiddleName: TextView? = null
    private var mTvLastName: TextView? = null
    private var mEtFirstName: EditText? = null
    private var mEtMiddleName: EditText? = null
    private var mEtLastName: EditText? = null
    private var mButtonSubmit: Button? = null
    private var mButtonCamera: Button? = null
    private var mIvProfilePicture: ImageView? = null

    // intent for next page
    private var mDisplayIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize buttons
        mButtonSubmit = findViewById<View>(R.id.button_submit) as Button
        mButtonCamera = findViewById<View>(R.id.button_camera) as Button

        mButtonSubmit!!.setOnClickListener(this)
        mButtonCamera!!.setOnClickListener(this)

        // create fragment
        mDisplayIntent = Intent(this, DisplayFragment::class.java)
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
                // get name strings from edit text
                mEtFirstName = findViewById<View>(R.id.et_first_name) as EditText
                mFirstName = mEtFirstName!!.text.toString()

                mEtMiddleName = findViewById<View>(R.id.et_middle_name) as EditText
                mMiddleName = mEtMiddleName!!.text.toString()

                mEtLastName = findViewById<View>(R.id.et_last_name) as EditText
                mLastName = mEtLastName!!.text.toString()

                // make sure all names have content
                if (mFirstName.isNullOrBlank() || mMiddleName.isNullOrBlank() || mLastName.isNullOrBlank()) {
                    // warn user
                    Toast.makeText(
                        this@MainActivity,
                        "All names must be entered!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    // pass strings to new activity
                    mDisplayIntent!!.putExtra("FN_DATA", mFirstName)
                    mDisplayIntent!!.putExtra("LN_DATA", mLastName)
                    startActivity(mDisplayIntent) //explicit intent
                }

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