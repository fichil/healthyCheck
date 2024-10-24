package com.cykj.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Packages {

  private long packageId;
  private String packageName;
  private double price;
  private long isDeleted;

  private List<Item> items = new ArrayList<>();


}
