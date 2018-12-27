package me.dara.memoapp.ui.memoList;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import me.dara.memoapp.R;
import me.dara.memoapp.file.FileManager;
import me.dara.memoapp.ui.MemoCallback;

/**
 * @author sardor
 */
/**
 * Main class which gets data from MemoProvider  and shows it in UI
 * */
public class MemoViewHolder extends RecyclerView.ViewHolder {

  MemoCallback callback;
  AppCompatTextView textTitle;
  AppCompatTextView textDescription;
  AppCompatImageView img;
  CardView root;
  AppCompatTextView textDate;
  FileManager fileManager;

  public MemoViewHolder(@NonNull View itemView, MemoCallback callback, FileManager fileManager) {
    super(itemView);
    this.callback = callback;
    this.fileManager = fileManager;
    textTitle = itemView.findViewById(R.id.text_memo_title);
    textDescription = itemView.findViewById(R.id.text_memo_description);
    img = itemView.findViewById(R.id.img_memo);
    root = itemView.findViewById(R.id.linear_memo_item);
    textDate = itemView.findViewById(R.id.text_memo_date);
  }


  // Listener which listens image loading, after loading image it saves that image into FileSystem
  Target memoImgTarget = new Target() {
    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      img.setImageBitmap(bitmap);
      String filename = new File(Uri.parse(img.getTag().toString()).getLastPathSegment()).getName();
      fileManager.saveFile(bitmap, filename);
    }

    @Override public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
  };

  // Logic which converts model to UI cells

  public void bind(MemoProvider memo) {
    textTitle.setText(memo.title);
    textDescription.setText(memo.description);
    textDate.setText(memo.createdAt);
    root.setOnClickListener(v -> callback.onMemoClicked(memo.id));
    if (memo.isDownloadedFile) {
      Picasso.get().load(new File(memo.downloadUrl)).into(img);
    } else {
      img.setTag(memo.downloadUrl);
      Picasso.get().load(memo.downloadUrl).into(memoImgTarget);
    }
  }
}