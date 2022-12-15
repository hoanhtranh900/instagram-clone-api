package com.sangnk.service.impl;


import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.Notification;
import com.sangnk.core.exception.BadRequestException;
import com.sangnk.core.repository.NotificationRepository;
import com.sangnk.core.utils.QueryBuilder;
import com.sangnk.core.utils.QueryUtils;
import com.sangnk.core.utils.UtilsCommon;
import com.sangnk.service.NotificationService;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
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
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService<Notification> {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EntityManager entityManager;


    @Override
    public Optional<Notification> save(Notification entity) {
        if(entity.getFromUserId() != entity.getToUserId()){
            return Optional.ofNullable(notificationRepository.save(entity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Notification> readNotification(Long id) throws Throwable {
        Notification notification = notificationRepository.getById(id);
        if (notification == null) {
            throw new BadRequestException(messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"Notification"}, UtilsCommon.getLocale()));
        }
        notification.setIsRead(1L);
        return Optional.ofNullable(notificationRepository.save(notification));
    }

    @Override
    public Page<Notification> getPage(SearchForm search, Pageable pageable) {
        Page<Notification> page = null;
        try {
            List<Notification> list = new ArrayList<>();
            String hql = " from Notification u  where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count( distinct u)", new StringBuffer(hql), false);

            builder.and(QueryUtils.EQ, "u.toUserId", UtilsCommon.getUserLogin().get().getId());

            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());

            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
//            builder.addOrder("u.createdDate", QueryUtils.DESC);

            builder.setSubFix("select distinct u");
            query = builder.initQuery(Notification.class);
            if (pageable.getPageSize() > 0) {
                query.setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset()))).setMaxResults(pageable.getPageSize());
            }
            list = query.getResultList();

            page = new PageImpl<>(list, pageable, count);


        } catch (Exception e) {
            e.printStackTrace();
//            telegramBot.sendBotMessage("Lỗi tại CmUnitServiceImpl.getPage " + e.getMessage());
        }
        return page;
    }

    @Override
    public Object countNotifiUnread() {
        return null;
    }
}
