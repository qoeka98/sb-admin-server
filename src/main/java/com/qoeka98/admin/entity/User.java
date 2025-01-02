package com.qoeka98.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class User {


    public Long id;
    public String email;
    public String password;
    public String nickname;
    public String role;
    public String createdAt;



}
