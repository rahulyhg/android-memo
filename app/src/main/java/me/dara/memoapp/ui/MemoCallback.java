package me.dara.memoapp.ui;

import android.widget.ImageView;
import com.google.firebase.storage.StorageReference;

/**
 * @author sardor
 */
public interface MemoCallback {
  void onMemoCreated();

  void onMemoClicked(long id);

  void loadImg(StorageReference reference, ImageView imageView);
}
