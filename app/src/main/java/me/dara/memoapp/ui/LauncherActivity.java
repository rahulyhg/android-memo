package me.dara.memoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.dara.memoapp.AppModule;
import me.dara.memoapp.MemoApp;
import me.dara.memoapp.R;
import me.dara.memoapp.network.MemoService;
import me.dara.memoapp.ui.auth.AuthActivity;

/**
 * @author sardor
 */

/**
 * Main entry class where checks for user authorization
 */
public class LauncherActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    MemoApp app = ((MemoApp) getApplication());
    AppModule appModule = app.appModule;
    MemoService webService = appModule.memoService;
    boolean isAuthorized = webService.checkForAuth();
    Intent intent;
    if (isAuthorized) {
      intent = new Intent(this, AuthActivity.class);
    } else {
      intent = new Intent(this, MainActivity.class);
    }
    Handler handler = new Handler();
    handler.postDelayed(() -> {
      startActivity(intent);
      finish();
    }, 1000);
  }
}
