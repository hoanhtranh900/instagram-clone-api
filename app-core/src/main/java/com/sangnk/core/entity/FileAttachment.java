package com.sangnk.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FILE_ATTACHMENT", indexes = {
        @Index(name = "OBJECT_ID", columnList = "OBJECT_ID", unique = false),
        @Index(name = "OBJECT_TYPE", columnList = "OBJECT_TYPE", unique = false),
        @Index(name = "FILE_SERVICE_ID", columnList = "FILE_SERVICE_ID", unique = false),
})
@org.hibernate.annotations.Table(appliesTo = "FILE_ATTACHMENT", comment = "Table máp file với chức năng tương ứng")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileAttachment implements Serializable, Creatable, Updatable, Deletable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OBJECT_ID")
    private Long objectId;
    
    @Column(name = "OBJECT_TYPE")
    private Long objectType;

    @Column(name = "FILE_NAME", length = 150)
    private String fileName;

    @Column(name = "FILE_EXTENTION", length = 50)
    private String fileExtention;

    @Column(name = "FILE_PATH", length = 1000)
    private String filePath;

    @Column(name = "NOTE", length = 500)
    private String note;

    @Column(name = "IS_SIGNED")
    private Long isSigned;

    @Column(name = "IS_ENCRYPT")
    private Long isEncrypt;

    @Column(name = "SIGNED_DOC_ID", length = 100)
    private String signedDocId;

    @Column(name = "FILE_SERVICE_ID")
    private Long fileServiceId;

    @Column(name = "STORAGE_TYPE", length = 100)
    private String storageType;

    @Column(name = "CONTENT_TYPE", length = 100)
    private String contentType;

    @Column(name = "PDF_ALREADY")
    private Long pdfAlready;


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
