package com.qoeka98.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatResponse {

    public TotalResponse total;
    public List<DateResponse> byDate;
    public List<CategoryResponse> byCategory;

}
