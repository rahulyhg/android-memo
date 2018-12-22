package me.dara.memoapp.ui.memoList;

import android.widget.LinearLayout;
import com.google.firebase.storage.StorageReference;

/**
 * @author sardor
 */
public class MemoProvider {
  public LinearLayout todoView;
  public Long id;
  public String createdAt;
  public String title;
  public String description;
  public String downloadUrl;
  public boolean isDownloadedFile;
  public StorageReference fileReference;

}
