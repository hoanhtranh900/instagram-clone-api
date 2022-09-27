package com.sangnk.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FILE_ATTACHMENT_METADATA", indexes = {
        @Index(name = "OBJECT_TYPE", columnList = "OBJECT_TYPE", unique = false),
})
@org.hibernate.annotations.Table(appliesTo = "FILE_ATTACHMENT_METADATA", comment = "Table lưu ảnh nhỏ dc resize")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileAttachmentMetadata extends Auditable implements Serializable {

    @Column(name = "VB_ATTACHMENT_ID")
    private Long vbAttachmentId;
    
    @Basic
    @Column(name = "OBJECT_TYPE")
    private String objectType;
    
    @Column(name = "SIZE_TOTAL")
    private Long sizeTotal;
    
    @Column(name = "PAGE_SIZE")
    private Long pageSize;
    
    @Column(name = "STATUS")
    private String status;

    @Column(name = "IS_DELETE")
    private Long isDelete;
    
}
