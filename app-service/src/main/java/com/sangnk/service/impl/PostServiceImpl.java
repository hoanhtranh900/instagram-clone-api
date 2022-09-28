package com.sangnk.service.impl;


import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Comment;
import com.sangnk.core.entity.Post;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.entity.view.ViewPost;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.repository.follow.FollowRepository;
import com.sangnk.core.repository.post.LikeRepository;
import com.sangnk.core.repository.post.PostRepository;
import com.sangnk.core.utils.*;
import com.sangnk.service.AdmUserService;
import com.sangnk.service.CommentService;
import com.sangnk.service.PostService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AdmUserService<AdmUser> admUserService;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UtilsService utilsService;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Page<ViewPost> getAllPost(SearchForm searchObject, Pageable pageable) {
        Page<ViewPost> page = null;
        try {
            List<ViewPost> list = new ArrayList<>();
            String hql = " from ViewPost u left join u.unit unt where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);


            if (StringUtils.isNotBlank(searchObject.getFullName())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.username)", "%" + searchObject.getUsername().trim().toUpperCase() + "%");
            }
            if (StringUtils.isNotBlank(searchObject.getPhoneNumber())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.phoneNumber)", "%" + searchObject.getPhoneNumber().trim().toUpperCase() + "%");
            }
            if (StringUtils.isNotBlank(searchObject.getStatus())) {
                builder.and(QueryUtils.EQ, "u.status", Long.parseLong(searchObject.getStatus().trim()));
            }
            if (StringUtils.isNotBlank(searchObject.getEmail())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.email)", "%" + searchObject.getEmail().trim().toUpperCase() + "%");
            }
            if (StringUtils.isNotBlank(searchObject.getFullName())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.fullName)", "%" + searchObject.getFullName().trim().toUpperCase() + "%");
            }

            if (StringUtils.isNotBlank(searchObject.getPosition())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.position)", "%" + searchObject.getPosition().trim().toUpperCase() + "%");
            }

            if (H.isTrue(searchObject.getUnitId())) {
                builder.and(QueryUtils.EQ, "UPPER(unt.id)", Long.parseLong(searchObject.getUnitId().trim()));
            }


            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());

            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
            builder.addOrder("u.createdDate", QueryUtils.DESC);

            builder.setSubFix("select u");
            query = builder.initQuery(ViewAdmUser.class);
            if(pageable.getPageSize() > 0){
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

    public Post createPost(Post post) {
        utilsService.save(postRepository, post);
        return post;
    }

    public Comment addComment(Long postId, String commentBody){
        Comment comment = commentService.commentPost(postId, commentBody);
        return comment;
    }

    public Post getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BaseException("Post not found with id " + postId));
        return post;
    }
}
