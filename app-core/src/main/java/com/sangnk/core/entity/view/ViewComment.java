package com.sangnk.core.entity.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Post;
import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import com.sangnk.core.utils.UtilsDate;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from COMMENTS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ViewComment  implements Serializable, Creatable, Updatable, Deletable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "BODY")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AdmUser user;

    @org.hibernate.annotations.Comment("Trạng thái xóa")
    @Column(name = "IS_DELETE", columnDefinition = "bigint default 0")
    private Long isDelete = ConstantString.IS_DELETE.active;
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

    @Transient
    private String createTimeStr;

    public String getCreateTimeStr() {
        return UtilsDate.date3str(this.createTime);
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
