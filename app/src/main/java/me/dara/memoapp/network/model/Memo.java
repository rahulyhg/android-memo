package me.dara.memoapp.network.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sardor
 */

@IgnoreExtraProperties
public class Memo {

  public Long id;
  public Long createdTime;
  public Map<String, Boolean> todoList;
  public String title;
  public String description;
  public String downloadUrl;

  @Exclude
  public File file;

  public Memo() {

  }

  public Memo(Map<String, Boolean> todoList, String title, String description,
      Long createdTime,
      File file) {
    this.id = createdTime;
    this.todoList = todoList;
    this.title = title;
    this.description = description;
    this.createdTime = createdTime;
    this.file = file;
  }

  @Exclude
  public Map<String, Object> toMap() {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("todo", todoList);
    map.put("title", title);
    map.put("description", description);
    map.put("date", createdTime);
    map.put("downloadUrl", downloadUrl);
    map.put("id", id);
    return map;
  }
}
