package com.sangnk.core.contants;

import com.sangnk.core.entity.FileAttachment;
import com.sangnk.core.repository.FileAttachmentRepository;
import com.sangnk.core.utils.H;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CategoryBuffer {
    private static FileAttachmentRepository fileAttachmentRepository;

    private static ModelMapper modelMapper;

    public static HashMap<Long, String> userAvata = new HashMap<>();



    public CategoryBuffer(final ModelMapper modelMapper, final FileAttachmentRepository fileAttachmentRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.modelMapper = modelMapper;
    }


    public static String getUserAvataById(Long id) {
        if(id == null) return null;
        String avata = userAvata.get(id);
        if (avata == null) {
            List<FileAttachment> fileAttachment = fileAttachmentRepository.findByObjectIdAndObjectTypeAndIsDelete(id, ConstantString.OBJECT_TYPE.AVATAR, null, ConstantString.IS_DELETE.active);
            if (H.isTrue(fileAttachment)) {
                avata = ConstantString.imageUrlTest + fileAttachment.get(0).getFileServiceId();
            }
        }
        return avata;
    }
}
