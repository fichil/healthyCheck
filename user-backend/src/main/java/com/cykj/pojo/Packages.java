package com.cykj.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Packages {

  private long packageId;
  private String packageName;
  private double price;
  private long isDeleted;
  private String img;

  private ArrayList<Item> items = new ArrayList<>();

}
