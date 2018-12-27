package me.dara.memoapp.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import java.io.FileNotFoundException;
import me.dara.memoapp.R;
import me.dara.memoapp.databinding.FragmentRegisterBinding;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.network.model.User;
import me.dara.memoapp.ui.view.Alert;
import me.dara.memoapp.ui.view.ProgressDialog;
import me.dara.memoapp.util.ImageUtil;

/**
 * @author sardor
 */

/**
 * Register screen fragment
 */
public class RegisterFragment extends Fragment {

  FragmentRegisterBinding binding;
  AuthCallback callback;
  AuthViewModel viewModel;
  ProgressDialog progress;

  @Override public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    callback = (AuthCallback) context;
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
    return binding.getRoot();
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = ViewModelProviders.of(requireActivity()).get(AuthViewModel.class);
    progress = new ProgressDialog();
    progress.setCancelable(false);

    binding.imgCloseRegister.setOnClickListener(v -> {
      callback.onCloseClicked();
      clearFields();
    });

    binding.btnRegister.setOnClickListener(v -> {
      String email = binding.editRegisterEmail.getText().toString();
      String password = binding.editRegisterPassword.getText().toString();
      String confirmPassword = binding.editConfirmPassword.getText().toString();
      if (validateInput(email, password, confirmPassword)) {
        signUp(email, password);
      }
    });

    binding.editConfirmPassword.addTextChangedListener(new FormTextWatcher() {
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
          binding.inputRegisterConfirmPassword.setError("");
        }
      }
    });

    binding.editRegisterEmail.addTextChangedListener(new FormTextWatcher() {
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
          binding.inputRegisterEmail.setError("");
        }
      }
    });
    binding.editRegisterPassword.addTextChangedListener(new FormTextWatcher() {
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
          binding.inputRegisterPassword.setError("");
        }
      }
    });
  }

  private void signUp(String email, String password) {
    User user = new User(email, password, "");
    progress.show(getChildFragmentManager(), "ProgressDialog");
    viewModel.signUp(user).observe(
        getViewLifecycleOwner(), response -> {
          progress.dismiss();
          if (response.getStatus() == Status.SUCCESS) {
            String msg = getString(R.string.email_sent, email);
            String title = getString(R.string.info);
            Alert alert = Alert.newInstance(title, msg);
            alert.listener = () -> {
              clearFields();
              callback.registerSuccess();
            };
            alert.show(getChildFragmentManager(), "Alert");
          } else {
            clearFields();
            Toast.makeText(requireContext(), R.string.registration_error, Toast.LENGTH_SHORT)
                .show();
          }
        });
  }

  private boolean validateInput(String email, String password, String confirmPassword) {
    boolean isValid = true;
    if (email.isEmpty()) {
      binding.inputRegisterEmail.setError(getResources().getString(R.string.fill_username));
      isValid = false;
    }
    if (password.isEmpty()) {
      binding.inputRegisterEmail.setError(getResources().getString(R.string.fill_password));
      isValid = false;
    }
    if (confirmPassword.isEmpty()) {
      binding.inputRegisterEmail.setError(getResources().getString(R.string.fill_password));
      isValid = false;
    }

    return isValid;
  }

  // Method called after user successfully registered to system
  private void clearFields() {
    binding.editConfirmPassword.setText("");
    binding.editRegisterPassword.setText("");
    binding.editRegisterEmail.setText("");
  }
}
