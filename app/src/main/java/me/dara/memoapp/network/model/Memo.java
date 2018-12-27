package me.dara.memoapp.network.model;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ulugbek
 */
/**
 * Memo class for posting FireBaseDatabase
 * */
@IgnoreExtraProperties
public class Memo {

  public Long id;
  public Long createdTime = -1L;
  public String title;
  public String description;
  public String downloadUrl;

  @Exclude
  public Bitmap imgBitmap;

  public Memo() {

  }

  public Memo(String title, String description,
      Long createdTime,
      String downloadUrl, Long id) {
    this.title = title;
    this.description = description;
    this.createdTime = createdTime;
    this.downloadUrl = downloadUrl;
    this.id = id;
  }

  @Exclude
  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  @Exclude
  public File getFile() {
    if (downloadUrl == null) {
      return null;
    }
    return new File(Uri.parse(downloadUrl).getLastPathSegment());
  }

  @Exclude
  public Map<String, Object> toMap() {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("title", title);
    map.put("description", description);
    map.put("date", createdTime);
    map.put("downloadUrl", downloadUrl);
    map.put("id", id);
    return map;
  }

  @Override public int hashCode() {
    return id.hashCode()
        + createdTime.hashCode()
        + title.hashCode()
        + description.hashCode()
        + downloadUrl.hashCode();
  }
}
