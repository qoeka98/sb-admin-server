package com.qoeka98.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DateResponse {

    public String date;
    public Integer reviewCount;
    public Double averageRating;
}
