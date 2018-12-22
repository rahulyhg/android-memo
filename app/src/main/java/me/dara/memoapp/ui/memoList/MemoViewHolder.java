package me.dara.memoapp.ui.memoList;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import me.dara.memoapp.R;
import me.dara.memoapp.file.FileManager;
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
  CardView root;
  LinearLayout todoRoot;
  AppCompatTextView textDate;
  long id = -1;
  Task<Uri> uriTask = null;
  FileManager fileManager;

  public MemoViewHolder(@NonNull View itemView, MemoCallback callback, FileManager fileManager) {
    super(itemView);
    this.callback = callback;
    this.fileManager = fileManager;
    textTitle = itemView.findViewById(R.id.text_memo_title);
    textDescription = itemView.findViewById(R.id.text_memo_description);
    img = itemView.findViewById(R.id.img_memo);
    root = itemView.findViewById(R.id.linear_memo_item);
    todoRoot = itemView.findViewById(R.id.linear_todo);
    textDate = itemView.findViewById(R.id.text_memo_date);
    root.setOnClickListener(v -> {

    });
  }

  OnSuccessListener<Uri> successListener = uri -> Picasso.get().load(uri).into(new Target() {
    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      img.setImageBitmap(bitmap);
      fileManager.saveFile(bitmap,uri.getLastPathSegment());
    }

    @Override public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
      img.setImageDrawable(placeHolderDrawable);
    }
  });

  public void bind(MemoProvider memo) {
    if (id != memo.id) {
      textTitle.setText(memo.title);
      textDescription.setText(memo.description);

      todoRoot.removeAllViews();
      todoRoot.addView(memo.todoView);
      textDate.setText(memo.createdAt);
      if (memo.isDownloadedFile) {
        Picasso.get().load(memo.downloadUrl).into(img);
      } else {
        if (uriTask == null) {
          uriTask = memo.fileReference.getDownloadUrl();
          uriTask.addOnSuccessListener(successListener);
        }
      }
    }
  }

}
