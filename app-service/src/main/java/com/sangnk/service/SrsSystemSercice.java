package com.sangnk.service;

import com.sangnk.core.dto.request.LoginRequest;
import com.sangnk.core.dto.response.UserInfo;
import com.sangnk.core.exception.UnauthorizedException;

/*
* Service dành cho logic hệ thống
* */
public interface SrsSystemSercice {
    //Login đăng nhập JWT
    UserInfo loginJWT(LoginRequest loginRequest) throws UnauthorizedException;
    //Login đăng nhập JWT By refreshToken
    UserInfo getAccessTokenByRefreshTokenJwt(String refreshToken) throws UnauthorizedException;
    //Logout JWT
    boolean logoutJwt();


}
