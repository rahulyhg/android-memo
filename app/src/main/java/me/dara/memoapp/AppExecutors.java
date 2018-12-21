package me.dara.memoapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sardor
 */
public class AppExecutors {

  public ExecutorService IO;
  public ExecutorService disk;

  public AppExecutors() {
    IO = Executors.newSingleThreadExecutor();
    disk = Executors.newSingleThreadExecutor();
  }
}
