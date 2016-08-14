package com.dropit.eyaldagiavdor.dropit.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.dropit.eyaldagiavdor.dropit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BagsActivity extends Activity {

  @BindView(R.id.list_of_items)
  ListView listOfItems;

  @OnClick(R.id.address_next_button)
  void goToDropPage() {
    Intent i = new Intent(getApplicationContext(), DropActivity.class);
    i.putExtra("bags", adapter.getCount());
    startActivity(i);
  }

  private SimpleAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bags);
    ButterKnife.bind(this);

    adapter = new SimpleAdapter(this);
    listOfItems.setAdapter(adapter);

   }

}
