package me.dara.memoapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ulugbek
 */

/**
* This class provides with executors for background tasks
*
* */
public class AppExecutors {

  public ExecutorService IO;
  public ExecutorService disk;

  public AppExecutors() {
    IO = Executors.newSingleThreadExecutor();
    disk = Executors.newSingleThreadExecutor();
  }
}
