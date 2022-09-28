package com.sangnk.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FILE_IMAGE_EXTRACT")
@org.hibernate.annotations.Table(appliesTo = "FILE_IMAGE_EXTRACT", comment = "Table lưu Image convert")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileImageExtract implements Serializable, Creatable, Updatable, Deletable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VB_ATTACHMENT_ID", nullable = true)
    private Long vbAttachmentId;

    @Column(name = "ATTACHMENT_METADATA_ID", nullable = true)
    private Long attachmentMetadataId;

    @Column(name = "FILE_SIZE", nullable = true)
    private Long fileSize;

    @Column(name = "PAGE_NUMBER", nullable = true)
    private Long pageNumber;

    @Column(name = "FILE_NAME", nullable = true)
    private String fileName;
    
    @Column(name = "FILE_EXTENTION", nullable = true, length = 50)
    private String fileExtention;
    
    @Column(name = "FILE_PATH",length = 1000)
    private String filePath;
    
    @Column(name = "NOTE", nullable = true)
    private String note;
    
    @Column(name = "IS_SIGNED", nullable = true, precision = 0)
    private Long isSigned;
    
    @Column(name = "IS_ENCRYPT", nullable = true, precision = 0)
    private Long isEncrypt;
    
    @Column(name = "STORAGE_TYPE", nullable = true, length = 100)
    private String storageType;
    
    @Column(name = "STATUS", nullable = true, length = 50)
    private Long status;
    

    @Column(name = "IS_EDITED", nullable = true, precision = 0)
    private Long isEdited;

    public FileImageExtract(FileImageExtract imageExtract) {
        this.vbAttachmentId = imageExtract.vbAttachmentId;
        this.attachmentMetadataId = imageExtract.attachmentMetadataId;
        this.fileSize = imageExtract.fileSize;
        this.pageNumber = imageExtract.pageNumber;
        this.fileName = imageExtract.fileName;
        this.fileExtention = imageExtract.fileExtention;
        this.filePath = imageExtract.filePath;
        this.note = imageExtract.note;
        this.isSigned = imageExtract.isSigned;
        this.isEncrypt = imageExtract.isEncrypt;
        this.storageType = imageExtract.storageType;
        this.status = imageExtract.status;
        this.isDelete = imageExtract.isDelete;
        this.isEdited = imageExtract.isEdited;
    }
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
