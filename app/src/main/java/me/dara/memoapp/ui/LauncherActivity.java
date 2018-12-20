package me.dara.memoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.dara.memoapp.MemoApp;
import me.dara.memoapp.R;
import me.dara.memoapp.ui.auth.AuthActivity;

/**
 * @author sardor
 */
public class LauncherActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    boolean isAuthorized = true;//((MemoApp) getApplication()).appModule.webService.checkForAuth();
    Intent intent;
    if (isAuthorized) {
      intent = new Intent(this, AuthActivity.class);
    } else {
      intent = new Intent(this, MainActivity.class);
    }
    new Handler().postDelayed(() -> {
      finish();
      startActivity(intent);
    }, 1000);
  }
}
