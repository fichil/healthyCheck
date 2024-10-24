package com.cykj.pojo;

import lombok.Data;

@Data
public class User {

  private long userId;
  private String phone;
  private String idNumber;
  private String name;
  private String gender;
  private long age;
  private double balance;
  private String password;
  private long isDeleted;

}
