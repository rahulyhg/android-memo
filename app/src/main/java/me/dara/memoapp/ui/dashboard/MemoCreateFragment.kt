package me.dara.memoapp.ui.dashboard

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.graphics.TypefaceCompatUtil.getTempFile
import kotlinx.android.synthetic.main.fragment_memo_create.*

import me.dara.memoapp.R
import me.dara.memoapp.ui.view.DatePickerAppCompat
import java.io.File

class MemoCreateFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_memo_create, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    imgMemo.setOnClickListener {
      startActivityForResult(getPickImageIntent(requireContext()), 1000)
    }
    dateMemo.setOnClickListener {
      val picker = DatePickerAppCompat()
      picker.listener = { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
        Toast.makeText(requireContext(), "year:$year,month:$month,dayofMonth:$dayOfMonth", Toast.LENGTH_LONG).show()
      }
      picker.show(childFragmentManager, "DatePickerAppCompat")
    }
    btnCreateMem.setOnClickListener {

    }
  }


  fun imgPicker() {

  }


  fun getPickImageIntent(context: Context): Intent {
    lateinit var chooserIntent: Intent
    val intentList = arrayListOf<Intent>()

    val pickIntent = Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val file = File(context.cacheDir,"images")
    val imgUri = FileProvider.getUriForFile(context,"${context.packageName}.provider",file)
    takePhotoIntent.putExtra("return-data", true)
    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
    addIntentsToList(context, intentList, pickIntent)
    addIntentsToList(context, intentList, takePhotoIntent)
    if (intentList.size > 0) {
      chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1),
          context.getString(R.string.pick_image_intent_title))
      val list = arrayListOf<Parcelable>()
      chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray<Parcelable>())
    }

    return chooserIntent
  }


  fun addIntentsToList(context: Context, list: ArrayList<Intent>, intent: Intent) {
    val resInfoList = context.packageManager.queryIntentActivities(intent, 0)
    for (resInfo in resInfoList) {
      val packageName = resInfo.activityInfo.packageName
      val targetedIntent = Intent(intent)
      targetedIntent.setPackage(packageName);
      list.add(targetedIntent)
    }
  }

}
