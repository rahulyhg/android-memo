package me.dara.memoapp.ui;

import android.widget.ImageView;
import com.google.firebase.storage.StorageReference;

/**
 * @author sardor
 */
public interface MemoCallback {
  void onMemoCreated();

  void onMemoClicked(Long id);

  void onBackPressed();

  void onCreateMemo();

  void exitToApp();
}
