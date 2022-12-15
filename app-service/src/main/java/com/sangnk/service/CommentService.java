package com.sangnk.service;


import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.*;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.entity.view.ViewComment;
import com.sangnk.core.entity.view.ViewFollow;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.exception.ResourceNotFoundException;
import com.sangnk.core.repository.AdmUserRepository;
import com.sangnk.core.repository.post.CommentRepository;
import com.sangnk.core.repository.post.PostRepository;
import com.sangnk.core.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AdmUserRepository userRepository;

    @Autowired
    private PostService postService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FollowService followService;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private NotificationService notificationService;

    public Comment commentPost(Long postId, String commentBody) {
        commentBody = commentBody.substring(1, commentBody.length() - 1);
        Post post = postService.getPostById(postId);
//        if (!validateComment(post)) throw new BaseException("Comment is not allowed");

        Optional<AdmUser> user = UtilsCommon.getUserLogin();


        Comment comment = new Comment();
        comment.setBody(commentBody);
        comment.setPost(post);
        comment.setUser(user.get());


        utilsService.save(commentRepository, comment);

        String content = "Ông/bà " + user.get().getFullName() + " đã bình luận bài viết của bạn";
        notificationService.save(Notification.builder().content(content).fromUserId(user.get().getId()).toUserId(post.getCreatorId()).createTime(new Date()).build());
        return comment;
    }



    public Page<ViewComment> getCommentsByPostId(Long id, Pageable pageable) {
        Page<ViewComment> page = null;
        try {
            List<ViewComment> list = new ArrayList<>();
            String hql = " from ViewComment u left join u.post pt where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);


            builder.and(QueryUtils.EQ, "pt.id", id);


            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());

            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
            builder.addOrder("u.createTime", QueryUtils.DESC);

            builder.setSubFix("select u");
            query = builder.initQuery(ViewComment.class);
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

    public Comment getCommentByPostIdAndCommentId(Long postId, Long commentId) {
        return commentRepository.findCommentByPostIdAndCommentId(postId, commentId).orElseThrow(
                () -> new BaseException("Comment not found with id " + commentId + " and postId " + postId));

    }


}
