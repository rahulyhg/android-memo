package me.dara.memoapp.ui.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatDialogFragment
import me.dara.memoapp.R

import java.util.*

/**
 * @author sardor
 */
class DatePickerAppCompat : AppCompatDialogFragment(), DatePickerDialog.OnDateSetListener {


  lateinit var listener: (view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) -> Unit

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(requireContext(), R.style.DialogThemeStyle,this, year, month, day)
  }

  override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
    listener(view, year, month, dayOfMonth)
  }

}