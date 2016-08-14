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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dropit.eyaldagiavdor.dropit.MySingleton;
import com.dropit.eyaldagiavdor.dropit.QueueRequest;
import com.dropit.eyaldagiavdor.dropit.R;
import com.dropit.eyaldagiavdor.dropit.api_model.ContactRequest;
import com.dropit.eyaldagiavdor.dropit.model.Contact;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class ContactActivity extends Activity implements QueueRequest{

  @BindView(R.id.first_name)
  EditText firstName;
  @BindView(R.id.last_name)
  EditText lastName;

  Realm realm = Realm.getDefaultInstance();
  Contact currentContact;

  public final static String SHARED_PREFS_TAG = "SHARED_PREFS";

  @OnClick(R.id.name_next_button)
  void goToAddressPage() {

    SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS_TAG, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();

    editor.putString("fname", firstName.getText().toString());
    editor.putString("lname", lastName.getText().toString());
    editor.apply();

    addRequestToDB();

    String url = "http://contacturl.com";
    StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
      new Response.Listener<String>()
      {
        @Override
        public void onResponse(String response) {

          // check validity of reponse, if valid reomve from DB

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
        // add contact params to call

        return params;
      }

    };

    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);

    Intent i = new Intent(getApplicationContext(), AddressActivity.class);
    startActivity(i);
  }

  public final static String URL = "http://www.someurl.com";


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact);
    ButterKnife.bind(this);

    SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS_TAG, Context.MODE_PRIVATE);
    String fname = sharedPref.getString("fname",null);
    String lname = sharedPref.getString("lname",null);

    if (fname != null) {
      firstName.setText(fname);
      lastName.setText(lname);
      return;
    }


    JsonObjectRequest jsObjRequest = new JsonObjectRequest
      (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
          //read names from json to name and set editTexts

        }
      }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
          // TODO Auto-generated method stub

        }
      });
    // Add the request to the RequestQueue.
    MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

  }

  @Override
  public void addRequestToDB() {

    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        currentContact = realm.createObject(Contact.class);
        currentContact.setFirstName(firstName.getText().toString());
        currentContact.setFirstName(lastName.getText().toString());

        ContactRequest cr = realm.createObject(ContactRequest.class);
        cr.setContact(currentContact);
      }
    });

  }

  @Override
  public void removeRequestFromDB() {


    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        currentContact = realm.createObject(Contact.class);
        currentContact.setFirstName(firstName.getText().toString());
        currentContact.setFirstName(lastName.getText().toString());

        final RealmResults<ContactRequest> requests
          = realm.where(ContactRequest.class).equalTo("firstName", currentContact.getFirstName())
          .equalTo("lastName", currentContact.getLastName()).findAll();

       requests.deleteAllFromRealm();
      }
    });

  }
}

