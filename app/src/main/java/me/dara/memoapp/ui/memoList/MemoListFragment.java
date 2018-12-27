package me.dara.memoapp.ui.memoList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import me.dara.memoapp.R;
import me.dara.memoapp.databinding.FragmentMemoListBinding;
import me.dara.memoapp.network.model.Status;
import me.dara.memoapp.ui.MainActivityViewModel;
import me.dara.memoapp.ui.MemoCallback;
import me.dara.memoapp.ui.view.Alert;
import me.dara.memoapp.ui.view.ProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 */
/**
 * @author sardor
 */

/**
 * Screen which shows list of memos
 * */
public class MemoListFragment extends Fragment {

  FragmentMemoListBinding binding;
  MemoListAdapter adapter;
  MemoCallback callback;
  MainActivityViewModel viewModel;
  ProgressDialog progress;

  @Override public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    callback = (MemoCallback) context;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo_list, container,
        false);
    return binding.getRoot();
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    progress = new ProgressDialog();
    progress.setCancelable(false);

    viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
    adapter = new MemoListAdapter(callback, viewModel.module.fileManager);
    binding.recyclerMemo.setAdapter(adapter);
    binding.recyclerMemo.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.swipeMemo.setOnRefreshListener(this::loadMemos);
    binding.fab.setOnClickListener(v -> callback.onCreateMemo());
    binding.toolbarList.inflateMenu(R.menu.menu_main);
    binding.toolbarList.setOnMenuItemClickListener(item -> {
      alert();
      return false;
    });
    loadMemos();
  }

  // Method for showing alert windows
  public void alert() {
    Alert alert = Alert.newInstance(getResources().getString(R.string.warning),
        getResources().getString(R.string.exit_msg));
    alert.listener = () -> {
      progress.show(getChildFragmentManager(), "ProgressDialog");
      viewModel.exit().observe(getViewLifecycleOwner(), o -> {
        progress.dismiss();
        callback.exitToApp();
      });
    };
    alert.count = 2;
    alert.show(getChildFragmentManager(), "Alert");
  }

  public void loadMemos() {
    binding.swipeMemo.setRefreshing(true);
    viewModel.loadMemos().observe(getViewLifecycleOwner(), response -> {
      binding.swipeMemo.setRefreshing(false);
      if (response.getStatus() == Status.SUCCESS) {
        adapter.update((List<MemoProvider>) response.getObj());
      } else {
        Toast.makeText(requireContext(), R.string.memo_create_error, Toast.LENGTH_SHORT).show();
      }
    });
  }
}
