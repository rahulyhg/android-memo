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

  @NotNull
  public final MutableLiveData<ApiResponse> signUp(@NotNull String email,
      @NotNull String password) {
    final MutableLiveData<ApiResponse> signupLiveData = new MutableLiveData<ApiResponse>();
    this.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

      if (task.isSuccessful()) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
          user.sendEmailVerification().addOnCompleteListener(emailTask -> {
            if (emailTask.isSuccessful()) {
              signupLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
            } else {
              signupLiveData.setValue(new ApiResponse(null, Status.FAIL));
            }
          });
        } else {
          signupLiveData.setValue(new ApiResponse(null, Status.FAIL));
        }
      } else {
        signupLiveData.setValue(new ApiResponse(null, Status.FAIL));
      }
    });
    return signupLiveData;
  }

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

  public final MutableLiveData<ApiResponse> insertUser(@NotNull User user) {
    final MutableLiveData<ApiResponse> userLiveData = new MutableLiveData<>();
    auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        FirebaseUser fbUser = auth.getCurrentUser();
        if (fbUser != null) {
          fbUser.sendEmailVerification().addOnCompleteListener(emailTask -> {
            if (emailTask.isSuccessful()) {
              user.uid = fbUser.getUid();
              dbReference.child("users").setValue(user).addOnCompleteListener(
                  userSaveTask -> {
                    if (userSaveTask.isSuccessful()) {
                      StorageMetadata metadata = new StorageMetadata.Builder()
                              .setContentType("image/jpg")
                              .build();

                      StorageReference avtarRef =
                          storageRef.child(
                              "images/"+"avatarurl.jpg");
                      avtarRef.putBytes(user.getByteOfBitmap(),metadata).addOnCompleteListener(
                          avatarTask -> {
                            if (avatarTask.isSuccessful()) {
                              userLiveData.postValue(new ApiResponse(null, Status.SUCCESS));
                            } else {
                              Log.i(MemoService.class.getName(), "Failed to upload avatar");
                              // failed upload avatar task
                            }
                          });
                    } else {
                      Log.i(MemoService.class.getName(), "Failed to saving user into DB");
                      // failed saving user into DB
                    }
                  });
            } else {
              Log.i(MemoService.class.getName(), "Failed sending email");
              // failed sending email
            }
          });
        } else {
          Log.i(MemoService.class.getName(), "FbUser is null");
          // fbUser is null
        }
      } else {
        Log.i(MemoService.class.getName(), "Failed creating user");
        // failed creating user
      }
    });
    return userLiveData;
  }

  public final void updateUser() {
  }
}
