
package com.sangnk.core.entity.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.Auditable;
import com.sangnk.core.utils.H;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
* Tạo view bằng @Subselect
* */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from ADM_USERS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ViewAdmUser extends Auditable implements Serializable {


    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "COMMUNE_ID")
    private Long communeId;

    @Column(name = "USER_NAME")
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "GIVEN_NAME")
    private String givenName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "IS_DELETE")
    private Long isDelete;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "GENDER", length = 1)
    private Long gender;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Transient
    private String statusStr;
    @Transient
    private String isDeleteStr;
    @Transient
    private String addressFull;

}
