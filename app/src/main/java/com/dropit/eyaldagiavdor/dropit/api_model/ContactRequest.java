package com.dropit.eyaldagiavdor.dropit.api_model;

import com.dropit.eyaldagiavdor.dropit.model.Contact;

import io.realm.RealmObject;

public class ContactRequest extends RealmObject {

  private Contact contact;

  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }
}
