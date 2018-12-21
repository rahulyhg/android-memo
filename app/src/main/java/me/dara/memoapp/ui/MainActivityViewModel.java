package me.dara.memoapp.ui;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import java.io.ByteArrayOutputStream;
import java.io.File;
import me.dara.memoapp.AppModule;
import me.dara.memoapp.MemoApp;
import me.dara.memoapp.file.FilePath;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.Memo;

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
    String uid = FirebaseAuth.getInstance().getUid().toLowerCase();
    long currentTime = SystemClock.currentThreadTimeMillis();
    String fileName = uid + "_" + currentTime;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
    return module.fileManager.saveFile(FilePath.UPLOAD, bos.toByteArray(), fileName);
  }
}
