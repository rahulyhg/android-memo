package me.dara.memoapp.db;

import android.content.Context;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author sardor
 */
@Entity(tableName = "memo")
public class MemoEntity {

  @PrimaryKey
  @ColumnInfo(name = "id")
  long id;
  @ColumnInfo(name = "created_time")
  public Long createdTime;
  @TypeConverters(Converters.class)
  public Map<String, Boolean> todoList;
  @ColumnInfo(name = "title")
  public String title;
  @ColumnInfo(name = "description")
  public String description;
  @ColumnInfo(name = "downloadUrl")
  public String downloadUrl;


  @Ignore
  public MemoEntity(Map<String, Boolean> todoList, String title, String description,
      Long createdTime,
      String downloadUrl) {
    this.id = createdTime;
    this.todoList = todoList;
    this.title = title;
    this.description = description;
    this.createdTime = createdTime;
    this.downloadUrl = downloadUrl;
  }

  public MemoEntity() {
  }
}
