package com.betalab.android.pojos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class Incidence implements Serializable {

  public String description;
  public GeoData gmap;
  public List<HistoryEvent> history;
  public String picture;
  public CitizenData reported_by;
  public List<CitizenData> support;
  public String title;

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Category category;

  public Incidence() {
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public GeoData getGmap() {
    return gmap;
  }

  public void setGmap(GeoData gmap) {
    this.gmap = gmap;
  }

  public List<HistoryEvent> getHistory() {
    return history;
  }

  public void setHistory(List<HistoryEvent> history) {
    this.history = history;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public CitizenData getReported_by() {
    return reported_by;
  }

  public void setReported_by(CitizenData reported_by) {
    this.reported_by = reported_by;
  }

  public List<CitizenData> getSupport() {
    return support;
  }

  public void setSupport(List<CitizenData> support) {
    this.support = support;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
