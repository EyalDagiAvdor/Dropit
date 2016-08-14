package com.dropit.eyaldagiavdor.dropit.api_model;


import com.dropit.eyaldagiavdor.dropit.model.Drop;

import io.realm.RealmObject;

public class DropRequest extends RealmObject {

  private Drop drop;

  public Drop getDrop() {
    return drop;
  }

  public void setDrop(Drop drop) {
    this.drop = drop;
  }
}
