package com.qoeka98.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewResponse {

    public Long userId;
    public String nickname;
    public Integer reviewCount;
    public Double averageRating;
    public String lastReviewDate;



}
