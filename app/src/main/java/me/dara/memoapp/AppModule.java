package me.dara.memoapp;

import me.dara.memoapp.file.FileManager;
import me.dara.memoapp.network.MemoService;

/**
 * @author sardor
 */
public final class AppModule {

  public MemoService memoService;
  public FileManager fileManager;

  public AppModule(MemoService memoService, FileManager fileManager) {
    this.memoService = memoService;
    this.fileManager = fileManager;
  }
}
