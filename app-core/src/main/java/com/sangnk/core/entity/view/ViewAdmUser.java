
package com.sangnk.core.entity.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.CategoryBuffer;

import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
public class ViewAdmUser  implements Serializable, Creatable, Updatable, Deletable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "USER_NAME")
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TYPE")
    private Long type;

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

    @Transient
    private String avatar;

    public String getAvatar() {
        return CategoryBuffer.getUserAvataById(id);
    }

    @org.hibernate.annotations.Comment("ID người tạo")
    @Column(name = "CREATOR_ID")
    private Long creatorId;

    @Column(name = "CREATOR_NAME")
    private String creatorName;

    @Column(name = "CREATE_TIME")
    private Date createTime;
    @Column(name = "UPDATER_ID")
    private Long updatorId;
    @Column(name = "UPDATER_NAME")
    private String updatorName;
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    @Override
    public Long getCreatorId() {
        return this.creatorId;
    }

    @Override
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getCreatorName() {
        return this.creatorName;
    }

    @Override
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Long getIsDelete() {
        return this.isDelete;
    }

    @Override
    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public Long getUpdatorId() {
        return this.updatorId;
    }

    @Override
    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    @Override
    public String getUpdatorName() {
        return this.updatorName;
    }

    @Override
    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
