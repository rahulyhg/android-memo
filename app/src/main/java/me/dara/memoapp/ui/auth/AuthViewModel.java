package me.dara.memoapp.ui.auth;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import me.dara.memoapp.AppModule;
import me.dara.memoapp.MemoApp;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.User;

/**
 * @author sardor
 */

public final class AuthViewModel extends AndroidViewModel {
  private final AppModule module;

  public final LiveData<ApiResponse> signUp(User user){
    return this.module.memoService.signUp(user);
  }

  public final LiveData<ApiResponse> signIn( String email,  String password) {
    return this.module.memoService.signIn(email, password);
  }

  public AuthViewModel( Application app) {
    super(app);
    this.module = ((MemoApp) this.getApplication()).appModule;
  }
}
