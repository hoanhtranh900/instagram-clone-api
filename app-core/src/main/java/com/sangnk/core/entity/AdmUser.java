
package com.sangnk.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.utils.H;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADM_USERS", indexes = {
        @Index(name = "USER_NAME", columnList = "USER_NAME", unique = true),
        @Index(name = "STATUS", columnList = "STATUS", unique = false)
})
@org.hibernate.annotations.Table(appliesTo = "ADM_USERS", comment = "Table người dùng")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdmUser extends Auditable implements Serializable, UserDetails {

    public AdmUser(String username, String password, List<GrantedAuthority> authorities) {
        this.username=username;
        this.password=password;
        this.grantedAuths=authorities;
    }


    @Transient
    private FileAttachDocument fileAvatar;

    @NotEmpty(message = "{error.required.username}")
    @Comment("Tên đăng nhập")
    @Column(name = "USER_NAME", length = 100, unique = true)
    private String username;

    @Comment("Chức vụ")
    @Column(name = "POSITION", length = 100)
    private String position;

    @Comment("Ngày sinh")
    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Comment("Địa chỉ chi tiết")
    @Column(name = "ADDRESS", length = 200)
    private String address;

    @NotEmpty(message = "{error.required.password}")
    @Comment("Mật khẩu")
    @Column(name = "PASSWORD", length = 300)
    private String password;

    @Comment("Số điện thoại")
    @Column(name = "PHONE_NUMBER", length = 100)
    private String phoneNumber;

    @Comment("Họ")
    @Column(name = "SURNAME", length = 100)
    private String surname;

    @Comment("Giới tính")
    @Column(name = "GENDER", length = 1)
    private Long gender;


    @Comment("Tên")
    @Column(name = "GIVEN_NAME", length = 100)
    private String givenName;

    @Comment("Tên đầy đủ")
    @Column(name = "FULL_NAME", length = 300)
    private String fullName;

    @Comment("Trạng thái hoạt động")
    @Column(name = "STATUS", columnDefinition = "bigint default 0")
    private Long status = ConstantString.STATUS.active;

    @Comment("Trạng thái xóa")
    @Column(name = "IS_DELETE", columnDefinition = "bigint default 0")
    private Long isDelete = ConstantString.IS_DELETE.active;

    @Email(message = "{error.pattern.EMAIL_ERROR}")
    @Comment("EMAIL")
    @Column(name = "EMAIL", unique = true, length = 50)
    private String email;

    @Transient
    private String ipAddress;
    @Transient
    private String rePassWord;
    @Transient
    private String oldPassWord;
    @Transient
    private List<GrantedAuthority> grantedAuths;
    @Transient
    private String statusStr;
    @Transient
    private String isDeleteStr;
    @Transient
    private String addressFull;
    @Transient
    private List<Long> groupIds;

    public String getStatusStr() {
        return ConstantString.STATUS.getStatusStr(this.status);
    }

    public String getIsDeleteStr() {
        return ConstantString.IS_DELETE.getStatusStr(this.isDelete);
    }



    //update
    public AdmUser formToBo(AdmUser form, AdmUser bo) {
        bo.setAddress(form.getAddress());
        bo.setPhoneNumber(form.getPhoneNumber());
        bo.setSurname(form.getSurname());
        bo.setGivenName(form.getGivenName());
        bo.setFullName(form.getFullName());
        bo.setStatus(form.getStatus());
        bo.setEmail(form.getEmail());
        bo.setBirthday(form.getBirthday());
        bo.setPosition(form.getPosition());
        bo.setGender(form.getGender());
        return bo;
    }

    public AdmUser updateProfile(AdmUser form, AdmUser bo) {
        bo.setPhoneNumber(form.getPhoneNumber());
        bo.setSurname(form.getSurname());
        bo.setGivenName(form.getGivenName());
        bo.setFullName(form.getFullName());
        bo.setStatus(form.getStatus());
        bo.setEmail(form.getEmail());
        bo.setBirthday(form.getBirthday());
        bo.setPosition(form.getPosition());
        bo.setGender(form.getGender());
        return bo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isDelete != null && this.isDelete.compareTo(ConstantString.IS_DELETE.active) == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status != null && this.status.compareTo(ConstantString.STATUS.active) == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status != null && this.status.compareTo(ConstantString.STATUS.active) == 0;
    }
}
