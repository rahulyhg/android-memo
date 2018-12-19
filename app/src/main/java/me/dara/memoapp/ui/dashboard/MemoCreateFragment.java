package me.dara.memoapp.ui.dashboard;

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
import me.dara.memoapp.databinding.FragmentMemoCreateBinding;

/**
 * @author sardor
 */
public class MemoCreateFragment extends Fragment {

  DashBoardViewModel viewModel;
  FragmentMemoCreateBinding binding;

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo_create,
        container, true);
    return binding.getRoot();
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);

  }

  /*
  *
  *
  *
  *
  *
  *

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(DashBoardViewModel::class.java)
    imgMemo.setOnClickListener {
      startActivityForResult(ImageUtil.getPickImageIntent(requireContext()), 1000)
    }
    dateMemo.setOnClickListener {
      val picker = DatePickerAppCompat()
      picker.listener = { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
        Toast.makeText(requireContext(), "year:$year,month:$month,dayofMonth:$dayOfMonth", Toast.LENGTH_LONG).show()
      }
      picker.show(childFragmentManager, "DatePickerAppCompat")
    }
    btnCreateMem.setOnClickListener {
      viewModel.postUser(User("sardorislomov96gmailcom","5341","phot_url2",false,29))
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 1000) {
      val bmp = ImageUtil.getImageFromResult(requireContext(), resultCode, data)
      imgMemo.setImageBitmap(bmp)
    }
  }
  *
  *
  *
  *
  *
  *
  *
  * */
}
