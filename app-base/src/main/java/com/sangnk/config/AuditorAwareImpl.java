package com.sangnk.config;

import com.sangnk.core.contants.Constants;
import com.sangnk.core.entity.AdmUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 *
 * @author DELL
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        if (null != SecurityContextHolder.getContext().getAuthentication() && getUsername()) {
            return Optional.ofNullable(((AdmUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        }
        return Optional.empty();
    }

    private boolean getUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !username.equalsIgnoreCase(Constants.ANONYMOUS);
    }

}
