package com.cykj.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Item {

  private long itemId;
  private String itemName;
  private double price;
  private long isDeleted;

  private ArrayList<DetailItem> detailItems = new ArrayList<>();
}
