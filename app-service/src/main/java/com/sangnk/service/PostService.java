package com.sangnk.service;


import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.Comment;
import com.sangnk.core.entity.Like;
import com.sangnk.core.entity.Post;
import com.sangnk.core.entity.view.ViewPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public interface PostService {
     Page<ViewPost> getAllPost(SearchForm searchForm, Pageable pageable);

     public Post createPost(Post post);

     public Comment addComment(Long postId, String commentBody);

     public Post getPostById(Long postId);

     public Post updatePost(Post post);

     public Post deletePost(Post post);

     Like likeOrUnlike(Long postId);

     List<Like> getLikeByPostId(Long postId);

     Like checkLike(Long postId);
}
