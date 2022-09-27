package com.sangnk.core.repository;

import com.sangnk.core.entity.FileAttachmentMetadata;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentMetadataRepository extends JpaSpecificationExecutor<FileAttachmentMetadata>, org.springframework.data.jpa.repository.JpaRepository<FileAttachmentMetadata, Long> {

}
