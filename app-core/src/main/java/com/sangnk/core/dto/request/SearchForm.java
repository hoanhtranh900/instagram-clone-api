package com.sangnk.core.dto.request;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {
    private boolean init = false;

    private String userId;
    private String fullName;
    private String position;
    private String email;
    private String address;
    private String unitId;
    private String phone;
    private String status;
    private String isDelete;
    private String username;
    private String phoneNumber;
    private String typeRegistration;

    //POST
    private String description;


}
