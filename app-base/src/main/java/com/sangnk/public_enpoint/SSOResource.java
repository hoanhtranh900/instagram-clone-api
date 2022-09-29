package com.sangnk.public_enpoint;

import com.sangnk.core.contants.Constants;
import com.sangnk.core.dto.request.LoginRequest;
import com.sangnk.core.dto.response.ResponseData;
import com.sangnk.core.dto.response.UserInfo;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.exception.BadRequestException;
import com.sangnk.core.exception.Result;
import com.sangnk.core.exception.UnauthorizedException;
import com.sangnk.core.utils.UtilsCommon;
import com.sangnk.service.SrsSystemSercice;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@RestController
@RequestMapping(value = "/public/v1/sso", produces = MediaType.APPLICATION_JSON_VALUE)
public class SSOResource {

    @Autowired private MessageSource messageSource;
    @Autowired private SrsSystemSercice srsSystemSercice;

    @ApiOperation(response = UserInfo.class, notes = Constants.NOTE_API + "empty_note", value = "Login đăng nhập", authorizations = {@Authorization(value = Constants.API_KEY)})
    @PostMapping(value = "/jwt-login")
    public ResponseEntity<ResponseData> loginJWT(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) throws UnauthorizedException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println(request.getHeader("Accept-Language"));
        log.info("language current: " + UtilsCommon.getLocale() + ", examle error_401: " + messageSource.getMessage("error_401", null, UtilsCommon.getLocale()));
        loginRequest.setIp(request.getRemoteAddr());
        loginRequest.setUserAgent(request.getRemoteUser());
        return new ResponseEntity<>(new ResponseData<>(srsSystemSercice.loginJWT(loginRequest), Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = Boolean.class, notes = Constants.NOTE_API + "empty_note", value = "logout wso2is", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping(value = "/jwt-logout")
    public ResponseEntity<ResponseData> logoutWso2IS() {
        return new ResponseEntity<>(new ResponseData<>(srsSystemSercice.logoutJwt(), Result.SUCCESS), HttpStatus.OK);
    }


    @ApiOperation(response = AdmUser.class, notes = Constants.NOTE_API + "empty_note", value = "Đăng ký tài khoản", authorizations = {@Authorization(value = Constants.API_KEY)})
    @PostMapping(path = "/register")
    public ResponseEntity<ResponseData> register(@RequestBody @Valid AdmUser user) throws BadRequestException, UnauthorizedException {
        return new ResponseEntity<>(new ResponseData<>(srsSystemSercice.register(user), Result.SUCCESS), HttpStatus.OK);
    }


}
