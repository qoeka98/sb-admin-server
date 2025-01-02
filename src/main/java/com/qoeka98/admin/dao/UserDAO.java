package com.qoeka98.admin.dao;

import com.qoeka98.admin.dto.RequsetUserDTO;
import com.qoeka98.admin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

 public int  createdUser(RequsetUserDTO requsetUserDTO){
        String sql = "INSERT INTO user(email, password,nickname,`role`)\n" +
                "values (?,?,?,\"admin\");";
        return jdbcTemplate.update(sql,requsetUserDTO.email,requsetUserDTO.password,requsetUserDTO.nickname);
    }

    public User userLogin(RequsetUserDTO requsetUserDTO){
        String sql = "SELECT *\n" +
                "FROM user\n" +
                "where email = ? and role ='admin'\n" +
                ";";
        return jdbcTemplate.queryForObject(sql,new userRowMapper(),requsetUserDTO.email);
    }

    public static class userRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.id = rs.getLong("id");
            user.email = rs.getString("email");
            user.password = rs.getString("password");
            user.nickname = rs.getString("nickname");
            user.role = rs.getString("role");
            user.createdAt =rs.getString("created_at");
            return user;
        }
    }





}
