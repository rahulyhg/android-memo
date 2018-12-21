package me.dara.memoapp.ui.memoList;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import me.dara.memoapp.R;
import me.dara.memoapp.network.model.Memo;
import me.dara.memoapp.ui.MemoCallback;

/**
 * @author sardor
 */
public class MemoViewHolder extends RecyclerView.ViewHolder {
  MemoCallback callback;
  AppCompatTextView textTitle;
  AppCompatTextView textDescription;
  AppCompatImageView img;
  LinearLayout root;

  public MemoViewHolder(@NonNull View itemView, MemoCallback callback) {
    super(itemView);
    this.callback = callback;
    textTitle = itemView.findViewById(R.id.text_memo_title);
    textDescription = itemView.findViewById(R.id.text_memo_description);
    img = itemView.findViewById(R.id.img_memo);
    root = itemView.findViewById(R.id.linear_memo_item);
    root.setOnClickListener(v -> {

    });
  }

  public void bind(Memo memo) {
    textTitle.setText(memo.title);
    textDescription.setText(memo.description);
    root.setTag(memo.id);
  }
}
