package com.dropit.eyaldagiavdor.dropit.model;

import io.realm.RealmObject;

public class Contact extends RealmObject {

  private String firstName;
  private String lastName;

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;

  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


}
