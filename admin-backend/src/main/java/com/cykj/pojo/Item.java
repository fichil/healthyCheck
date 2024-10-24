package com.cykj.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Item {

  private long itemId;
  private String itemName;
  private double price;
  private long isDeleted;

  private List<DetailItem> detailItems = new ArrayList<>();
}
