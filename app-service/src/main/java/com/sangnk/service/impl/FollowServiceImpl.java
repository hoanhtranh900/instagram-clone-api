package com.sangnk.service.impl;


import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Follow;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.entity.view.ViewFollow;
import com.sangnk.core.entity.view.ViewPost;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.repository.follow.FollowRepository;
import com.sangnk.core.repository.follow.FollowRequestRepository;
import com.sangnk.core.service.BaseService;
import com.sangnk.core.service.BaseServiceImpl;
import com.sangnk.core.utils.*;
import com.sangnk.service.AdmUserService;
import com.sangnk.service.FollowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl extends BaseServiceImpl<Follow, FollowRepository> implements FollowService<Follow> {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FollowRequestRepository followRequestRepository;
    @Autowired
    private AdmUserService<AdmUser> admUserService;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private EntityManager entityManager;

    public FollowServiceImpl(FollowRepository repository) {
        super(repository);
    }

    @Override
    public Follow followUser(Long userId) {
        AdmUser cuUser = UtilsCommon.getUserLogin().get();
        validateFollow(userId, cuUser);
        Follow follow = new Follow();
        follow.setFollower(cuUser);
        AdmUser user = admUserService.get(userId).orElseThrow(() -> new BaseException("User not found"));
        follow.setFollowing(user);
        utilsService.save(followRepository, follow);
        return follow;
    }

    private void validateFollow(Long following, AdmUser cuUser) {
        if (cuUser.getId().equals(following)) {
            throw new BaseException("You can not follow yourself");
        }
    }

    @Override
    public Follow isFollowing(Long userIdAdmUser) {
        return null;
    }

    @Override
    public Page<ViewFollow> getUserFollowers(SearchForm searchForm, Pageable pageable) {
        Page<ViewFollow> page = null;
        try {
            List<ViewFollow> list = new ArrayList<>();
            String hql = " from ViewFollow u left join u.following fli where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);

            builder.and(QueryUtils.EQ, "u.isDelete", ConstantString.IS_DELETE.active);
            builder.and(QueryUtils.EQ, "fli.id", Long.parseLong(searchForm.getId()));


            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());

            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
            builder.addOrder("u.createTime", QueryUtils.DESC);

            builder.setSubFix("select u");
            query = builder.initQuery(ViewFollow.class);
            if (pageable.getPageSize() > 0) {
                query.setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset()))).setMaxResults(pageable.getPageSize());
            }
            list = query.getResultList();

            if (list != null) {
                page = new PageImpl<>(list, pageable, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public Follow isUserFollowedByCurrentUser(Long userId) {
        return null;
    }

    @Override
    public Page<ViewFollow> getUserFollowing(SearchForm searchForm, Pageable pageable) {
        Page<ViewFollow> page = null;
        try {
            List<ViewFollow> list = new ArrayList<>();
            String hql = " from ViewFollow u left join u.follower fle where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);

            builder.and(QueryUtils.EQ, "u.isDelete", ConstantString.IS_DELETE.active);
            builder.and(QueryUtils.EQ, "fle.id", Long.parseLong(searchForm.getId()));


            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());

            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
            builder.addOrder("u.createTime", QueryUtils.DESC);

            builder.setSubFix("select u");
            query = builder.initQuery(ViewFollow.class);
            if (pageable.getPageSize() > 0) {
                query.setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset()))).setMaxResults(pageable.getPageSize());
            }
            list = query.getResultList();

            if (list != null) {
                page = new PageImpl<>(list, pageable, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public Follow acceptFollow(Long followRequestId) {
        return null;
    }

    @Override
    public Follow declineFollow(Long followRequestId) {
        return null;
    }



}
