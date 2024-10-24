package com.cykj.pojo;

import lombok.Data;

@Data
public class Staff {

  private long staffId;
  private String account;
  private String password;
  private String nickname;
  private long roleId;
  private long departmentId;
  private String status;
  private long isDeleted;

}
