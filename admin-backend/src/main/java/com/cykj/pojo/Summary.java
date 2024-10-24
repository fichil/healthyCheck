package com.cykj.pojo;

import lombok.Data;

@Data
public class Summary {

  private long summaryId;
  private long orderId;
  private String resultDescription;
  private String doctor;
  private java.sql.Timestamp date;

}
