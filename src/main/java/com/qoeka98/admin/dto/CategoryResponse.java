package com.qoeka98.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryResponse {


    public String category;
    public Integer reviewCount;
    public Double averageRating;

}
