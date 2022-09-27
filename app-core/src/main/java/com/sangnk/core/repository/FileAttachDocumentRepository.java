package com.sangnk.core.repository;

import com.sangnk.core.entity.FileAttachDocument;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachDocumentRepository extends JpaSpecificationExecutor<FileAttachDocument>, org.springframework.data.jpa.repository.JpaRepository<FileAttachDocument, Long> {

}
