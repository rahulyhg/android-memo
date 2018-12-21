package me.dara.memoapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import me.dara.memoapp.R;
import me.dara.memoapp.ui.memoCreate.MemoCreateFragment;
import me.dara.memoapp.ui.memoList.MemoListFragment;

/**
 * @author sardor
 */
public class MainActivity extends AppCompatActivity implements MemoCallback {

  Fragment memoCreate, memoList;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    memoCreate = new MemoCreateFragment();
    memoList = new MemoListFragment();
    replaceFragment(memoList);
  }

  @Override public void onMemoCreated() {

  }

  @Override public void onMemoClicked(long id) {

  }

  public void addFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.frame_main, fragment)
        .addToBackStack(null)
        .commit();
  }

  public void replaceFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.frame_main, fragment)
        .addToBackStack(null)
        .commit();
  }
}
