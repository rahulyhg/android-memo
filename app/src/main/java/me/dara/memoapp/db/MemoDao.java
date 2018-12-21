package me.dara.memoapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

/**
 * @author sardor
 */
@Dao
public abstract class MemoDao {

  @Insert
  public abstract void insert(List<MemoEntity> entities);

  @Query("SELECT * FROM memo")
  public abstract LiveData<List<MemoEntity>> loadMemos();
}
