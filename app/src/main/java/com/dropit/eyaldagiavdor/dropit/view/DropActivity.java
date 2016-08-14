package com.dropit.eyaldagiavdor.dropit.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dropit.eyaldagiavdor.dropit.MySingleton;
import com.dropit.eyaldagiavdor.dropit.QueueRequest;
import com.dropit.eyaldagiavdor.dropit.R;
import com.dropit.eyaldagiavdor.dropit.model.Drop;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;


public class DropActivity extends Activity implements QueueRequest{

  @BindView(R.id.drop_contact) TextView contact;
  @BindView(R.id.drop_address) TextView address;
  @BindView(R.id.bags) TextView bags;

  int numOfBags;
  private Realm realm;
  Drop currentDrop;

  @OnClick(R.id.drop_button) void doTheDrop() {

    String url = "http://dropurl.com";
    StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
      new Response.Listener<String>()
      {
        @Override
        public void onResponse(String response) {
          // response

          Toast.makeText(getApplicationContext(), getResources().getString(R.string.successful_drop),
            Toast.LENGTH_SHORT)
            .show();
        }
      },
      new Response.ErrorListener()
      {
        @Override
        public void onErrorResponse(VolleyError error) {
          //cache the request
        }
      }
    ) {

      @Override
      protected Map<String, String> getParams()
      {
        Map<String, String> params = new HashMap<>();

        //add drop params to request

        return params;
      }

    };

    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);


  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drop);
    ButterKnife.bind(this);

    Intent i = getIntent();
    numOfBags = i.getIntExtra("bags", 0);
    String bagsToDisplay = "" + numOfBags;
    bags.setText(bagsToDisplay);

    SharedPreferences sharedPref = getSharedPreferences(ContactActivity.SHARED_PREFS_TAG,
      Context.MODE_PRIVATE);
    String streetNameStr = sharedPref.getString("street",null);
    int streetNumber = sharedPref.getInt("number",-1);
    String cityStr = sharedPref.getString("city",null);
    String countryStr = sharedPref.getString("country", null);

    String fname = sharedPref.getString("fname",null);
    String lname = sharedPref.getString("lname",null);

    String addressToDisplay = streetNameStr + " " + streetNumber + " ," + cityStr + " ,"
      + countryStr;
    address.setText(addressToDisplay);

    String contactToDisplay = fname + " " + lname;

    contact.setText(contactToDisplay);






  }

  @Override
  public void addRequestToDB() {

  }

  @Override
  public void removeRequestFromDB() {

  }
}
