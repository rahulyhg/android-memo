package me.dara.memoapp.ui.trending


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.dara.memoapp.R


/**
 * A simple [Fragment] subclass.
 *
 */
class TrendingFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_trending, container, false)
  }


}
