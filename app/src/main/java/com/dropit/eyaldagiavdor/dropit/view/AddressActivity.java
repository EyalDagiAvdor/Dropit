package com.dropit.eyaldagiavdor.dropit.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dropit.eyaldagiavdor.dropit.MySingleton;
import com.dropit.eyaldagiavdor.dropit.R;
import com.dropit.eyaldagiavdor.dropit.QueueRequest;
import com.dropit.eyaldagiavdor.dropit.api_model.ContactRequest;
import com.dropit.eyaldagiavdor.dropit.api_model.FullAddressRequest;
import com.dropit.eyaldagiavdor.dropit.model.FullAddress;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddressActivity extends Activity implements QueueRequest {

  @BindView(R.id.street)
  EditText street;
  @BindView(R.id.number)
  EditText number;
  @BindView(R.id.city)
  EditText city;
  @BindView(R.id.country)
  EditText country;

  Realm realm = Realm.getDefaultInstance();
  private FullAddress address;

  @OnClick(R.id.address_next_button)
  void goToBuyPage() {
    Intent i = new Intent(getApplicationContext(), BagsActivity.class);

    SharedPreferences sharedPref = getSharedPreferences(ContactActivity.SHARED_PREFS_TAG, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();

    editor.putString("street", street.getText().toString());
    editor.putInt("number", Integer.parseInt(number.getText().toString()));
    editor.putString("city", city.getText().toString());
    editor.putString("country", country.getText().toString());
    editor.apply();

    addRequestToDB();

    String url = "http://addressurl.com";
    StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
      new Response.Listener<String>()
      {
        @Override
        public void onResponse(String response) {

          // check validity of response, if valid remove from DB

          if (true){
            removeRequestFromDB();
          }
        }
      },
      new Response.ErrorListener()
      {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
      }
    ) {

      @Override
      protected Map<String, String> getParams()
      {
        Map<String, String> params = new HashMap<>();
        // add address params to call
        return params;
      }

    };

    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);


    startActivity(i);
  }




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_address);
    ButterKnife.bind(this);

    SharedPreferences sharedPref = getSharedPreferences(ContactActivity.SHARED_PREFS_TAG, Context.MODE_PRIVATE);
    String streetNameStr = sharedPref.getString("street",null);
    int streetNumber = sharedPref.getInt("number",-1);
    String cityStr = sharedPref.getString("city",null);
    String countryStr = sharedPref.getString("country", null);

    if (streetNameStr != null) {
      street.setText(streetNameStr);
    }

    if (streetNumber != -1) {
      number.setText(Integer.toString(streetNumber));
    }

    if (cityStr != null ) {
      city.setText(cityStr);
    }
    if (countryStr != null) {
      country.setText(countryStr);
    }
  }

  @Override
  public void addRequestToDB() {

    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        address = realm.createObject(FullAddress.class);
        address.setStreetName(street.getText().toString());
        address.setCity(city.getText().toString());
        address.setStreetNumber(Integer.parseInt(number.getText().toString()));
        address.setCountry(country.getText().toString());

        FullAddressRequest request = realm.createObject(FullAddressRequest.class);
        FullAddress fa = realm.createObject(FullAddress.class);

        fa.setCity(address.getCity());
        fa.setCountry(address.getCountry());
        fa.setStreetName(address.getStreetName());
        fa.setStreetNumber(address.getStreetNumber());

        request.setAddress(fa);
      }
    });

  }

  @Override
  public void removeRequestFromDB() {

    // Delete all persons
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        address = realm.createObject(FullAddress.class);
        address.setStreetName(street.getText().toString());
        address.setCity(city.getText().toString());
        address.setStreetNumber(Integer.parseInt(number.getText().toString()));
        address.setCountry(country.getText().toString());

        final RealmResults<FullAddressRequest> requests = realm.where(FullAddressRequest.class)
          .equalTo("streetName", address.getStreetName())
          .equalTo("city", address.getCity())
          .equalTo("streetNumber", address.getStreetNumber())
          .equalTo("country", address.getCountry())
          .findAll();

        requests.deleteAllFromRealm();
      }
    });

  }
}

