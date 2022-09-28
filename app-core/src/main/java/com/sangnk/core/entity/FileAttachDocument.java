package com.sangnk.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FILE_ATTACH_DOCUMENT", indexes = {
        @Index(name = "FILE_NAME", columnList = "FILE_NAME", unique = false),
        @Index(name = "FILE_CODE", columnList = "FILE_CODE", unique = false),
})
@org.hibernate.annotations.Table(appliesTo = "FILE_ATTACH_DOCUMENT", comment = "Table lưu file")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileAttachDocument implements Serializable, Creatable, Updatable, Deletable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("Tên file")
    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_CODE", length = 150)
    private String fileCode;

    @Column(name = "FILE_TYPE", length = 50)
    private String fileType;

    @Column(name = "FILE_SIZE", length = 1000)
    private Long fileSize;

    @Column(name = "IS_ENCRYPT")
    private Long isEncrypt;

    @Column(name = "IS_PDF")
    private Long isPdf;

    @Column(name = "PDF_ATTACH_DOCUMENT_ID")
    private String pdfAttachmentDocumentId;

    @Column(name = "IS_ORIGINAL")
    private Long isOriginal;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FILE_PATH", length = 500)
    private String filePath;

    @Column(name = "CONTENT_TYPE", length = 100)
    private String contentType;

    @Column(name = "IS_DELETE", columnDefinition = "bigint default 0")
    private Long isDelete = ConstantString.IS_DELETE.active;

    @Transient
    private String isDeleteStr;

    public String getIsDeleteStr() {
        return ConstantString.IS_DELETE.getStatusStr(this.isDelete);
    }


    @Comment("ID người tạo")
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
