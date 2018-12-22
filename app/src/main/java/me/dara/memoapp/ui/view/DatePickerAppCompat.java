package me.dara.memoapp.ui.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.Calendar;
import me.dara.memoapp.R;

/**
 * @author sardor
 */
public class DatePickerAppCompat extends AppCompatDialogFragment {
  public DatePickerDialog.OnDateSetListener listener;

  @NonNull @Override public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    return new DatePickerDialog(requireContext(), R.style.DialogThemeStyle, listener, year, month,
        day);
  }
}
