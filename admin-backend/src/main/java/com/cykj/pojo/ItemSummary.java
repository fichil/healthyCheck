package com.cykj.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ItemSummary {

  private long summaryId;
  private long orderId;
  private long itemId;
  private String summary;
  private String doctor;
  private java.sql.Timestamp date;

  private List<DetailResult> details;

}
