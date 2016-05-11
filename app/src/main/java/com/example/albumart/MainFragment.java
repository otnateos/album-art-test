package com.example.albumart;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * @author Johanes Soetanto
 * @since 10/05/16
 */
public class MainFragment extends Fragment {

  public MainFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    final GridView gridview = (GridView) rootView.findViewById(R.id.grid);
    gridview.setNumColumns(gridColumnCount());
    gridview.setAdapter(new AlbumAdapter(getContext()));
    return rootView;
  }

  private int gridColumnCount() {
    return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
        4 : 2;
  }
}
