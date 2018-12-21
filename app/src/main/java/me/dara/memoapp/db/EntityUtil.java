package me.dara.memoapp.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.dara.memoapp.network.model.Memo;

/**
 * @author sardor
 */
public class EntityUtil {

  public static List<Memo> map(List<MemoEntity> list) {
    List<Memo> newList = new ArrayList<>();
    for (MemoEntity entity : list) {
      newList.add(map(entity));
    }
    return newList;
  }

  public static Memo map(MemoEntity entity) {
    return new Memo(entity.todoList, entity.title, entity.description, entity.createdTime,
        new File(entity.downloadUrl));
  }

  public static MemoEntity map(Memo memo) {
    return new MemoEntity(memo.todoList, memo.title, memo.description, memo.createdTime,
        memo.downloadUrl);
  }

  public static List<MemoEntity> transform(List<Memo> list) {
    List<MemoEntity> newList = new ArrayList<>();
    for (Memo memo : list) {
      newList.add(map(memo));
    }
    return newList;
  }
}
