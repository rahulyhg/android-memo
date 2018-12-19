package me.dara.memoapp.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import me.dara.memoapp.MemoApp

/**
 * @author sardor
 */
class AuthViewModel(app: Application) : AndroidViewModel(app) {

  val module = getApplication<MemoApp>().appModule

  fun signUp(email: String, password: String) = module.webService.signUp(email, password)

  fun signIn(email: String, password: String) = module.webService.signUp(email, password)

}