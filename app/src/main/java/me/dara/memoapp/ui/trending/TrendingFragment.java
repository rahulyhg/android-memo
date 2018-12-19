package me.dara.memoapp.ui.trending;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import me.dara.memoapp.R;

/**
 * @author sardor
 */
public class TrendingFragment extends Fragment {

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_trending, container, false);
  }
}
