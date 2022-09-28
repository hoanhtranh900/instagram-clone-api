package com.sangnk.core.entity;

import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import com.sangnk.core.utils.UtilsDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable implements Creatable, Updatable, Deletable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long isDelete;
    private Long creatorId;
    private String creatorName;
    private Date createTime;
    private Long updatorId;
    private String updatorName;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
