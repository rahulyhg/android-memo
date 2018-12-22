package me.dara.memoapp.db;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import me.dara.memoapp.file.FileManager;
import me.dara.memoapp.file.FilePath;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.ui.memoList.MemoProvider;

/**
 * @author sardor
 */
public class EntityUtil {

  public static List<Memo> map(List<MemoEntity> list) {
    List<Memo> newList = new ArrayList<>();
    for (MemoEntity entity : list) {
      newList.add(map(entity));
    }
    return newList;
  }

  public static Memo map(MemoEntity entity) {
    return new Memo(entity.todoList, entity.title, entity.description, entity.createdTime,
        entity.downloadUrl);
  }

  public static MemoEntity map(Memo memo) {
    return new MemoEntity(memo.todoList, memo.title, memo.description, memo.createdTime,
        memo.downloadUrl);
  }

  public static List<MemoEntity> transform(List<Memo> list) {
    List<MemoEntity> newList = new ArrayList<>();
    for (Memo memo : list) {
      newList.add(map(memo));
    }
    return newList;
  }

  public static List<MemoProvider> providerFrom(List<Memo> list, Context context,
      FileManager fileManager) {
    List<MemoProvider> newList = new ArrayList<>();
    for (Memo memo : list) {
      newList.add(providerFrom(memo, context, fileManager));
    }
    return newList;
  }

  public static MemoProvider providerFrom(Memo memo, Context context, FileManager fileManager) {
    LinearLayout linearLayout = new LinearLayout(context);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    linearLayout.setLayoutParams(params);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    for (Map.Entry<String, Boolean> entry : memo.todoList.entrySet()) {
      CheckBox todo = new CheckBox(context);
      todo.setLayoutParams(params);
      todo.setEnabled(false);
      todo.setChecked(entry.getValue());
      todo.setText(entry.getKey());
      linearLayout.addView(todo);
    }

    String fileName = "/" + memo.getFile().getName();
    File localFile = fileManager.getFileByName(FilePath.UPLOAD, fileName);
    String providerUrl = "";

    MemoProvider memoProvider = new MemoProvider();
    memoProvider.todoView = linearLayout;
    memoProvider.id = memo.id;
    memoProvider.description = memo.description;
    memoProvider.title = memo.title;
    memoProvider.createdAt =
        DateUtils.formatDateTime(context, memo.createdTime, DateUtils.FORMAT_SHOW_TIME);
    if (localFile != null) {
      memoProvider.isDownloadedFile = true;
      providerUrl = localFile.getAbsolutePath();
      memoProvider.downloadUrl = providerUrl;
    } else {
      memoProvider.isDownloadedFile = false;
      StorageReference reference = FirebaseStorage.getInstance()
          .getReference(
          memo.getFile().getAbsolutePath());
      memoProvider.fileReference = reference;
    }
    return memoProvider;
  }
}
