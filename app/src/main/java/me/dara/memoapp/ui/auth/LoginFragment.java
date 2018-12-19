package me.dara.memoapp.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import me.dara.memoapp.R;
import me.dara.memoapp.databinding.FragmentLoginBinding;

/**
 * @author sardor
 */
public class LoginFragment extends Fragment {

  OnAuthCallback callback;
  AuthViewModel viewModel;
  FragmentLoginBinding binding;

  @Override public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    callback = (OnAuthCallback) context;
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
    return binding.getRoot();
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = ViewModelProviders.of(requireActivity()).get(AuthViewModel.class);
  }
}
