package me.dara.memoapp.ui;

/**
 * @author sardor
 */
public interface MemoCallback {
  void onMemoCreated();

  void onMemoClicked(long id);
}
