package com.sangnk.service.impl;

import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.request.LoginRequest;
import com.sangnk.core.dto.response.ISTokenInfo;
import com.sangnk.core.dto.response.UserInfo;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.exception.UnauthorizedException;
import com.sangnk.core.repository.*;
import com.sangnk.core.security.TokenHelper;
import com.sangnk.core.utils.UtilsCommon;
import com.sangnk.service.SrsSystemSercice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SrsSystemSerciceImpl implements SrsSystemSercice {

    @Autowired
    private AdmUserRepository admUserRepository;
    @Autowired
    private TokenHelper tokenHelper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserInfo loginJWT(LoginRequest loginRequest) throws UnauthorizedException {

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            String session = servletRequestAttributes.getSessionId();

            AdmUser user = (AdmUser) authentication.getPrincipal();


            UserInfo userInfo = new UserInfo();
            // Trả về jwt cho người dùng.
            String jwt = tokenHelper.generateToken(loginRequest, user, session);
            ISTokenInfo accessToken = new ISTokenInfo();
            accessToken.setAccessToken(jwt);
            accessToken.setTokenType("Bearer");
            accessToken.setExpiresIn(tokenHelper.getClaimsFromToken(jwt).getExpiration().getTime());
            userInfo.setUsername(user.getUsername());
            userInfo.setMobileAlias(user.getPhoneNumber());
            userInfo.setEmail(user.getEmail());
            userInfo.setAccessTokenInfo(accessToken);


            return userInfo;
        } catch (BadCredentialsException e) {
            log.error(loginRequest.getUsername() + "|" + e.getMessage());
            Locale locale = LocaleContextHolder.getLocale();
            LocaleContextHolder.setDefaultLocale(locale);
            Locale locale1 = LocaleContextHolder.getLocaleContext().getLocale();
            String msg = messageSource.getMessage("MSG_LOGIN_FAILED", null, locale);
            throw new UnauthorizedException(msg);
        } catch (AccessDeniedException ex1) {
            log.error(loginRequest.getUsername() + "|" + ex1.getMessage());
            throw new AccessDeniedException(messageSource.getMessage("error_401", null, UtilsCommon.getLocale()));
        } catch (LockedException ex) {
            log.error(loginRequest.getUsername() + "|" + ex.getMessage());
            throw new UnauthorizedException(messageSource.getMessage("MSG_LOCKED_ACCOUNT", null, UtilsCommon.getLocale()));
        } catch (DisabledException exx) {
            log.error(loginRequest.getUsername() + "|" + exx.getMessage());
            throw new UnauthorizedException(messageSource.getMessage("MSG_DISABLE_ACCOUNT", null, UtilsCommon.getLocale()));
        } catch (AuthenticationException atx) {
            log.error(loginRequest.getUsername() + "|" + atx.getMessage());
            throw new UnauthorizedException(messageSource.getMessage("NOTE_EVICT", null, UtilsCommon.getLocale()));
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException ex) {
            log.error(loginRequest.getUsername() + "|" + ex.getMessage());
            throw new UnauthorizedException(messageSource.getMessage("error.login_fail", null, UtilsCommon.getLocale()));
        }
    }

    @Override
    public UserInfo getAccessTokenByRefreshTokenJwt(String refreshToken) throws UnauthorizedException {

        return null;
    }

    @Override
    public boolean logoutJwt() {
        try {
            Optional<AdmUser> us = UtilsCommon.getUserLogin();
            if (us.isPresent()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


}
