package me.dara.memoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.dara.memoapp.R
import me.dara.memoapp.ui.dashboard.DashBoardFragment
import me.dara.memoapp.ui.dashboard.MemoCreateFragment
import me.dara.memoapp.ui.trending.TrendingFragment


class MainActivity : AppCompatActivity() {

  val trendingFragment by lazy {
    TrendingFragment()
  }
  val dashBoardFragment by lazy {
    DashBoardFragment()
  }
  val memoCreateFragment by lazy {
    MemoCreateFragment()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportFragmentManager.beginTransaction().replace(R.id.frameMain, memoCreateFragment)
        .addToBackStack(null).commit()
  }
}
