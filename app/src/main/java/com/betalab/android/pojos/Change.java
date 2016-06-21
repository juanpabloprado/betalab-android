package com.betalab.android.pojos;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class Change {
  String after;
  String before;
  String field;
  public Change() {

  }

  public String getAfter() {
    return after;
  }

  public void setAfter(String after) {
    this.after = after;
  }

  public String getBefore() {
    return before;
  }

  public void setBefore(String before) {
    this.before = before;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
