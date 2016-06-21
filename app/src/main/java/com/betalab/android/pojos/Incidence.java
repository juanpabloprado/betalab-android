package com.betalab.android.pojos;

import com.google.firebase.database.Exclude;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

  @Exclude public Map<String, Object> toMap() {
    return null;
  }
}
