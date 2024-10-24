package com.cykj.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

  private long menuId;
  private String menuName;
  private String menuRoute;
  private String menuPath;
  private String menuIcon;
  private long parentMenuId;
  private long isDeleted;
  private String status;

  private List<Menu> children = new ArrayList<>();
}
