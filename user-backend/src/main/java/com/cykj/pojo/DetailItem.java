package com.cykj.pojo;

import lombok.Data;

@Data
public class DetailItem {

  private long detailItemId;
  private String detailItemName;
  private String unit;
  private double upperLimit;
  private double lowerLimit;
  private long isDeleted;


}
