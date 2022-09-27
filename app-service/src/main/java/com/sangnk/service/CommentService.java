package com.sangnk.service;


import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.post.CommentRequest;
import com.sangnk.core.dto.post.response.PagedResponse;
import com.sangnk.core.dto.post.response.comment.CommentResponse;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Comment;
import com.sangnk.core.entity.Post;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.entity.view.ViewComment;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.exception.ResourceNotFoundException;
import com.sangnk.core.repository.AdmUserRepository;
import com.sangnk.core.repository.post.CommentRepository;
import com.sangnk.core.repository.post.PostRepository;
import com.sangnk.core.utils.H;
import com.sangnk.core.utils.QueryBuilder;
import com.sangnk.core.utils.QueryUtils;
import com.sangnk.core.utils.UtilsCommon;
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
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Comment commentPost(Long postId, CommentRequest commentRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException("Post not found with id " + postId));

        Optional<AdmUser> user = UtilsCommon.getUserLogin();


        Comment comment = new Comment();
        comment.setBody(commentRequest.getBody());
        comment.setPost(post);
        comment.setUser(user.get());


        return commentRepository.save(comment);
    }

    public Page<ViewComment> getCommentsByPostId(Long postId, Pageable pageable) {
        Page<ViewComment> page = null;
        try {
            List<ViewComment> list = new ArrayList<>();
            String hql = " from ViewComment u left join u.post pt where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);


            builder.and(QueryUtils.EQ, "u.status", postId);


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

    public Comment getCommentByPostIdAndCommentId(Long postId, Long commentId) {
        return commentRepository.findCommentByPostIdAndCommentId(postId, commentId).orElseThrow(
                () -> new BaseException("Comment not found with id " + commentId + " and postId " + postId));

    }


}
