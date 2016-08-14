package com.dropit.eyaldagiavdor.dropit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dropit.eyaldagiavdor.dropit.api_model.ContactRequest;
import com.dropit.eyaldagiavdor.dropit.api_model.DropRequest;
import com.dropit.eyaldagiavdor.dropit.api_model.FullAddressRequest;
import com.dropit.eyaldagiavdor.dropit.model.Contact;
import com.dropit.eyaldagiavdor.dropit.model.FullAddress;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class SyncService extends Service {
  public SyncService() {
  }

  Realm realm;

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {

    realm = Realm.getDefaultInstance();

    if (!CheckForPendingRequests())
    {
      stopSelf();
    }

    sendAddressRequests();
    sendContactRequests();
    sendDropRequests();

    return Service.START_NOT_STICKY;
  }

  public boolean CheckForPendingRequests() {

    int addressRequests = realm.where(FullAddressRequest.class)
      .findAll().size();
    int contactRequests = realm.where(ContactRequest.class)
      .findAll().size();
    int dropRequests = realm.where(DropRequest.class)
      .findAll().size();

    if (addressRequests == 0 && contactRequests == 0 && dropRequests == 0) {
      return false;
    }

    return true;

  }

  private void sendAddressRequests() {
    final RealmResults<FullAddressRequest> addressRequests = realm.where(FullAddressRequest.class)
      .findAll();
    String addressUrl = "http://addressurl.com";

    for (final FullAddressRequest request : addressRequests) {

      StringRequest putRequest = new StringRequest(Request.Method.PUT, addressUrl,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {

            // check validity of reponse, if valid reomve from DB

            if (true) {

              // Delete all persons
              realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                  request.deleteFromRealm();
                }
              });
            }


          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
        }
      ) {

        @Override
        protected Map<String, String> getParams() {
          Map<String, String> params = new HashMap<>();
          // add contact params to call

          return params;
        }

      };

      MySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);
    }
  }

  private void sendContactRequests() {
    final RealmResults<ContactRequest> contactRequests = realm.where(ContactRequest.class)
      .findAll();
    String contactUrl = "http://contacturl.com";

    for (final ContactRequest request : contactRequests) {

      StringRequest putRequest = new StringRequest(Request.Method.PUT, contactUrl,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {

            // check validity of response, if valid reomve from DB

            if (true) {

              // Delete all persons
              realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                  request.deleteFromRealm();
                }
              });
            }


          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
        }
      ) {

        @Override
        protected Map<String, String> getParams() {
          Map<String, String> params = new HashMap<>();
          // add contact params to call

          return params;
        }

      };

      MySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);


    }
  }

  private void sendDropRequests() {
    final RealmResults<DropRequest> dropRequest = realm.where(DropRequest.class)
      .findAll();
    String dropUrl = "http://dropurl.com";

    for (final DropRequest request : dropRequest) {

      StringRequest putRequest = new StringRequest(Request.Method.PUT, dropUrl,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {

            // check validity of response, if valid reomve from DB

            if (true) {

              // Delete all persons
              realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                  request.deleteFromRealm();
                }
              });
            }


          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
        }
      ) {

        @Override
        protected Map<String, String> getParams() {
          Map<String, String> params = new HashMap<>();
          // add contact params to call

          return params;
        }

      };

      MySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);


    }
  }


}
