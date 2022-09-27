package com.sangnk.service;

import com.sangnk.core.dto.request.DownloadOption;
import com.sangnk.core.dto.request.ExtractFileOption;
import com.sangnk.core.dto.request.ExtractFileResult;
import com.sangnk.core.dto.request.UploadOption;
import com.sangnk.core.dto.response.GetNumberPagesOfPdfResponse;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.FileAttachDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * TODO: write you class description here
 *
 * @author
 */

public interface FileAttachmentService<E> {

    Optional<E> save(E entity);
    Optional<E> update(E entity);
    Optional<E> get(Long id);
    Page<E> getPaging(Pageable pageable);
    List<E> getAll();
    Boolean deleteById(Long id);
    Boolean deleteAll();

}
