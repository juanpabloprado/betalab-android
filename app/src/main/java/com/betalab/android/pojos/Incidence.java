package com.betalab.android.pojos;

import com.google.firebase.database.Exclude;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class Incidence implements Serializable {

  String description;
  Map gmap;
  List<HistoryEvent> history;
  String picture;
  CitizenData reported_by;
  List<CitizenData> support;
  String title;

  @Exclude public Map<String, Object> toMap() {
    return null;
  }
}
