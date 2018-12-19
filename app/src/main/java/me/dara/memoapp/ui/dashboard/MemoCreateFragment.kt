package me.dara.memoapp.ui.dashboard

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_memo_create.*

import me.dara.memoapp.R
import me.dara.memoapp.ui.view.DatePickerAppCompat

class MemoCreateFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_memo_create, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    imgMemo.setOnClickListener {

    }
    dateMemo.setOnClickListener {
      val picker = DatePickerAppCompat()
      picker.listener = { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
        Toast.makeText(requireContext(), "year:$year,month:$month,dayofMonth:$dayOfMonth", Toast.LENGTH_LONG).show()
      }
      picker.show(childFragmentManager, "DatePickerAppCompat")
    }


  }


  fun imgPicker() {

  }


}
