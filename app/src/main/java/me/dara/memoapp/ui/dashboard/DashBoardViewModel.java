package me.dara.memoapp.ui.dashboard;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import me.dara.memoapp.AppModule;
import me.dara.memoapp.MemoApp;
import me.dara.memoapp.network.model.User;
import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */
public final class DashBoardViewModel extends AndroidViewModel {
  @NotNull
  public final AppModule module;

  public final void postUser(@NotNull User user) {
    this.module.webService.insertUser(user);
  }

  public DashBoardViewModel(@NotNull Application application) {
    super(application);
    this.module = ((MemoApp) this.getApplication()).appModule;
  }
}
