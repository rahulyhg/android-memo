package me.dara.memoapp;

import android.app.Application;
import com.squareup.picasso.Picasso;
import me.dara.memoapp.db.MemoDb;
import me.dara.memoapp.file.FileManager;
import me.dara.memoapp.network.MemoService;

/**
 * @author sardor
 */
public final class MemoApp extends Application {

  public AppModule appModule;

  public void onCreate() {
    super.onCreate();
    FileManager fileManager = new FileManager(getApplicationContext());
    MemoDb db = MemoDb.getInstance(getApplicationContext());
    AppExecutors executors = new AppExecutors();
    MemoService webService = new MemoService(db, executors);
    appModule = new AppModule(webService, fileManager, db, executors);
  }
}
