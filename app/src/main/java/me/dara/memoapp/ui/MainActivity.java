package me.dara.memoapp.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import me.dara.memoapp.R;
import me.dara.memoapp.ui.memoCreate.MemoCreateFragment;

/**
 * @author sardor
 */
public class MainActivity extends AppCompatActivity {

  Fragment trending, dashboard, memoCreate;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    memoCreate = new MemoCreateFragment();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.frameMain, memoCreate)
        .addToBackStack(null)
        .commit();
  }
}
