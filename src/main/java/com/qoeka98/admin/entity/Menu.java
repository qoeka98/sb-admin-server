package com.qoeka98.admin.entity;

public class Menu {

  public Integer id;
  public Integer restaurantId;
  public String name;
  public Integer price;
  public String description;
  public String category;
  public String createdAt;

    public Menu() {
    }

    public Menu(Integer id, Integer restaurantId, String name, Integer price, String description, String category, String createdAt) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
    }
}
