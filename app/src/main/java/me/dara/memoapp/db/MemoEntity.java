package me.dara.memoapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author sardor
 */

/**
 * memo table containing MemoEntity model
 */
@Entity(tableName = "memo")
public class MemoEntity {

  @PrimaryKey
  @ColumnInfo(name = "id")
  Long id;
  @ColumnInfo(name = "created_time")
  public Long createdTime;
  @ColumnInfo(name = "title")
  public String title;
  @ColumnInfo(name = "description")
  public String description;
  @ColumnInfo(name = "downloadUrl")
  public String downloadUrl;

  @Ignore
  public MemoEntity(String title, String description,
      Long createdTime,
      String downloadUrl, Long id) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.createdTime = createdTime;
    this.downloadUrl = downloadUrl;
  }

  public MemoEntity() {
  }
}
