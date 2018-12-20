package me.dara.memoapp.ui.auth;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import me.dara.memoapp.AppModule;
import me.dara.memoapp.MemoApp;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.User;
import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */

public final class AuthViewModel extends AndroidViewModel {
  @NotNull
  private final AppModule module;

  public final LiveData<ApiResponse> signUp(User user){
    return this.module.webService.signUp(user);
  }

  @NotNull
  public final LiveData<ApiResponse> signIn(@NotNull String email, @NotNull String password) {
    return this.module.webService.signIn(email, password);
  }

  public AuthViewModel(@NotNull Application app) {
    super(app);
    this.module = ((MemoApp) this.getApplication()).appModule;
  }
}
