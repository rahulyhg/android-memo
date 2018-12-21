package me.dara.memoapp.ui.memoCreate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import me.dara.memoapp.R;
import me.dara.memoapp.databinding.FragmentMemoCreateBinding;
import me.dara.memoapp.network.model.ApiResponse;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.ui.MainActivityViewModel;
import me.dara.memoapp.ui.view.ProgressDialog;
import me.dara.memoapp.util.ImageUtil;

/**
 * @author sardor
 */
public class MemoCreateFragment extends Fragment {

  MainActivityViewModel viewModel;
  FragmentMemoCreateBinding binding;
  ProgressDialog progress;

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo_create,
        container, false);
    return binding.getRoot();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1001) {
      Bitmap bitmap = ImageUtil.getImageFromResult(requireContext(), resultCode, data);
      File fileName = viewModel.saveFile(bitmap);
      binding.imgMemo.setImageBitmap(bitmap);
      binding.imgMemo.setTag(fileName);
    }
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
    progress = new ProgressDialog();
    progress.setCancelable(false);
    binding.btnCreateMem.setOnClickListener(v -> {
      List<String> todoList = new ArrayList<>();
      todoList.add("Todo 1");
      todoList.add("Todo 2");
      todoList.add("Todo 3");
      File file = ((File) binding.imgMemo.getTag());
      Memo memo = new Memo(todoList, "This is title", "This is description",
          Calendar.getInstance().getTime().getTime(), file);
      postMemo(memo);
    });
    binding.imgMemo.setOnClickListener(v -> {
      startActivityForResult(ImageUtil.getPickImageIntent(requireContext()), 1001);
    });
  }

  public void postMemo(Memo memo) {
    progress.show(getChildFragmentManager(), "Progress");
    viewModel.postMemo(memo).observe(getViewLifecycleOwner(), response -> {
      progress.dismiss();
      if (response.getStatus() == Status.SUCCESS) {
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(requireContext(), R.string.memo_create_error, Toast.LENGTH_LONG).show();
      }
    });
  }
}
