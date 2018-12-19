package me.dara.memoapp;

import kotlin.jvm.internal.Intrinsics;
import me.dara.memoapp.network.MemoService;
import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */
public final class AppModule {

  @NotNull
  public MemoService webService;


  public AppModule(@NotNull MemoService webService) {
    this.webService = webService;
  }
}
