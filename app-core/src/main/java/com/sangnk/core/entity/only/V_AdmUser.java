package com.sangnk.core.entity.only;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.Auditable;
import com.sangnk.core.utils.H;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from ADM_USER")
public class V_AdmUser extends Auditable implements Serializable {

    @Column(name = "AVATAR_ID")
    private Long avatarId;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "COMMUNE_ID")
    private Long communeId;

    @Column(name = "UNIT_ID")
    private Long unitId;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "ADDRESS")
    private String address;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

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

    @Column(name = "EMAIL", unique = true, length = 50)
    private String email;

    @Transient
    private String statusStr;
    @Transient
    private String isDeleteStr;

    @Transient
    private String addressFull;

    public String getStatusStr() {
        return ConstantString.STATUS.getStatusStr(this.status);
    }

    public String getIsDeleteStr() {
        return ConstantString.IS_DELETE.getStatusStr(this.isDelete);
    }

}
