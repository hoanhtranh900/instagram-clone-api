package com.sangnk.security;

import com.sangnk.core.contants.Constants;
import com.sangnk.core.security.CustomAuthenticationEntryPoint;
import com.sangnk.core.security.CustumAccessDeniedhandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

/**
 * TODO: write you class description here
 *
 * @author
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private CrmUserDetailsService crmUserDetailsService;
    @Value("${jwt.publicKeyFile}")
    private String publicKeyFile;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RestTemplate newRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.crmUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers(Constants.WEB_IGNORING);
    }

    @Bean
    public TokenAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() throws Exception {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustumAccessDeniedhandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .anonymous().disable()
                .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)
                .requestMatchers()
                .antMatchers("/**")
            .and()
                .authorizeRequests()
                .antMatchers(Constants.WEB_IGNORING).permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
            .and()
                .logout()
                .logoutUrl("/logout-jwt")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
        ;
        http.headers().frameOptions().sameOrigin();//X-Frame-Options deny frame
    }
}
