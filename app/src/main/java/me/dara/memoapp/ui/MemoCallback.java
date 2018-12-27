package me.dara.memoapp.ui;

/**
 * @author sardor
 */
/**
 * Listener for handling event like memo creating and memo editing
 * */
public interface MemoCallback {
  void onMemoCreated();

  void onMemoClicked(Long id);

  void onBackPressed();

  void onCreateMemo();

  void exitToApp();
}
