package me.dara.memoapp;

import android.app.Application;
import me.dara.memoapp.network.MemoService;
import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */
public final class MemoApp extends Application {
   @NotNull
   public AppModule appModule;

   @NotNull
   public final AppModule getAppModule() {
      return appModule;
   }


   public void onCreate() {
      super.onCreate();
      MemoService webService = new MemoService();
      this.appModule = new AppModule(webService);
   }
}
