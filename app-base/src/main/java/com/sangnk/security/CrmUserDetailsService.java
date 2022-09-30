package com.sangnk.security;

import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.repository.AdmUserRepository;
import com.sangnk.core.utils.UtilsCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: write you class description here
 *
 * @author
 */

@Service
public class CrmUserDetailsService implements UserDetailsService {

    @Autowired private AdmUserRepository userRepository;

    @Autowired private MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        AdmUser user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.login_fail", null, UtilsCommon.getLocale())));
        if (user == null) {
            throw new UsernameNotFoundException(messageSource.getMessage("error.login_fail", null, UtilsCommon.getLocale()));
        }
        return user;
    }

}
