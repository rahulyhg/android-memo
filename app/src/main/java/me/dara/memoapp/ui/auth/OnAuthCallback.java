package me.dara.memoapp.ui.auth;

public interface OnAuthCallback {

  void onRegisterClicked();

  void registerSuccess();

  void onCloseClicked();

  void loginSuccess();
}
