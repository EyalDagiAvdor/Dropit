package com.dropit.eyaldagiavdor.dropit;

import android.app.Application;
import android.content.Intent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

  private static MyApplication context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
    RealmConfiguration config = new RealmConfiguration.Builder(this).build();
    Realm.deleteRealm(config);
    Realm.setDefaultConfiguration(config);

    Intent i = new Intent(context, SyncService.class);
    context.startService(i);
  }


  public static MyApplication getContext() {
    return context;
  }
}
