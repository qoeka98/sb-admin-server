package com.qoeka98.admin.dao;

import com.qoeka98.admin.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CRMDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ReviewResponse> getTopReviewers(long userId, int size) {
        String sql = "select u.id, u.nickname, \n" +
                "\t\tcount(r.id) reviewCount, \n" +
                "\t    ROUND( IFNULL( avg(r.rating) , 0 ) , 2) averageRating  , \n" +
                "\t\tmax( r.created_at ) lastReviewDate\n" +
                "from user u \n" +
                "left join review r \n" +
                "on u.id = r.user_id\n" +
                "where role = 'USER'\n" +
                "group by u.id \n" +
                "order by reviewCount desc, averageRating desc\n" +
                "limit ? ;";
        return jdbcTemplate.query(sql, new ReviewerRowMapper(), size);
    }

    public static class ReviewerRowMapper implements RowMapper<ReviewResponse> {

        @Override
        public ReviewResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReviewResponse reviewerResponse = new ReviewResponse();
            reviewerResponse.userId = rs.getLong("id");
            reviewerResponse.nickname = rs.getString("nickname");
            reviewerResponse.reviewCount = rs.getInt("reviewCount");
            reviewerResponse.averageRating = rs.getDouble("averageRating");
            reviewerResponse.lastReviewDate = rs.getString("lastReviewDate");
            return reviewerResponse;
        }
    }

//  crmDAO.getTotal(userId,startDate, endDate);
//    crmDAO.getdate;
//    crmDAO.getBycategor

    //===============================================================================
// 토탈 아이디랑 스타트 데이트 엔드 데이트 하는거


    public TotalResponse getTotal(Long userId, String startDate, String endDate) {
        String sql = "SELECT  count(*) reviewCount , avg(rating) averageRating\n" +
                "from review\n" +
                "where created_at BETWEEN ? and ?;";
        return jdbcTemplate.queryForObject(sql, new TotalRowMapper(), startDate, endDate);

    }


    public static class TotalRowMapper implements RowMapper<TotalResponse> {
        @Override
        public TotalResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            TotalResponse totalResponse = new TotalResponse();
            totalResponse.reviewCount = rs.getInt("reviewCount");
            totalResponse.averageRating = rs.getDouble("averageRating");
            return totalResponse;
        }


    }
    // ================================================================================
    // 1. getdate

    public List<DateResponse> getdate(Long userId, String startDate, String endDate) {
        String sql = "SELECT date( created_at ) as date, \n" +
                "\t\t\tcount(*) reviewCount,  \n" +
                "\t\t\tavg(rating) averageRating\n" +
                "from review\n" +
                "where created_at BETWEEN ? and ? \n" +
                "group by date( created_at )\n" +
                "order by date asc;";
        return jdbcTemplate.query(sql, new DateRowMapper(), startDate, endDate);
    }

    public static class DateRowMapper implements RowMapper<DateResponse> {

        @Override
        public DateResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            DateResponse detailResponse = new DateResponse();
            detailResponse.date = rs.getString("date");
            detailResponse.reviewCount = rs.getInt("reviewCount");
            detailResponse.averageRating = rs.getDouble("averageRating");
            return detailResponse;
        }
    }
// ==================================================================
// 2. getBycategory

    public List<CategoryResponse> getBycategory(Long userId, String startDate, String endDate) {
        String sql = "SELECT rt.category  , \n" +
                "\t\t\tcount(*) reviewCount,  \n" +
                "\t\t\tavg( r.rating ) averageRating\n" +
                "from review r\n" +
                "join restaurant rt \n" +
                "on r.restaurant_id = rt.id\n" +
                "where r.created_at BETWEEN ? and ? \n" +
                "GROUP by rt.category\n" +
                "order by reviewCount desc;";
        return jdbcTemplate.query(sql, new CategoryRowMapper(), startDate, endDate);

    }

    public static class CategoryRowMapper implements RowMapper<CategoryResponse> {
        @Override
        public CategoryResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.category = rs.getString("category");
            categoryResponse.averageRating = rs.getDouble("averageRating");
            categoryResponse.reviewCount = rs.getInt("reviewCount");
            return categoryResponse;
        }
    }

   public List<TopResponse> getTop() {
        String sql = "SELECT r.id,r.name,category,COUNT(r.id)reviewCount,\n" +
                "ROUND( IFNULL(AVG(r2.rating),0),1)averageRating,max(r2.created_at) lastReviewDate \n" +
                "FROM restaurant r \n" +
                "join review r2 \n" +
                "on r.id  = r2.restaurant_id \n" +
                "GROUP by r.id\n" +
                "order by r.created_at desc;\n";

        return jdbcTemplate.query(sql, new TopRowMapper());
    }

    public List<TopResponse> getTop(String category) {
        String sql = "SELECT r.id,r.name,category,COUNT(r.id)reviewCount,\n" +
                "ROUND( IFNULL(AVG(r2.rating),0),1)averageRating,max(r2.created_at) lastReviewDate \n" +
                "FROM restaurant r \n" +
                "join review r2 \n" +
                "on r.id  = r2.restaurant_id \n" +
                "WHERE r.category = ?\n" +
                "GROUP by r.id \n" +
                "order by reviewCount desc;\n";
        return jdbcTemplate.query(sql, new TopRowMapper(), category);
    }

    public List<TopResponse> getTop(int minReview) {
        String sql = "SELECT r.id,r.name,category,COUNT(r.id)reviewCount,\n" +
                "ROUND( IFNULL(AVG(r2.rating),0),1)averageRating,max(r2.created_at) lastReviewDate \n" +
                "FROM restaurant r \n" +
                "join review r2 \n" +
                "on r.id  = r2.restaurant_id \n" +
                "GROUP by r.id HAVING reviewCount >=?\n" +
                "order by reviewCount desc;\n";
        return jdbcTemplate.query(sql, new TopRowMapper(), minReview);
    }

    public List<TopResponse> getTop(String category, int minReview) {
        String sql = "SELECT r.id,category,r.name,COUNT(r.id)reviewCount,\n" +
                "ROUND( IFNULL(AVG(r2.rating),0),1)averageRating,max(r2.created_at) lastReviewDate \n" +
                "FROM restaurant r \n" +
                "join review r2 \n" +
                "on r.id  = r2.restaurant_id \n" +
                "WHERE r.category = ?\n" +
                "GROUP by r.id HAVING reviewCount >=?\n" +
                "order by reviewCount desc;\n";
        return jdbcTemplate.query(sql, new TopRowMapper(), category, minReview);
    }


    public static class TopRowMapper implements RowMapper<TopResponse> {

        @Override
        public TopResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            TopResponse topResponse = new TopResponse();
            topResponse.id = rs.getLong("id");
            topResponse.name = rs.getString("name");
            topResponse.category = rs.getString("category");
            topResponse.reviewCount = rs.getInt("reviewCount");
            topResponse.averageRating = rs.getDouble("averageRating");
            topResponse.lastReviewDate = rs.getString("lastReviewDate");
            return topResponse;
        }
    }
}




