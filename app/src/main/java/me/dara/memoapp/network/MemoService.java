package me.dara.memoapp.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import kotlin.jvm.internal.Intrinsics;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.network.model.User;
import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */
public final class MemoService {

  @NotNull
  private FirebaseAuth auth = FirebaseAuth.getInstance();
  @NotNull
  private FirebaseDatabase database = FirebaseDatabase.getInstance();
  @NotNull
  private DatabaseReference dbReference = database.getReference();

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
    Intrinsics.checkParameterIsNotNull(email, "email");
    Intrinsics.checkParameterIsNotNull(password, "password");
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

  public final void insertUser(@NotNull User user) {
    this.dbReference.child("users").child(user.getEmail()).setValue(user);
  }

  public final void updateUser() {
  }
}
