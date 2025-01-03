package com.qoeka98.admin.service;

import com.qoeka98.admin.config.JwtConfig;
import com.qoeka98.admin.dao.CRMDAO;
import com.qoeka98.admin.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CRMService {
@Autowired
CRMDAO crmDAO;
@Autowired
JwtConfig jwtConfig;

    public ReviewerListResponse getTopReview(String token, int size) {
        Long userId = Long.parseLong(jwtConfig.getTokenClaims(token.substring(7)).getSubject());
        List<ReviewResponse> reviewResponseList = crmDAO.getTopReviewers(userId, size);
        return new ReviewerListResponse(reviewResponseList);
    }


   public  StatResponse dateReview(String token, String startDate , String endDate){
    Long userId = Long.parseLong(jwtConfig.getTokenClaims(token.substring(7)).getSubject());

        TotalResponse reviewResponseList = crmDAO.getTotal(userId,startDate, endDate);
        TotalResponse totalResponse = crmDAO.getTotal(userId,startDate, endDate);
        List<DateResponse> dateResponses = crmDAO.getdate(userId,startDate,endDate);
        List<CategoryResponse> categoryResponses= crmDAO.getBycategory(userId,startDate,endDate);

       StatResponse statResponse = new StatResponse(totalResponse, dateResponses, categoryResponses);

        return statResponse;


    }

 public  PopularListResponse getTop(String token, String category , Integer minReview) {
     Long userId = Long.parseLong(jwtConfig.getTokenClaims(token.substring(7)).getSubject());
     if (category == null && minReview == null) {
               List<TopResponse> topResponseList = crmDAO.getTop();
               return new PopularListResponse(topResponseList);
           } else if (category != null && minReview == null) {
               List<TopResponse>  topResponseList = crmDAO.getTop(category);
               return new PopularListResponse(topResponseList);
           } else if (category == null && minReview != null) {
               List<TopResponse>   topResponseList = crmDAO.getTop(minReview);
               return new PopularListResponse(topResponseList);
           } else {
               List<TopResponse>   topResponseList = crmDAO.getTop(category, minReview);
               return new PopularListResponse(topResponseList);
           }

 }


 }