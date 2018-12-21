package me.dara.memoapp;

import me.dara.memoapp.db.MemoDb;
import me.dara.memoapp.file.FileManager;
import me.dara.memoapp.network.MemoService;
import me.dara.memoapp.network.model.Memo;

/**
 * @author sardor
 */
public final class AppModule {

  public MemoService memoService;
  public FileManager fileManager;
  public MemoDb db;

  public AppModule(MemoService memoService, FileManager fileManager, MemoDb db) {
    this.memoService = memoService;
    this.fileManager = fileManager;
    this.db = db;
  }
}
