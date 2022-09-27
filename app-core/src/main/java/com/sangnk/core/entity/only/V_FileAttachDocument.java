package com.sangnk.core.entity.only;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import com.sangnk.core.entity.Auditable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from FILE_ATTACH_DOCUMENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class V_FileAttachDocument extends Auditable implements Serializable {

    @Comment("Tên file")
    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_CODE")
    private String fileCode;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_SIZE")
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

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "IS_DELETE")
    private Long isDelete;

    @Transient
    private String isDeleteStr;

    public String getIsDeleteStr() {
        return this.isDeleteStr;
    }
}
