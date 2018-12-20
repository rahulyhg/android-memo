package me.dara.memoapp.network.model;

import android.graphics.Bitmap;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.ByteArrayOutputStream;
import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */
@IgnoreExtraProperties
public final class User {
  public String email;
  public String password;
  public String photoUrl;
  public String uid;

  @Exclude
  public Bitmap photoBitmap;

  public User(@NotNull String email, @NotNull String password, @NotNull String photoUrl,String uid) {
    this.email = email;
    this.password = password;
    this.photoUrl = photoUrl;
    this.uid = uid;
  }

  public User() {
    this("", "", "","");
  }

  @Exclude
  public byte[] getByteOfBitmap() {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
    return bos.toByteArray();
  }
}