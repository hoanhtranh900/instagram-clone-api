package com.sangnk.service.impl;

import com.sangnk.core.entity.FileImageExtract;
import com.sangnk.core.repository.FileImageExtractRepository;
import com.sangnk.core.service.BaseServiceImpl;
import com.sangnk.service.FileImageExtractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FileImageExtractServiceServiceImpl extends BaseServiceImpl<FileImageExtract, FileImageExtractRepository> implements FileImageExtractService<FileImageExtract> {
    public FileImageExtractServiceServiceImpl(FileImageExtractRepository repository) {
        super(repository);
    }

}
