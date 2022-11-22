package com.sangnk.service.impl;


import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.*;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.entity.view.ViewPost;
import com.sangnk.core.exception.BadRequestException;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.repository.follow.FollowRepository;
import com.sangnk.core.repository.post.LikeRepository;
import com.sangnk.core.repository.post.PostRepository;
import com.sangnk.core.utils.*;
import com.sangnk.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private FileIOService fileIOService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FollowService followService;


    @Override
    public Page<ViewPost> getAllPost(SearchForm searchObject, Pageable pageable) {
        List<Follow> getListFollowByUser = followRepository.findAllByFollowerId(H.isTrue(searchObject.getId()) ? Long.parseLong(searchObject.getId()) : UtilsCommon.getUserLogin().get().getId());
        //userIdFollowing = follow.getFollowing().getId();
        List<Long> userIdFollowings = getListFollowByUser.stream().map(Follow::getFollowing).map(AdmUser::getId).collect(Collectors.toList());

        userIdFollowings.add(UtilsCommon.getUserLogin().get().getId());
        Page<ViewPost> page = null;
        try {
            List<ViewPost> list = new ArrayList<>();
            String hql = " from ViewPost u  where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);
            if (H.isTrue(searchObject.getId())) {
                builder.and(QueryUtils.EQ, "u.creatorId", Long.parseLong(searchObject.getId()));

            }

            if (StringUtils.isNotBlank(searchObject.getFullName())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.fullName)", "%" + searchObject.getFullName().trim().toUpperCase() + "%");
            }

            if(!H.isTrue(searchObject.getId()) ) builder.and(QueryUtils.IN, "u.creatorId", userIdFollowings);


            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());

            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
//            builder.addOrder("u.createTime", QueryUtils.DESC);

            builder.setSubFix("select u");
            query = builder.initQuery(ViewPost.class);
            if (pageable.getPageSize() > 0) {
                query.setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset()))).setMaxResults(pageable.getPageSize());
            }
            list = query.getResultList();


            for (ViewPost viewPost : list) {
                System.out.println(ConstantString.imageUrlTest);
                FileAttachment fileAttachment = H.isTrue(fileIOService.findByObjectIdAndObjectType(viewPost.getId(), ConstantString.OBJECT_TYPE.POST, null)) ? fileIOService.findByObjectIdAndObjectType(viewPost.getId(), ConstantString.OBJECT_TYPE.POST, null).get(0) : null;
                if (H.isTrue(fileAttachment)) {
                    viewPost.setPostImageUrl(ConstantString.imageUrlTest + fileAttachment.getId());
                }
            }
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

    public Comment addComment(Long postId, String commentBody) {
        Comment comment = commentService.commentPost(postId, commentBody);
        return comment;
    }

    public Post getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BaseException("Post not found with id " + postId));
        return post;
    }

    @Override
    public Post updatePost(Post form) {
        Post bo = getPostById(form.getId());
        bo = bo.formToBo(form, bo);
        utilsService.update(postRepository, bo);
        return bo;
    }

    @Override
    public Post deletePost(Post post) {
        Post bo = getPostById(post.getId());
        utilsService.delete(postRepository, bo);
        return bo;
    }

    @Override
    public Like likeOrUnlike(Long postId) {
        Post post = getPostById(postId);
        Like like = likeRepository.findByPostIdAndUserId(postId, UtilsCommon.getUserLogin().get().getId(), ConstantString.IS_DELETE.active).orElse(null);
        if (!H.isTrue(like)) {
            like = new Like();
            like.setPost(getPostById(postId));
            like.setUser(UtilsCommon.getUserLogin().get());
//            utilsService.save(likeRepository, like);
            likeRepository.save(like);
            post.setTotalLike(post.getTotalLike() + 1);

        } else {
            likeRepository.delete(like);
            if (post.getTotalLike() > 0) {
                post.setTotalLike(post.getTotalLike() - 1);
            } else {
                post.setTotalLike(0L);
            }
            like.setIsDelete(ConstantString.IS_DELETE.delete);
        }
        like.setTotalLike(post.getTotalLike());
        utilsService.update(postRepository, post);
        return like;
    }

    @Override
    public List<Like> getLikeByPostId(Long postId) {
        return likeRepository.findAllByPostIdAndIsDelete(postId, ConstantString.IS_DELETE.active);
    }

    @Override
    public Like checkLike(Long postId) {
        return likeRepository.findByPostIdAndUserId(postId, UtilsCommon.getUserLogin().get().getId(), ConstantString.IS_DELETE.active).orElse(null);
    }

    @Override
    public Page<ViewPost> getMyPost(SearchForm searchObject, Pageable pageable) {
        List<Follow> getListFollowByUser = followRepository.findAllByFollowerId(UtilsCommon.getUserLogin().get().getId());
        //userIdFollowing = follow.getFollowing().getId();
        List<Long> userIdFollowings = getListFollowByUser.stream().map(Follow::getFollowing).map(AdmUser::getId).collect(Collectors.toList());

        userIdFollowings.add(UtilsCommon.getUserLogin().get().getId());
        Page<ViewPost> page = null;
        try {
            List<ViewPost> list = new ArrayList<>();
            String hql = " from ViewPost u  where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);


            if (StringUtils.isNotBlank(searchObject.getFullName())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.fullName)", "%" + searchObject.getFullName().trim().toUpperCase() + "%");
            }
            if (H.isTrue(searchObject.getId())) {
                builder.and(QueryUtils.EQ, "u.creatorId", searchObject.getId());
            }

            if (H.isTrue(getListFollowByUser)) {
                builder.and(QueryUtils.IN, "u.creatorId", userIdFollowings);
            }


            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());

            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
//            builder.addOrder("u.createTime", QueryUtils.DESC);

            builder.setSubFix("select u");
            query = builder.initQuery(ViewPost.class);
            if (pageable.getPageSize() > 0) {
                query.setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset()))).setMaxResults(pageable.getPageSize());
            }
            list = query.getResultList();


            for (ViewPost viewPost : list) {
                System.out.println(ConstantString.imageUrlTest);
                FileAttachment fileAttachment = H.isTrue(fileIOService.findByObjectIdAndObjectType(viewPost.getId(), ConstantString.OBJECT_TYPE.POST, null)) ? fileIOService.findByObjectIdAndObjectType(viewPost.getId(), ConstantString.OBJECT_TYPE.POST, null).get(0) : null;
                if (H.isTrue(fileAttachment)) {
                    viewPost.setPostImageUrl(ConstantString.imageUrlTest + fileAttachment.getId());
                }
            }
            if (list != null) {
                page = new PageImpl<>(list, pageable, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public Post detail(Long id) {
        Post post = getPostById(id);

        FileAttachment fileAttachment = H.isTrue(fileIOService.findByObjectIdAndObjectType(post.getId(), ConstantString.OBJECT_TYPE.POST, null)) ? fileIOService.findByObjectIdAndObjectType(post.getId(), ConstantString.OBJECT_TYPE.POST, null).get(0) : null;
        if (H.isTrue(fileAttachment)) {
            post.setPostImageUrl(ConstantString.imageUrlTest + fileAttachment.getId());
        }
        return post;
    }
}
