package com.betalab.android.pojos;

import java.util.List;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class HistoryEvent {

  String date_time;
  String status;
  String description;
  String email;

  List<Change> changes;

  public HistoryEvent() {

  }

  public List<Change> getChanges() {
    return changes;
  }

  public void setChanges(List<Change> changes) {
    this.changes = changes;
  }

  public String getDate_time() {
    return date_time;
  }

  public void setDate_time(String date_time) {
    this.date_time = date_time;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
