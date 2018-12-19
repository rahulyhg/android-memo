package me.dara.memoapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.dara.memoapp.ui.MainActivity
import me.dara.memoapp.R

class AuthActivity : AppCompatActivity(), OnLoginClickListener {


  val registerFragment: RegisterFragment by lazy {
    RegisterFragment()
  }
  val loginFragment: LoginFragment by lazy {
    LoginFragment()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_auth)

    supportFragmentManager.beginTransaction().replace(R.id.frame_auth, loginFragment).addToBackStack(null).commit()
  }

  override fun registerSuccess() {
    onBackPressed()
  }

  override fun onCloseClicked() {
    supportFragmentManager.popBackStack()
  }

  override fun loginSuccess() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
  }

  override fun onRegisterClicked() {
    supportFragmentManager.beginTransaction()
        .add(R.id.frame_auth, registerFragment).addToBackStack(null).commit()
  }

}

interface OnLoginClickListener {
  fun onRegisterClicked()
  fun registerSuccess()
  fun onCloseClicked()
  fun loginSuccess()
}

