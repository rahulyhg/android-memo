package me.dara.memoapp.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import me.dara.memoapp.R;
import me.dara.memoapp.databinding.FragmentLoginBinding;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.ui.view.ProgressDialog;

/**
 * @author sardor
 */
/**
 * Login screen fragment
 * */
public class LoginFragment extends Fragment {

  AuthCallback callback;
  AuthViewModel viewModel;
  FragmentLoginBinding binding;
  ProgressDialog progress;

  @Override public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    callback = (AuthCallback) context;
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
    return binding.getRoot();
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = ViewModelProviders.of(requireActivity()).get(AuthViewModel.class);
    progress = new ProgressDialog();
    progress.setCancelable(false);
    String textRegister = getResources().getString(R.string.text_register);
    String register = getResources().getString(R.string.register);
    SpannableString spannableString = new SpannableString(textRegister);
    int startIndex = textRegister.indexOf(register);
    int endIndex = startIndex + register.length();
    spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
    binding.textRegister.setText(spannableString);
    binding.textRegister.setOnClickListener(v -> callback.onRegisterClicked());

    binding.btnLogin.setOnClickListener(v -> {
      String email = binding.editUsername.getText().toString();
      String password = binding.editPassword.getText().toString();
      if (validateInput(email, password)) {
        login(email, password);
      }
    });

    binding.editUsername.addTextChangedListener(new FormTextWatcher() {
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
          binding.inputEmail.setError("");
        }
      }
    });

    binding.editPassword.addTextChangedListener(new FormTextWatcher() {
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
          binding.inputPassword.setError("");
        }
      }
    });
  }

  // Method for loging user to system
  void login(String email, String password) {
    progress.show(getChildFragmentManager(), "ProgressDialog");
    viewModel.signIn(email, password).observe(getViewLifecycleOwner(), response -> {
      progress.dismiss();
      if (response.getStatus() == Status.SUCCESS) {
        callback.loginSuccess();
      } else {
        Object obj = response.getObj();
        int msgId = R.string.email_or_password_wrong;
        if (obj != null) {
          msgId = (Integer) obj;
        }
        Toast.makeText(requireContext(), msgId, Toast.LENGTH_LONG).show();
      }
    });
  }
  // Checking user input for empty and errors
  private boolean validateInput(String email, String password) {
    boolean isValid = true;
    if (email.isEmpty()) {
      binding.inputEmail.setError(getResources().getString(R.string.fill_username));
      isValid = false;
    }
    if (password.isEmpty()) {
      binding.inputPassword.setError(getResources().getString(R.string.fill_password));
      isValid = false;
    }
    return isValid;
  }
}
