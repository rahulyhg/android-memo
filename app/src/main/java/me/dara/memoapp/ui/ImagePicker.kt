package me.dara.memoapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log

import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayList
import me.dara.memoapp.R

import androidx.core.graphics.TypefaceCompatUtil.getTempFile

/**
 * @author sardor
 */
object ImagePicker {

  private val DEFAULT_MIN_WIDTH_QUALITY = 400        // min pixels
  private val TAG = "ImagePicker"
  private val TEMP_IMAGE_NAME = "tempImage"

  var minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY

  fun getPickImageIntent(context: Context): Intent? {
    var chooserIntent: Intent? = null

    var intentList: MutableList<Intent> = ArrayList()

    val pickIntent = Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    takePhotoIntent.putExtra("return-data", true)
    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(context)))
    intentList = addIntentsToList(context, intentList, pickIntent)
    intentList = addIntentsToList(context, intentList, takePhotoIntent)

    if (intentList.size > 0) {
      chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1),
          context.getString(R.string.pick_image_intent_title))
      chooserIntent!!.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray<Parcelable>())
    }

    return chooserIntent
  }

  private fun addIntentsToList(context: Context, list: MutableList<Intent>, intent: Intent): MutableList<Intent> {
    val resInfo = context.packageManager.queryIntentActivities(intent, 0)
    for (resolveInfo in resInfo) {
      val packageName = resolveInfo.activityInfo.packageName
      val targetedIntent = Intent(intent)
      targetedIntent.setPackage(packageName)
      list.add(targetedIntent)
      Log.d(TAG, "Intent: " + intent.action + " package: " + packageName)
    }
    return list
  }
}
