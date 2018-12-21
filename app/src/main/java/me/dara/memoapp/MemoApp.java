package me.dara.memoapp;

import android.app.Application;
import me.dara.memoapp.file.FileManager;
import me.dara.memoapp.network.MemoService;

/**
 * @author sardor
 */
public final class MemoApp extends Application {

   public AppModule appModule;


   public void onCreate() {
      super.onCreate();
      MemoService webService = new MemoService();
      FileManager fileManager = new FileManager(getApplicationContext());
      appModule = new AppModule(webService,fileManager);
   }
}
