package me.dara.memoapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import me.dara.memoapp.R;
import me.dara.memoapp.ui.MainActivity;
import org.jetbrains.annotations.Nullable;

/**
 * @author sardor
 */
public final class AuthActivity extends AppCompatActivity implements AuthCallback {

  Fragment register, login;

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    register = new RegisterFragment();
    login = new LoginFragment();

    this.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.frame_auth, login)
        .addToBackStack(null)
        .commit();
  }

  public void registerSuccess() {
    this.onBackPressed();
  }

  public void onCloseClicked() {
    this.getSupportFragmentManager().popBackStack();
  }

  public void loginSuccess() {
    Intent intent = new Intent(this, MainActivity.class);
    this.startActivity(intent);
    this.finish();
  }

  public void onRegisterClicked() {
    this.getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.frame_auth, register)
        .addToBackStack(null)
        .commit();
  }
}

