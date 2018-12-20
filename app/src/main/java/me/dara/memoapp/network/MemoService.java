package me.dara.memoapp.network;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.network.model.User;
import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */
public final class MemoService {

  @NotNull
  private FirebaseAuth auth = FirebaseAuth.getInstance();

  @NonNull
  FirebaseDatabase db = FirebaseDatabase.getInstance();

  @NotNull
  private DatabaseReference dbReference = db.getReference();

  @NonNull
  FirebaseStorage storage = FirebaseStorage.getInstance();

  @NonNull
  StorageReference storageRef = storage.getReference();

  public final boolean checkForAuth() {
    return this.auth.getCurrentUser() == null;
  }

  @NotNull
  public final LiveData<ApiResponse> signIn(@NotNull String email, @NotNull String password) {
    final MutableLiveData<ApiResponse> signInLiveData = new MutableLiveData<>();
    this.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        signInLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
      } else {
        signInLiveData.setValue(new ApiResponse(null, Status.FAIL));
      }
    });
    return signInLiveData;
  }

  public final MutableLiveData<ApiResponse> signUp(@NotNull User user) {
    final MutableLiveData<ApiResponse> userLiveData = new MutableLiveData<>();
    auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        FirebaseUser fbUser = auth.getCurrentUser();
        if (fbUser != null) {
          fbUser.sendEmailVerification().addOnCompleteListener(emailTask -> {
            if (emailTask.isSuccessful()) {
              user.uid = fbUser.getUid();
              StorageMetadata metadata = new StorageMetadata.Builder()
                  .setContentType("image/jpg")
                  .build();
              StorageReference avtarRef =
                  storageRef.child(
                      "/user/thumbnails/"
                          + user.uid + "/" + user.uid.toLowerCase() + "avatar_url.jpg");
              avtarRef.putBytes(user.getByteOfBitmap(), metadata).addOnCompleteListener(
                  avatarTask -> {
                    if (avatarTask.isSuccessful()) {
                      user.photoUrl = avatarTask.getResult().getMetadata().getPath();
                      dbReference.child("users").setValue(user).addOnCompleteListener(
                          userSaveTask -> {
                            if (userSaveTask.isSuccessful()) {
                              userLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
                            } else {
                              userLiveData.setValue(new ApiResponse(null, Status.FAIL));
                              Log.i(MemoService.class.getName(), "Failed to saving user into DB");
                            }
                          });
                    } else {
                      Log.i(MemoService.class.getName(), "Failed to upload avatar");
                      userLiveData.setValue(new ApiResponse(null, Status.FAIL));
                    }
                  });
            } else {
              Log.i(MemoService.class.getName(), "Failed sending email");
              userLiveData.setValue(new ApiResponse(null, Status.FAIL));
            }
          });
        } else {
          Log.i(MemoService.class.getName(), "FbUser is null");
          userLiveData.setValue(new ApiResponse(null, Status.FAIL));
        }
      } else {
        Log.i(MemoService.class.getName(), "Failed creating user");
        userLiveData.setValue(new ApiResponse(null, Status.FAIL));
      }
    });
    return userLiveData;
  }

  public final void updateUser() {
  }
}
