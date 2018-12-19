package me.dara.memoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import me.dara.memoapp.MemoApp
import me.dara.memoapp.R
import me.dara.memoapp.ui.auth.AuthActivity

class LauncherActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_launcher)
    val intent = if ((application as MemoApp).appModule.webService.checkForAuth()) {
      Intent(this, AuthActivity::class.java)
    } else {
      Intent(this, MainActivity::class.java)
    }
    Handler().postDelayed({
      finish()
      startActivity(intent)
    }, 1000)

  }
}
