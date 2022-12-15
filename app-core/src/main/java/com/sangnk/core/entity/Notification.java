package com.sangnk.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import com.sangnk.core.utils.DateUtils;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "NOTIFICATION")
@org.hibernate.annotations.Table(appliesTo = "NOTIFICATION", comment = "Table Th?ng b?o ? qu? chu?ng")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notification implements Serializable, Creatable, Updatable, Deletable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "FROM_USER_ID")
    private Long fromUserId;

    @Column(name = "FROM_USER_NAME")
    private String fromUserName;


    @Column(name = "TO_USER_ID")
    private Long toUserId;

    @Column(name = "TO_USER_NAME")
    private String toUserName;

    @Column(name = "FROM_FULL_NAME")
    private String fromFullName;

    @Column(name = "TO_FULL_NAME")
    private String toFullName;

    //content
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "TYPE")
    private Long type = 1L; //=2 s? show ? th?ng b?o n?i b? m?n trang ch?

    @Column(name = "IS_READ")
    private Long isRead = 0L; // 0 = ch?a ??c, 1 = ??c

    @org.hibernate.annotations.Comment("Tr?ng th?i x?a")
    @Column(name = "IS_DELETE", columnDefinition = "bigint default 0")
    private Long isDelete = ConstantString.IS_DELETE.active;
    @org.hibernate.annotations.Comment("ID ng??i t?o")
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

    @Transient
    private String createTimeStr;

    public String getCreateTimeStr() {
        return DateUtils.convertDateToString(this.createTime);
    }


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
