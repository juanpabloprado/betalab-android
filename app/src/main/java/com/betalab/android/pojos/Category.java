package com.betalab.android.pojos;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class Category {
  String description;
  String icon;
  String type;

  public Category() {
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
