package com.sangnk.service.impl;

import com.sangnk.core.entity.FileAttachment;
import com.sangnk.core.repository.FileAttachmentRepository;
import com.sangnk.core.service.BaseServiceImpl;
import com.sangnk.service.FileAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FileAttachmentServiceImpl extends BaseServiceImpl<FileAttachment, FileAttachmentRepository> implements FileAttachmentService<FileAttachment> {
    public FileAttachmentServiceImpl(FileAttachmentRepository repository) {
        super(repository);
    }

}
