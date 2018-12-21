package me.dara.memoapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import me.dara.memoapp.R;
import me.dara.memoapp.ui.memoCreate.MemoCreateFragment;

/**
 * @author sardor
 */
public class MainActivity extends AppCompatActivity {

  Fragment  memoCreate;

  @Override protected void onCreate( Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    memoCreate = new MemoCreateFragment();
    getSupportFragmentManager().beginTransaction()
        .add(R.id.frame_main, memoCreate)
        .addToBackStack(null)
        .commit();
  }
}
