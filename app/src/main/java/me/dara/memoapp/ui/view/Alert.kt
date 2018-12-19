package me.dara.memoapp.ui.view

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import me.dara.memoapp.R

/**
 * @author sardor
 */
class Alert : AppCompatDialogFragment() {

  var title: String = ""
  var msg = ""

  var positiveFun: () -> Unit = {}

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

    val dialog = AlertDialog.Builder(requireContext()).setTitle(title)
        .setMessage(msg)
        .setPositiveButton(android.R.string.yes) { dialog, _ ->
          dialog.dismiss()
          positiveFun()
        }
        .create()
    dialog.setOnShowListener {
      dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
          ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorPrimary)))
      dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
          ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorPrimary)))
    }
    return dialog
  }
}