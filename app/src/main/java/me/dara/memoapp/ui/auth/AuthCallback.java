package me.dara.memoapp.ui.auth;

public interface AuthCallback {

  void onRegisterClicked();

  void registerSuccess();

  void onCloseClicked();

  void loginSuccess();
}
