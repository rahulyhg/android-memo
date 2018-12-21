package me.dara.memoapp.ui.memoCreate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.dara.memoapp.R;
import me.dara.memoapp.databinding.FragmentMemoCreateBinding;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.ui.MainActivityViewModel;
import me.dara.memoapp.ui.MemoCallback;
import me.dara.memoapp.ui.view.ProgressDialog;
import me.dara.memoapp.util.ImageUtil;

/**
 * @author sardor
 */
public class MemoCreateFragment extends Fragment {

  MainActivityViewModel viewModel;
  FragmentMemoCreateBinding binding;
  ProgressDialog progress;
  MemoCallback callback;

  int dummyI = 0;

  @Override public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    callback = (MemoCallback) context;
  }

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

      postMemo(create());
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
        //Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
        //if (dummyI != 10) {
        //  postMemo(create());
        //}
      } else {
        Toast.makeText(requireContext(), R.string.memo_create_error, Toast.LENGTH_LONG).show();
      }
    });
  }

  public Memo create() {
    List<Map<String, Boolean>> todoList = new ArrayList<>();
    Map<String, Boolean> map = new HashMap<>();
    map.put("Todo1 " + dummyI, false);
    map.put("Todo2 " + dummyI, false);
    map.put("Todo3 " + dummyI, false);
    File file = ((File) binding.imgMemo.getTag());
    long created = System.currentTimeMillis();
    Memo memo = new Memo(map, "This is title " + dummyI, "This is description " + dummyI,
        created, file);
    dummyI++;
    return memo;
  }
}
