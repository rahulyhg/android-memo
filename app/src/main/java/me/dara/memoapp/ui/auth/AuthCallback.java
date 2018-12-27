package me.dara.memoapp.ui.auth;

/**
 * Class for listening changes in register and login screen
 * */
public interface AuthCallback {

  void onRegisterClicked();

  void registerSuccess();

  void onCloseClicked();

  void loginSuccess();
}
