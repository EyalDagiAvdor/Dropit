package com.dropit.eyaldagiavdor.dropit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dropit.eyaldagiavdor.dropit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleAdapter extends BaseAdapter {
  private static String[] ITEM_NAMES = ("item1,item2,item3,item4".split(",")) ;
  private static String[] ITEM_IDS = "id1,id2,id3,id4".split(",");

  private final LayoutInflater inflater;

  public SimpleAdapter(Context context) {
    inflater = LayoutInflater.from(context);
  }

  @Override public int getCount() {
    return ITEM_NAMES.length;
  }

  @Override public String getItem(int position) {
    return ITEM_NAMES[position];
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View view, ViewGroup parent) {
    ViewHolder holder;
    if (view != null) {
      holder = (ViewHolder) view.getTag();
    } else {
      view = inflater.inflate(R.layout.simple_list_item, parent, false);
      holder = new ViewHolder(view);
      view.setTag(holder);
    }

    String itemName = getItem(position);
    holder.item.setText(itemName);
    // Note: don't actually do string concatenation like this in an adapter's getView.

    return view;
  }

  static final class ViewHolder {
    @BindView(R.id.shopping_item) TextView item;

    ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }
}
