package com.cykj.pojo;

import lombok.Data;

@Data
public class Orders {

  private long orderId;
  private String orderNumber;
  private long userId;
  private java.sql.Timestamp orderDate;
  private double totalAmount;
  private String paymentStatus;

}
