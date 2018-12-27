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

/**
 * Adapter class which converts Memo models to UI element
 * */
public class MemoListAdapter extends RecyclerView.Adapter<MemoViewHolder> {
  List<MemoProvider> list = new ArrayList<>();
  MemoCallback callback;
  FileManager fileManager;

  public MemoListAdapter(MemoCallback callback, FileManager fileManager) {
    this.callback = callback;
    this.fileManager = fileManager;
  }

  public void update(List<MemoProvider> subList) {
    list.clear();
    list.addAll(subList);
    notifyDataSetChanged();
  }

  @NonNull @Override
  public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).
            inflate(R.layout.list_item_memo, parent, false);
    return new MemoViewHolder(view, callback, fileManager);
  }

  @Override public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
    holder.bind(list.get(position));
  }

  @Override public int getItemCount() {
    return list.size();
  }
}


