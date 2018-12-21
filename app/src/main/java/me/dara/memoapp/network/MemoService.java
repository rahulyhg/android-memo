package me.dara.memoapp.network;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import me.dara.memoapp.AppExecutors;
import me.dara.memoapp.R;
import me.dara.memoapp.db.EntityUtil;
import me.dara.memoapp.db.MemoDb;
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
  private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

  @NonNull
  private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

  @Nullable
  private FirebaseUser user = auth.getCurrentUser();

  @NonNull
  private AppExecutors executors;

  @NonNull MemoDb db;

  public MemoService(MemoDb db, AppExecutors executors) {
    this.executors = executors;
    this.db = db;
  }

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
                      dbReference.child("users")
                          .child(user.getUid().toLowerCase())
                          .setValue(user)
                          .addOnCompleteListener(
                              userSaveTask -> {
                                if (userSaveTask.isSuccessful()) {
                                  userLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
                                } else {
                                  userLiveData.setValue(new ApiResponse(null, Status.FAIL));
                                  Log.i(MemoService.class.getName(),
                                      "Failed to saving user into DB");
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
              memo.downloadUrl = fileTask.getResult().getMetadata().getPath();
              dbReference.child("memos")
                  .child(user.getUid().toLowerCase())
                  .child(memo.createdTime.toString())
                  .setValue(memo)
                  .addOnCompleteListener(
                      memoTask -> {
                        if (memoTask.isSuccessful()) {
                          Log.i(MemoService.class.getName(), "Memo upload succeed");
                          memoLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
                        } else {
                          Log.i(MemoService.class.getName(), "Memo upload failed");
                          memoLiveData.setValue(new ApiResponse(null, Status.FAIL));
                        }
                      });
              //Map<String, Object> memoValues = memo.toMap();
              //Map<String, Object> childUpdates = new HashMap<>();
              //childUpdates.put("/memos/" + key, memoValues);
              //dbReference.updateChildren(childUpdates).addOnCompleteListener(
              //    memoTask -> {
              //      if (memoTask.isSuccessful()) {
              //        Log.i(MemoService.class.getName(), "Memo upload succeed");
              //        memoLiveData.setValue(new ApiResponse(null, Status.SUCCESS));
              //      } else {
              //        Log.i(MemoService.class.getName(), "Memo upload failed");
              //        memoLiveData.setValue(new ApiResponse(null, Status.FAIL));
              //      }
              //    });
            } else {
              Log.i(MemoService.class.getName(), "Memo file upload failed");
              memoLiveData.setValue(new ApiResponse(null, Status.FAIL));
            }
          });
    }
    return memoLiveData;
  }

  private MutableLiveData<ApiResponse> loadMemosFromService() {
    MutableLiveData<ApiResponse> memoLiveData = new MutableLiveData<>();
    dbReference.child("memos/" + user.getUid().toLowerCase())
        .orderByKey()
        .addListenerForSingleValueEvent(
            new ValueEventListener() {
              @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Memo> memoList = new ArrayList<>();
                for (DataSnapshot memoSnapshot : dataSnapshot.getChildren()) {
                  memoList.add(memoSnapshot.getValue(Memo.class));
                }
                memoLiveData.setValue(new ApiResponse(memoList, Status.SUCCESS));
              }

              @Override public void onCancelled(@NonNull DatabaseError databaseError) {
                memoLiveData.setValue(new ApiResponse(null, Status.FAIL));
              }
            });
    return memoLiveData;
  }

  public LiveData<ApiResponse> loadMemos() {
    MediatorLiveData<ApiResponse> mediatorLiveData = new MediatorLiveData<>();
    LiveData<ApiResponse> dbSource = loadMemosFromLocal();
    MutableLiveData<ApiResponse> serviceSource = loadMemosFromService();

    mediatorLiveData.addSource(dbSource, dbResponse -> {
      if (dbResponse.getStatus() == Status.SUCCESS) {
        mediatorLiveData.removeSource(dbSource);
        mediatorLiveData.postValue(dbResponse);
        mediatorLiveData.addSource(serviceSource, serviceResponse -> {
          mediatorLiveData.removeSource(serviceSource);
          if (serviceResponse.getStatus() == Status.SUCCESS) {
            executors.IO.execute(() -> {
              Object object = serviceResponse.getObj();
              if (object != null) {
                List<Memo> memoList = (List<Memo>) object;
                db.memoDao().insert(EntityUtil.transform(memoList));
                mediatorLiveData.postValue(new ApiResponse(memoList, Status.SUCCESS));
              }
            });
          } else {
            mediatorLiveData.postValue(new ApiResponse(null, Status.FAIL));
          }
        });
      } else {
        mediatorLiveData.postValue(new ApiResponse(null, Status.FAIL));
      }
    });

    return mediatorLiveData;
  }

  private LiveData<ApiResponse> loadMemosFromLocal() {
    return Transformations.map(db.memoDao().loadMemos(),
        input -> {
          List<Memo> memoList = EntityUtil.map(input);
          return new ApiResponse(memoList, Status.SUCCESS);
        });
  }
}