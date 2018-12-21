package me.dara.memoapp.ui;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Stack;
import me.dara.memoapp.AppModule;
import me.dara.memoapp.MemoApp;
import me.dara.memoapp.db.EntityUtil;
import me.dara.memoapp.db.MemoEntity;
import me.dara.memoapp.file.FilePath;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.network.model.Status;

/**
 * @author sardor
 */
public class MainActivityViewModel extends AndroidViewModel {
  private final AppModule module;

  public MainActivityViewModel(@NonNull Application application) {
    super(application);
    module = ((MemoApp) this.getApplication()).appModule;
  }

  public LiveData<ApiResponse> postMemo(Memo memo) {
    return module.memoService.postMemo(memo);
  }

  public File saveFile(Bitmap bitmap) {
    if (bitmap == null) {
      return null;
    }
    String uid = FirebaseAuth.getInstance().getUid().toLowerCase();
    long currentTime = System.currentTimeMillis();
    String fileName = uid + "_" + currentTime;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
    return module.fileManager.saveFile(FilePath.UPLOAD, bos.toByteArray(), fileName);
  }

  public LiveData<ApiResponse> loadMemos() {
    return module.memoService.loadMemos();
  }
}
