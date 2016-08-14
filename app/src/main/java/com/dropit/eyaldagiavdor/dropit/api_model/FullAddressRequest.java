package com.dropit.eyaldagiavdor.dropit.api_model;

import com.dropit.eyaldagiavdor.dropit.model.FullAddress;

import io.realm.RealmObject;

public class FullAddressRequest extends RealmObject{

  private FullAddress address;

  public FullAddress getAddress() {
    return address;
  }

  public void setAddress(FullAddress address) {
    this.address = address;
  }
}
