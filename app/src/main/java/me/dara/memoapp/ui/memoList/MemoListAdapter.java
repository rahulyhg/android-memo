package me.dara.memoapp.ui.memoList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import me.dara.memoapp.R;
import me.dara.memoapp.file.FileManager;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.ui.MemoCallback;

/**
 * @author sardor
 */
public class MemoListAdapter extends RecyclerView.Adapter<MemoViewHolder> {
  List<MemoProvider> list = new ArrayList<>();
  MemoCallback callback;
  FileManager fileManager;

  public MemoListAdapter(MemoCallback callback, FileManager fileManager) {
    this.callback = callback;
    this.fileManager = fileManager;
  }

  public void add(MemoProvider memo) {
    list.add(memo);
    notifyItemChanged(getItemCount());
  }

  public void add(List<MemoProvider> subList) {
    int changedPosition = list.size();
    list.addAll(subList);
    notifyItemRangeChanged(changedPosition, getItemCount());
  }

  @NonNull @Override
  public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).
            inflate(R.layout.list_item_memo, parent, false);
    return new MemoViewHolder(view, callback,fileManager);
  }

  @Override public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
    holder.bind(list.get(position));
  }

  @Override public int getItemCount() {
    return list.size();
  }
}


