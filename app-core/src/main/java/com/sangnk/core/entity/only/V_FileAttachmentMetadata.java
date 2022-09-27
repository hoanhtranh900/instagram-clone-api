package com.sangnk.core.entity.only;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.entity.Auditable;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from FILE_ATTACHMENT_METADATA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class V_FileAttachmentMetadata extends Auditable implements Serializable{
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
