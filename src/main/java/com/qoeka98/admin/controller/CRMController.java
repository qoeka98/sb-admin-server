package com.qoeka98.admin.controller;

import com.qoeka98.admin.dto.PopularListResponse;
import com.qoeka98.admin.dto.StatResponse;
import com.qoeka98.admin.dto.ReviewerListResponse;
import com.qoeka98.admin.service.CRMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CRMController {
@Autowired
CRMService crmService;


    @GetMapping("/api/v1/admin/crm/users/top-reviewers")
public ResponseEntity<ReviewerListResponse>    getTopReviewers(@RequestHeader("Authorization") String token,
                                                               @RequestParam("size") int size) {
      ReviewerListResponse reviewerListResponse = crmService.getTopReview(token, size);
        return ResponseEntity.status(200).body(reviewerListResponse);
    }

    @GetMapping("/api/v1/admin/crm/reviews/stats")
    public ResponseEntity<StatResponse> getStats(@RequestHeader("Authorization") String token,

                                                 @RequestParam("startDate")
    String startDate,
                                                 @RequestParam("endDate")
    String endDate){
    StatResponse statResponse = crmService.dateReview(token ,startDate, endDate);

        return ResponseEntity.status(200).body(statResponse);
    }

    @GetMapping("/api/v1/admin/crm/restaurants/popular")
   public ResponseEntity<PopularListResponse> toprestaurants(@RequestHeader ("Authorization") String token,
                                                             @RequestParam(required = false) String category
            , @RequestParam(required = false) Integer minReview){
     PopularListResponse allTopResponse = crmService.getTop(token, category, minReview);
     return ResponseEntity.status(200).body(allTopResponse);
    }
    }



