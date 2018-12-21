package me.dara.memoapp.network;

import android.net.Uri;
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
import me.dara.memoapp.R;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.network.model.User;

/**
 * @author sardor
 */
public final class MemoService {

  @NonNull
  private FirebaseAuth auth = FirebaseAuth.getInstance();

  @NonNull
  FirebaseDatabase db = FirebaseDatabase.getInstance();

  private DatabaseReference dbReference = db.getReference();

  @NonNull
  FirebaseStorage storage = FirebaseStorage.getInstance();

  @NonNull
  StorageReference storageRef = storage.getReference();

  FirebaseUser user = auth.getCurrentUser();

  public final boolean checkForAuth() {
    return auth.getCurrentUser() == null;
  }

  @NonNull
  public final LiveData<ApiResponse> signIn(@NonNull String email, @NonNull String password) {
    final MutableLiveData<ApiResponse> signInLiveData = new MutableLiveData<>();
    this.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
      user = auth.getCurrentUser();
      if (task.isSuccessful()) {
        if (user != null) {
          if (user.isEmailVerified()) {
            signInLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
          } else {
            signInLiveData.setValue(new ApiResponse(R.string.email_not_verified, Status.FAIL));
          }
        }
      } else {
        signInLiveData.setValue(new ApiResponse(null, Status.FAIL));
      }
    });
    return signInLiveData;
  }

  public final MutableLiveData<ApiResponse> signUp(@NonNull User nUser) {
    final MutableLiveData<ApiResponse> userLiveData = new MutableLiveData<>();
    auth.createUserWithEmailAndPassword(nUser.email, nUser.password).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        user = auth.getCurrentUser();
        if (user != null) {
          user.sendEmailVerification().addOnCompleteListener(emailTask -> {
            if (emailTask.isSuccessful()) {
              nUser.uid = user.getUid();
              StorageMetadata metadata = new StorageMetadata.Builder()
                  .setContentType("image/jpg")
                  .build();
              StorageReference avtarRef =
                  storageRef.child(
                      "/user/thumbnails/"
                          + nUser.uid + "/" + nUser.uid.toLowerCase() + "avatar_url.jpg");
              avtarRef.putBytes(nUser.getByteOfBitmap(), metadata).addOnCompleteListener(
                  avatarTask -> {
                    if (avatarTask.isSuccessful()) {
                      nUser.photoUrl = avatarTask.getResult().getMetadata().getPath();
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

  public final LiveData<ApiResponse> postMemo(Memo memo) {
    final MutableLiveData<ApiResponse> memoLiveData = new MutableLiveData<>();
    if (user != null) {
      StorageMetadata metadata = new StorageMetadata.Builder()
          .setContentType("image/jpg")
          .build();
      StorageReference fileRef =
          storageRef.child(
              "/user/uploads/"
                  + user.getUid() + "/" + memo.file.getName());
      fileRef.putFile(Uri.fromFile(memo.file), metadata).addOnCompleteListener(
          fileTask -> {
            if (fileTask.isSuccessful()) {
              Log.i(MemoService.class.getName(), "Memo file upload succeed");
              dbReference.child("memos").setValue(memo).addOnCompleteListener(
                  memoTask -> {
                    if (memoTask.isSuccessful()) {
                      Log.i(MemoService.class.getName(), "Memo upload succeed");
                      memoLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
                    } else {
                      Log.i(MemoService.class.getName(), "Memo upload failed");
                      memoLiveData.setValue(new ApiResponse(null, Status.FAIL));
                    }
                  });
            } else {
              Log.i(MemoService.class.getName(), "Memo file upload failed");
              memoLiveData.setValue(new ApiResponse(null, Status.FAIL));
            }
          });
    }
    return memoLiveData;
  }
}
