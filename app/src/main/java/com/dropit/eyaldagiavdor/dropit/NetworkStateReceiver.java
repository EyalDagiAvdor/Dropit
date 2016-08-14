package com.dropit.eyaldagiavdor.dropit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStateReceiver extends BroadcastReceiver
{
  private final static String TAG = "NetworkStateReceiver";

  public void onReceive(Context context, Intent intent)
  {

    if (intent.getExtras() != null)
    {
      ConnectivityManager connectivityManager = ((ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE));

      NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
      if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED)
      {
        Intent i = new Intent(context, SyncService.class);
        context.startService(i);
      }
      else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,
        Boolean.FALSE))
      {
        Log.d(TAG, "There's no network connectivity");
      }
    }
  }

}
