package com.dropit.eyaldagiavdor.dropit.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Drop extends RealmObject{

  private Contact contact;
  private FullAddress address;
  private RealmList<Item> items;

  public RealmList<Item> getItems() {
    return items;
  }

  public void setItems(RealmList<Item> items) {
    this.items = items;
  }

  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }


  public FullAddress getAddress() {
    return address;
  }

  public void setAddress(FullAddress address) {
    this.address = address;
  }



}
