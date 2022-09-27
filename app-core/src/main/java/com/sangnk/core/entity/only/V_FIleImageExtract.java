package com.sangnk.core.entity.only;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.entity.Auditable;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from FILE_IMAGE_EXTRACT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class V_FIleImageExtract extends Auditable implements Serializable {
    @Column(name = "VB_ATTACHMENT_ID")
    private Long vbAttachmentId;

    @Column(name = "ATTACHMENT_METADATA_ID")
    private Long attachmentMetadataId;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "PAGE_NUMBER")
    private Long pageNumber;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_EXTENTION")
    private String fileExtention;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "IS_SIGNED")
    private Long isSigned;

    @Column(name = "IS_ENCRYPT")
    private Long isEncrypt;

    @Column(name = "STORAGE_TYPE")
    private String storageType;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "IS_DELETE")
    private Long isDelete;

    @Column(name = "IS_EDITED")
    private Long isEdited;
}
