package com.qoeka98.admin.entity;

public class Restaurant {

public   Long  id;
  public String name;
  public String address;
  public Integer phone;
  public String category;
  public String description;
  public String createdAt;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address, Integer phone, String category, String description, String createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.category = category;
        this.description = description;
        this.createdAt = createdAt;
    }
}
