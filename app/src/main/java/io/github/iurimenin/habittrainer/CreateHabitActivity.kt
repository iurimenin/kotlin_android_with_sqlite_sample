package io.github.iurimenin.habittrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import io.github.iurimenin.habittrainer.db.HabitDbTable
import kotlinx.android.synthetic.main.activity_create_habit.*
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {

    private val TAG = CreateHabitActivity::class.java.simpleName
    private val CHOOSE_IMAGE_REQUEST = 1

    private var imageBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)

        buttonChooseImage.setOnClickListener { chooseImage() }
        buttonSave.setOnClickListener { storeHabit() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE_REQUEST &&
                resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            Log.d(TAG, "An image was chosen by the user")

            val bitmap = tryReadBitmap(data.data)

            bitmap?.let {
                imageBitmap = it
                imageViewImage.setImageBitmap(it)
                Log.d(TAG, "Read image bitmap and updated image view")
            }
        }
    }

    private fun storeHabit() {

        if (editTextTitle.isBlank() ||
                editTextDescription.isBlank()) {

            Log.d(TAG, "No habit stored: title or description missing")
            displayErrorMessage(getString(R.string.error_title_description_missing))
            return
        } else if (imageBitmap == null) {
            Log.d(TAG, "No habit stored: image missing")
            displayErrorMessage(getString(R.string.error_image_missing))
            return
        }

        textViewError.visibility = View.INVISIBLE
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val habit = Habit(title, description, imageBitmap!!)

        val id = HabitDbTable(this).store(habit)

        if (id == -1L) {
            displayErrorMessage(getString(R.string.habit_save_db_error))
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun chooseImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, getString(R.string.choose_image_habit))
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)

        Log.d(TAG, "Intent to choose image sent...")
    }

    private fun tryReadBitmap(uri: Uri): Bitmap? {

        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } catch (e : IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun displayErrorMessage(message : String) {
        textViewError.text = message
        textViewError.visibility = View.VISIBLE
    }
}

private fun EditText.isBlank(): Boolean = this.text.toString().isBlank()
