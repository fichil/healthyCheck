package com.cykj.pojo;

import lombok.Data;

@Data
public class DetailResult {

  private long resultId;
  private long detailItemId;
  private String result;
  private long orderId;
  private String doctor;
  private java.sql.Timestamp date;

}
