package me.dara.memoapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import me.dara.memoapp.network.model.Memo;

/**
 * @author sardor
 */
@Dao
public abstract class MemoDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insert(List<MemoEntity> entities);

  @Query("SELECT * FROM memo")
  public abstract LiveData<List<MemoEntity>> loadMemos();

  @Query("SELECT * FROM memo WHERE id =:id")
  public abstract MemoEntity loadMemo(Long id);

  @Query("DELETE FROM memo")
  public abstract void delete();
}
