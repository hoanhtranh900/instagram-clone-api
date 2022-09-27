package com.sangnk.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangnk.core.contants.Constants;
import com.sangnk.core.dto.response.JwtAuthDto;
import com.sangnk.core.security.TokenBasedAuthentication;
import com.sangnk.core.security.TokenHelper;
import com.sangnk.core.utils.UtilsHttp;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.repository.AdmUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * TODO: write you class description here
 *
 * @author
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    CrmUserDetailsService userDetailService;
    @Autowired
    private TokenHelper tokenHelper;
    @Autowired
    private AdmUserRepository userRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ObjectMapper mapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean isWebIgroring = false;
        String contextPath = request.getContextPath();
        for (String check : Constants.WEB_IGNORING) {
            isWebIgroring = new AntPathMatcher().match(contextPath + check, request.getRequestURI());
            if (isWebIgroring) {
                break;
            }
        }
        return isWebIgroring;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("url " + request.getRequestURL());
        String accessToken = UtilsHttp.getToken(request);

        // json web token
        if (accessToken != null) {
            String username = tokenHelper.getUsernameFromToken(accessToken);
            if (StringUtils.isNotBlank(username)) {
                Optional<AdmUser> userOpt = this.userRepository.findByUsername(username);
                if (userOpt.isPresent()) {
                    JwtAuthDto jwtAuthDto = tokenHelper.getJWTInfor(accessToken);
                    AdmUser user = (AdmUser) this.userDetailService.loadUserByUsername(username);
                    user.setIpAddress(jwtAuthDto.getIpAddress());
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(user, true);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }

        // wso2 is
        /*if (accessToken != null) {
            ISUserInfo iSUserInfo = this.wso2ISService.getUserInfo(accessToken);
            if (iSUserInfo != null) {
                AdmUser userDetails = (AdmUser) this.userDetailService.loadUserByUsername(iSUserInfo.getSub());
                TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails, true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            SecurityContextHolder.clearContext();
        }*/

        chain.doFilter(request, response);
    }

}
