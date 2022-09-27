package com.sangnk.core.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String username;
    private String mobileAlias;
    private String email;
    private ISTokenInfo accessTokenInfo;

}
