package com.qoeka98.admin.service;

import com.qoeka98.admin.config.JwtConfig;
import com.qoeka98.admin.dao.UserDAO;
import com.qoeka98.admin.dto.RequsetUserDTO;
import com.qoeka98.admin.entity.User;
import com.qoeka98.admin.util.EmailValidator;
import com.qoeka98.admin.util.NickNameValidator;
import com.qoeka98.admin.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtConfig jwtConfig;


    public int createdUser(RequsetUserDTO requsetUserDTO) {
//      int result = userDAO.createdUser(requsetUserDTO);
//      - email: @foodreview.com 도메인만 허용
//      - password: 최소 10자 이상, 영문 대/소문자/숫자/특수문자 모두 포함 필수
//              - nickname: 2-20자 이내, 한글/영문/숫자 허용
        if (EmailValidator.isValidEmail(requsetUserDTO.email) == false)
            return 1;
        if (PasswordValidator.isValid(requsetUserDTO.password) == false)
            return 2;
        if (NickNameValidator.isValid(requsetUserDTO.nickname) == false)
            return 3;
//암호화
         String password = passwordEncoder.encode(requsetUserDTO.password);
         requsetUserDTO.password = password;
        //DAO 넘김
        try {
            userDAO.createdUser(requsetUserDTO);

        } catch (Exception e) {
            return 4;

        }
        return 0;
    }

    public Object Login(RequsetUserDTO requsetUserDTO) {
        //이메일 유효한지 체크
        if (EmailValidator.isValidEmail(requsetUserDTO.email) == false)
            return 1;
        //디비 데이터 자져온다.
        //유저가 없는지 확인 한다. 그리고 패스워드 확인하고 토큰 만들어 반환한다

        try {
            User user = userDAO.userLogin(requsetUserDTO);

            if (passwordEncoder.matches(requsetUserDTO.password, user.getPassword()) == false) {
                return 3;
            }
            //토큰 만들어서 반환
            String token = jwtConfig.createToken(user.id);
            return token;


        } catch (Exception e) {
            // 유저가 없는지 확인
            return 2;
        }


    }

}




