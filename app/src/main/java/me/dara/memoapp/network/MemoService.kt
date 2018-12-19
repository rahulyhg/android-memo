package me.dara.memoapp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import me.dara.memoapp.network.model.ApiResponse
import me.dara.memoapp.network.model.Status

/**
 * @author sardor
 */
class MemoService {

  val auth: FirebaseAuth = FirebaseAuth.getInstance()


  fun signUp(email: String, password: String): LiveData<ApiResponse<Any>> {
    val signupLiveData = MutableLiveData<ApiResponse<Any>>()
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        val user = auth.currentUser
        if (user != null) {
          user.sendEmailVerification().addOnCompleteListener { emailTask ->
            if (emailTask.isSuccessful) {
              signupLiveData.value = ApiResponse(null, Status.SUCCESS)
            } else {
              signupLiveData.value = ApiResponse(null, Status.FAIL)
            }
          }
        } else {
          signupLiveData.value = ApiResponse(null, Status.FAIL)
        }
      } else {
        signupLiveData.value = ApiResponse(null, Status.FAIL)
      }
    }
    return signupLiveData
  }

  fun checkForAuth() = auth.currentUser == null

  fun signIn(email: String, password: String): LiveData<ApiResponse<Any>> {
    val signInLiveData = MutableLiveData<ApiResponse<Any>>()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        signInLiveData.value = ApiResponse(null, Status.SUCCESS)
      } else {
        signInLiveData.value = ApiResponse(null, Status.FAIL)
      }
    }
    return signInLiveData
  }

}