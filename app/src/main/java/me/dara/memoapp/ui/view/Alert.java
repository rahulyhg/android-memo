package me.dara.memoapp.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import me.dara.memoapp.R;

import static androidx.core.os.LocaleListCompat.create;

/**
 * @author sardor
 */
public class Alert extends AppCompatDialogFragment {

  String title = "";
  String msg = "";
  OnAlertCallback listener;

  @NonNull @Override public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    Dialog dg = new AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(android.R.string.yes, (dialog1, which) -> {
          dialog1.dismiss();
          listener.positiveBtnClicked();
        }).create();

    dg.setOnShowListener(dialog -> {

      ((AlertDialog) dg).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
          ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorPrimary)));
      ((AlertDialog) dg).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
          ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorPrimary)));
    });
    return dg;
  }
}

interface OnAlertCallback {
  void positiveBtnClicked();
}
