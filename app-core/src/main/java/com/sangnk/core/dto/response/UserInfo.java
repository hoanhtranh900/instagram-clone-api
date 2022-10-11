package com.sangnk.core.dto.response;

import com.sangnk.core.dto.wso2is.ISTokenInfo;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long userId;
    private String username;
    private String mobileAlias;
    private String email;
    private ISTokenInfo accessTokenInfo;

}
