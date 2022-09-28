package com.sangnk.core.config;

import com.sangnk.core.entity.*;
import com.sangnk.core.repository.*;
import com.sangnk.core.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BeanInitDatabase {

    @Autowired private Environment env;

    @Autowired private AdmUserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilsService utilsService;

    @Bean
    public void initDataDatabase() {

        if (!userRepository.findByUsername("admin").isPresent()) {
            utilsService.save(userRepository, AdmUser.builder().isDelete(0L).type(0L).status(0L).username("admin").password(passwordEncoder.encode("admin")).build());
        }
    }

}
