package com.sangnk.core.dto.request;

import com.sangnk.core.entity.FileAttachmentMetadata;
import com.sangnk.core.entity.FileImageExtract;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class ExtractFileResult {
    private List<FileImageExtract> imageExtractList;
    private FileAttachmentMetadata attachmentMetadata;

}
