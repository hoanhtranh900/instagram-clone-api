package com.sangnk.core.repository;

import com.sangnk.core.entity.FileImageExtract;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileImageExtractRepository extends JpaSpecificationExecutor<FileImageExtract>, org.springframework.data.jpa.repository.JpaRepository<FileImageExtract, Long> {
}
