package me.dara.memoapp.ui.memoCreate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.util.Calendar;
import me.dara.memoapp.R;
import me.dara.memoapp.databinding.FragmentMemoCreateBinding;
import me.dara.memoapp.file.FilePath;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.ui.MainActivityViewModel;
import me.dara.memoapp.ui.MemoCallback;
import me.dara.memoapp.ui.view.Alert;
import me.dara.memoapp.ui.view.DatePickerAppCompat;
import me.dara.memoapp.ui.view.ProgressDialog;
import me.dara.memoapp.util.ImageUtil;

import static android.app.Activity.RESULT_OK;

/**
 * @author sardor
 */

/**
 * Screen for editing and creating memos
 * */
public class MemoCreateFragment extends Fragment {

  MainActivityViewModel viewModel;
  FragmentMemoCreateBinding binding;
  ProgressDialog progress;
  MemoCallback callback;
  public Long memoId = -1L;
  public Memo memo;


  // Attaching listener for MainActivity
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


  // Getting result from image pick
  @Override public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1001 && resultCode == RESULT_OK) {
      Bitmap bitmap = ImageUtil.getImageFromResult(requireContext(), resultCode, data);
      File fileName = viewModel.saveFile(bitmap);
      memo.setDownloadUrl(fileName.getAbsolutePath());
      binding.imgMemo.setTag(fileName.getAbsolutePath());
      binding.imgMemo.setImageBitmap(bitmap);
    }
  }


  // Alert window with warning about discard
  public void showWarning() {
    String title = binding.editMemoTitle.getText().toString();
    String descrption = binding.editMemo.getText().toString();
    memo.title = title;
    memo.description = descrption;
    if (viewModel.isChanged(memo)) {
      Alert alert = Alert.newInstance(getResources().getString(R.string.warning),
          getResources().getString(R.string.memo_create_exit_msg));
      alert.listener = () -> {
        memoId = -1L;
        memo = null;
        callback.onBackPressed();
      };
      alert.count = 2;
      alert.show(getChildFragmentManager(), "Alert");
    } else {
      memo = null;
      callback.onBackPressed();
    }
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
    memo = new Memo();
    Memo localMemo = viewModel.loadMemo(memoId);
    setMemo(localMemo);
    binding.toolbarMemoCreate.setNavigationOnClickListener(v -> {
      callback.onBackPressed();
    });
    progress = new ProgressDialog();
    progress.setCancelable(false);
    binding.btnCreateMem.setOnClickListener(v -> {
      if (validateMemo()) {
        postMemo(memo);
      }
    });
    binding.imgMemo.setOnClickListener(v -> {
      startActivityForResult(ImageUtil.getPickImageIntent(requireContext()), 1001);
    });
    binding.dateMemo.setOnClickListener(v -> {
      DatePickerAppCompat dialog = new DatePickerAppCompat();
      dialog.listener = (view1, year, month, dayOfMonth) -> {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        memo.createdTime = cal.getTimeInMillis();
        binding.dateMemo.setText(DateUtils.formatDateTime(requireContext(), memo.createdTime,
            DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME));
      };
      dialog.show(getChildFragmentManager(), "DatePickerAppCompat");
    });
  }


  // Setting memo from list to memo create screen
  public void setMemo(Memo localMemo) {
    if (localMemo != null) {
      String title = localMemo.title;
      String description = localMemo.description;
      binding.editMemoTitle.setText(title);
      binding.editMemo.setText(description);
      if (localMemo.imgBitmap == null) {
        Picasso.get().load(Uri.parse(localMemo.downloadUrl)).into(new Target() {
          @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            String filename =
                new File(Uri.parse(localMemo.downloadUrl).getLastPathSegment()).getName();
            binding.imgMemo.setImageBitmap(bitmap);
            viewModel.saveFile(bitmap, filename);
            binding.imgMemo.setTag(
                viewModel.module.fileManager.getFileByName(FilePath.UPLOAD, filename)
                    .getAbsoluteFile());
          }

          @Override public void onBitmapFailed(Exception e, Drawable errorDrawable) {

          }

          @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
            binding.imgMemo.setImageDrawable(placeHolderDrawable);
          }
        });
      } else {
        binding.imgMemo.setImageBitmap(localMemo.imgBitmap);
      }
      binding.dateMemo.setText(DateUtils.formatDateTime(requireContext(), localMemo.createdTime,
          DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME));
      memo.setDownloadUrl(localMemo.downloadUrl);
      memo.title = title;
      memo.description = description;
      memo.createdTime = localMemo.createdTime;
      memo.id = localMemo.id;
      try {
        binding.imgMemo.setTag(viewModel.getFilePath(localMemo.getFile()));
      } catch (NullPointerException e) {
        binding.imgMemo.setTag("");
      }
    }
  }

  // Posting or updating memos to FireBase databse and FirebaseStorage
  public void postMemo(Memo memo) {
    if (memo.id == null) {
      memo.id = memo.createdTime;
    }
    memo.downloadUrl = (String) binding.imgMemo.getTag();
    progress.show(getChildFragmentManager(), "Progress");
    viewModel.postMemo(memo).observe(getViewLifecycleOwner(), response -> {
      progress.dismiss();
      if (response.getStatus() == Status.SUCCESS) {
        callback.onMemoCreated();
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(requireContext(), R.string.memo_create_error, Toast.LENGTH_LONG).show();
      }
    });
  }

  public Boolean validateMemo() {
    String title = binding.editMemoTitle.getText().toString();
    String descrption = binding.editMemo.getText().toString();
    memo.title = title;
    memo.description = descrption;
    boolean isValid = true;
    int errorMsgId = R.string.memo_create_error;
    if (title == null || title.isEmpty()) {
      errorMsgId = R.string.memo_title_error;
      isValid = false;
    } else if (descrption == null || descrption.isEmpty()) {
      errorMsgId = R.string.memo_description_error;
      isValid = false;
    } else if (memo.createdTime == -1L) {
      errorMsgId = R.string.memo_date_error;
      isValid = false;
    } else if (memo.downloadUrl == null || memo.downloadUrl.isEmpty()) {
      errorMsgId = R.string.memo_img_error;
      isValid = false;
    }
    if (!isValid) {
      Toast.makeText(requireContext(), errorMsgId, Toast.LENGTH_LONG).show();
    }
    return isValid;
  }

}
