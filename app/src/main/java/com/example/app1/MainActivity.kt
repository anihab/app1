package com.example.app1

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.EditText
import android.widget.ImageView
import android.graphics.Bitmap
import android.widget.Button
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.view.View
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // strings
    private var mFirstName: String? = null
    private var mMiddleName: String? = null
    private var mLastName: String? = null
    private var mProfilePicturePath: String? = null

    // UI elements
    private var mEtFirstName: EditText? = null
    private var mEtMiddleName: EditText? = null
    private var mEtLastName: EditText? = null

    private var mButtonSubmit: Button? = null
    private var mButtonCamera: Button? = null

    private var mIvProfilePicture: ImageView? = null
    private var mThumbnailImage: Bitmap? = null

    // intent for next page
    private var mSubmitIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize edit text
        mEtFirstName = findViewById(R.id.et_first_name)
        mEtMiddleName = findViewById(R.id.et_middle_name)
        mEtLastName = findViewById(R.id.et_last_name)

        // initialize buttons
        mButtonSubmit = findViewById(R.id.button_submit)
        mButtonCamera = findViewById(R.id.button_camera)

        mButtonSubmit!!.setOnClickListener(this)
        mButtonCamera!!.setOnClickListener(this)

        // create intent for next page (activity) but don't start yet
        mSubmitIntent = Intent(this, SubmitActivity::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("FN", mEtFirstName!!.text.toString())
        outState.putString("MN", mEtMiddleName!!.text.toString())
        outState.putString("LN", mEtLastName!!.text.toString())
        outState.putString("PFP_PATH", mProfilePicturePath)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mEtFirstName!!.setText(savedInstanceState.getString("FN"))
        mEtMiddleName!!.setText(savedInstanceState.getString("MN"))
        mEtLastName!!.setText(savedInstanceState.getString("LN"))
        mProfilePicturePath = savedInstanceState.getString("PFP_PATH")

        mIvProfilePicture = findViewById(R.id.iv_pfp)
        if (mProfilePicturePath != null) {
            mThumbnailImage = BitmapFactory.decodeFile(mProfilePicturePath)
            mIvProfilePicture!!.setImageBitmap(mThumbnailImage)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            // when the camera button is clicked
            R.id.button_camera -> {
                // open the camera
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraLauncher.launch(cameraIntent)
                } catch (ex: ActivityNotFoundException) {
                    // do nothing
                }
            }

            // when the submit button is clicked
            R.id.button_submit -> {
                // get name strings from edit text
                mFirstName = mEtFirstName!!.text.toString()
                mMiddleName = mEtMiddleName!!.text.toString()
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
                    mSubmitIntent!!.putExtra("FN_DATA", mFirstName)
                    mSubmitIntent!!.putExtra("LN_DATA", mLastName)
                    // explicit intent
                    startActivity(mSubmitIntent)
                }
            }
        }
    }

    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val extras = result.data!!.extras
            mThumbnailImage = extras!!["data"] as Bitmap?
            mIvProfilePicture = findViewById(R.id.iv_pfp)

            // write to file
            if (isExternalStorageWritable) {
                mProfilePicturePath = saveImage(mThumbnailImage)
                mIvProfilePicture!!.setImageBitmap(mThumbnailImage)
            } else {
                // do nothing
            }
        }
    }

    private fun saveImage(finalBitmap: Bitmap?): String {
        val root = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val fname = "App1_Thumbnail.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private val isExternalStorageWritable: Boolean
    get() {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
}