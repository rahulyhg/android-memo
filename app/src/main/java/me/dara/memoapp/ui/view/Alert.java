package me.dara.memoapp.ui.view;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import java.io.Serializable;
import me.dara.memoapp.R;
import me.dara.memoapp.ui.auth.AlertCallback;

/**
 * @author sardor
 */
public class Alert extends AppCompatDialogFragment {

  public String title = "";
  public String msg = "";
  public AlertCallback listener;

  public static Alert newInstance(String title, String msg) {
    Bundle args = new Bundle();
    args.putString("msg", msg);
    args.putString("title", title);
    Alert alert = new Alert();
    alert.setArguments(args);
    return alert;
  }

  @NonNull @Override public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Bundle args = getArguments();
    title = args.getString("title");
    msg = args.getString("msg");
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
