package com.cykj.pojo;

import lombok.Data;

@Data
public class OrderDetail {

  private long detailId;
  private String orderNumber;
  private long packageId;
  private String type;

}
