package me.dara.memoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author sardor
 */
class LoadingDialog : AppCompatDialogFragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.dialog_loading, container, false)
  }


}