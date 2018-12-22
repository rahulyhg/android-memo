package me.dara.memoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.firebase.storage.StorageReference;
import me.dara.memoapp.R;
import me.dara.memoapp.ui.memoCreate.MemoCreateFragment;
import me.dara.memoapp.ui.memoList.MemoListFragment;

/**
 * @author sardor
 */
public class MainActivity extends AppCompatActivity implements MemoCallback {

  Fragment memoCreate, memoList;

  final String MEMO_CREATE_FRAGMENT = "Memo create";
  final String MEMO_LIST_FRAGMENT = "Memo list";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    memoList = new MemoListFragment();
    replaceFragment(memoList, MEMO_LIST_FRAGMENT);
  }

  @Override public void onMemoCreated() {
    getSupportFragmentManager().popBackStack();
    ((MemoListFragment) memoList).loadMemos();
  }

  @Override public void onMemoClicked(Long id) {
    memoCreate = new MemoCreateFragment();
    ((MemoCreateFragment) memoCreate).memoId = id;
    addFragment(memoCreate, MEMO_CREATE_FRAGMENT);
  }

  public void addFragment(Fragment fragment, String tag) {
    getSupportFragmentManager().beginTransaction()
        .add(R.id.frame_main, fragment, tag)
        .addToBackStack(null)
        .commit();
  }

  public void replaceFragment(Fragment fragment, String tag) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.frame_main, fragment, tag)
        .commit();
  }

  @Override public void onBackPressed() {
    MemoCreateFragment current =
        (MemoCreateFragment) getSupportFragmentManager().findFragmentByTag(MEMO_CREATE_FRAGMENT);
    if (current != null && current.isVisible()) {
      if (current.memo == null) {
        super.onBackPressed();
      } else {
        current.showWarning();
      }
    } else {
      super.onBackPressed();
    }
  }

  @Override public void onCreateMemo() {
    addFragment(new MemoCreateFragment(), MEMO_CREATE_FRAGMENT);
  }

  @Override public void exitToApp() {
    Intent intent = new Intent(this, LauncherActivity.class);
    startActivity(intent);
    finish();
  }
}
