package com.sangnk.service;


import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.Notification;
import com.sangnk.core.exception.BadRequestException;
import com.sangnk.core.repository.NotificationRepository;
import com.sangnk.core.utils.QueryBuilder;
import com.sangnk.core.utils.QueryUtils;
import com.sangnk.core.utils.UtilsCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface NotificationService<E> {
    Optional<E> save(E entity);

    Optional<Notification> readNotification(Long id) throws Throwable;

    Page<Notification> getPage(SearchForm searchObject, Pageable pageable);

    Object countNotifiUnread();
}