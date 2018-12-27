package me.dara.memoapp.network.model;

import android.graphics.Bitmap;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.ByteArrayOutputStream;

/**
 * @author ulugbek
 */
@IgnoreExtraProperties
public final class User {
  public String email;
  public String password;
  public String uid;


  public User( String email,  String password,
      String uid) {
    this.email = email;
    this.password = password;
    this.uid = uid;
  }

  public User() {
    this("", "", "");
  }

}