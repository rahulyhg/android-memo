package me.dara.memoapp.ui;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
  public final AppModule module;
  private MemoApp app;

  public MainActivityViewModel(@NonNull Application application) {
    super(application);
    app = getApplication();
    module = app.appModule;
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
    return Transformations.map(module.memoService.loadMemos(),
        input -> {
          if (input.getStatus() == Status.SUCCESS) {
            List<Memo> memoList = (List<Memo>) input.getObj();
            return new ApiResponse(EntityUtil.providerFrom(memoList, app.getApplicationContext(),
                module.fileManager), Status.SUCCESS);
          }
          return input;
        });
  }

  public Memo loadMemo(Long id) {
    try {
      return module.executors.IO.submit(() -> {
        Memo memo = EntityUtil.map(module.db.memoDao().loadMemo(id));

        File file = module.fileManager.getFileByName(FilePath.UPLOAD, memo.getFile().getName());
        if (file != null) {
          memo.imgBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return memo;
      }).get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void saveFile(Bitmap bitmap, String fileName) {
    module.fileManager.saveFile(bitmap, fileName);
  }

  public String getFilePath(File file) {
    try {
      File file1 = module.fileManager.getFileByName(FilePath.UPLOAD, file.getName());
      return file1.getAbsolutePath();
    } catch (Throwable throwable) {
      return "";
    }
  }

  public boolean isChanged(Memo memo) {

    Memo localMemo = loadMemo(memo.id);
    if (localMemo == null) {
      return true;
    }
    int hash1 = localMemo.hashCode();
    int hash2 = memo.hashCode();
    return hash1 != hash2;
  }

  public LiveData<Object> exit() {
    MutableLiveData<Object> liveData = new MutableLiveData<Object>();
    module.executors.IO.execute(() -> {
      module.fileManager.deleteAllFiles();
      module.db.memoDao().delete();
      FirebaseAuth.getInstance().signOut();
      liveData.postValue(1);
    });
    return liveData;
  }
}
