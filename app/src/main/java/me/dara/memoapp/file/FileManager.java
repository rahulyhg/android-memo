package me.dara.memoapp.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author sardor
 */
public class FileManager {
  File rootFile;
  File thumbnail;
  File uploadFile;

  public FileManager(Context context) {
    rootFile = context.getExternalFilesDir(null);
    thumbnail = new File(rootFile, "thumbnails");
    uploadFile = new File(rootFile, "uploads");
    if (!thumbnail.exists()) {
      thumbnail.mkdir();
    }
    if (!uploadFile.exists()) {
      uploadFile.mkdir();
    }
  }

  public File saveFile(FilePath path, byte[] data, String fileName) {
    File file;
    if (path.equals(FilePath.UPLOAD)) {
      file = new File(uploadFile, fileName);
    } else {
      file = new File(thumbnail, fileName);
    }
    try {
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(data);
      fos.flush();
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      Log.i(FileManager.class.getName(), e.getLocalizedMessage());
      return file;
    } catch (IOException e) {
      e.printStackTrace();
      Log.i(FileManager.class.getName(), e.getLocalizedMessage());
      return file;
    }
    return file;
  }

  public File getFileByName(FilePath path, String name) {
    if (path.equals(FilePath.UPLOAD)) {
      for (File file : uploadFile.listFiles()) {
        if (file.getName().contains(name)) {
          return file;
        }
      }
    } else {
      for (File file : thumbnail.listFiles()) {
        if (file.getName().contains(name)) {
          return file;
        }
      }
    }
    return null;
  }

  public File saveFile(Bitmap bitmap,String name) {
    if (bitmap == null) {
      return null;
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
    return saveFile(FilePath.UPLOAD, bos.toByteArray(), name);
  }

}


