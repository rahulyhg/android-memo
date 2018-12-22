package me.dara.memoapp;

import java.util.concurrent.Executors;
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
  public AppExecutors executors;

  public AppModule(MemoService memoService, FileManager fileManager, MemoDb db,
      AppExecutors executors) {
    this.memoService = memoService;
    this.fileManager = fileManager;
    this.db = db;
    this.executors = executors;
  }
}
