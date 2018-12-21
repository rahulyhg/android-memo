package me.dara.memoapp.network.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.File;
import java.util.List;

/**
 * @author sardor
 */

@IgnoreExtraProperties
public class Memo {

  public List<String> todoList;
  public String title;
  public String description;
  @Exclude
  public File file;
  public Long date;
  public String memoFilePath;

  public Memo() {

  }

  public Memo(List<String> todoList, String title, String description, Long date, File file) {
    this.todoList = todoList;
    this.title = title;
    this.description = description;
    this.date = date;
    this.file = file;
  }
}
