package me.dara.memoapp.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author sardor
 */
@Database(entities = {MemoEntity.class}, version = 1,exportSchema = false)
public abstract class MemoDb extends RoomDatabase {

  public abstract MemoDao memoDao();

  private static MemoDb db;

  private static final String DB_NAME = "memo.db";

  public static MemoDb getInstance(Context context) {
    if (db == null) {
      db = Room.databaseBuilder(context, MemoDb.class, DB_NAME)
          .fallbackToDestructiveMigration()
          .build();
    }
    return db;
  }
}
