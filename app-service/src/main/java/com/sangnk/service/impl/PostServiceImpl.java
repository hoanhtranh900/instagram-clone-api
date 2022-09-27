package com.sangnk.service.impl;


import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.entity.view.ViewPost;
import com.sangnk.core.repository.follow.FollowRepository;
import com.sangnk.core.repository.post.LikeRepository;
import com.sangnk.core.repository.post.PostRepository;
import com.sangnk.core.utils.H;
import com.sangnk.core.utils.QueryBuilder;
import com.sangnk.core.utils.QueryUtils;
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

    @PersistenceContext
    private EntityManager entityManager;



    public Page<ViewPost> getAllPost(SearchForm searchObject, Pageable pageable) {
        Page<ViewAdmUser> page = null;
        try {
            List<ViewAdmUser> list = new ArrayList<>();
            String hql = " from ViewPost u left join u.unit unt where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);


            if (StringUtils.isNotBlank(searchObject.getUsername())) {
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

    public ResponseEntity<?> createPost(PostRequest postRequest, MultipartFile image) {
        String fileName = fileStorageService.storeFile(image);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/posts/images/")
                .path(fileName)
                .toUriString();

        Post post = new Post();
        post.setDescription(postRequest.getDescription());
        post.setImagePath(fileDownloadUri);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postId}")
                .buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Post created successfully."));
    }

    public CommentResponse addComment(UserPrincipal currentUser, Long postId, CommentRequest commentRequest){
        Comment comment = commentService.commentPost(postId, commentRequest, currentUser);

        User user = userRepository.getOne(currentUser.getId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{commentId}")
                .buildAndExpand(comment.getId()).toUri();

        return ModelMapper.mapCommentToCommentResponse(comment, user);
    }

    public PostResponse getPostById(Long postId, UserPrincipal currentUser) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve post creator details
        User creator = userRepository.findById(post.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", post.getCreatedBy()));

        return ModelMapper.mapPostToPostResponse(post, creator);
    }

    public ResponseEntity<Resource> getPostImage(String fileName, HttpServletRequest request){
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public PagedResponse<PostResponse> getPostByUserId(UserPrincipal currentUser, int page, int size, Long userId) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Post> posts = postRepository.findByCreatedBy(userId, pageable);

        if (posts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), posts.getNumber(),
                    posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
        }

        Map<Long, User> creatorMap = getPostCreatorMap(posts.getContent());

        List<PostResponse> postResponses = posts.map(post -> {
            return ModelMapper.mapPostToPostResponse(post,
                    creatorMap.get(post.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(postResponses, posts.getNumber(),
                posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
    }

    public PagedResponse<LikeResponse> getLikesByPostId(UserPrincipal currentUser, int page, int size, Long postId) {
        validatePageNumberAndSize(page, size);

        // Retrieve Posts
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Like> likes = likeRepository.findByPostId(postId, pageable);

        //Get followingList
        List<Follow> followList = followRepository.findAllByFollowerId(currentUser.getId());

        if (likes.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), likes.getNumber(),
                    likes.getSize(), likes.getTotalElements(), likes.getTotalPages(), likes.isLast());
        }

        Map<Long, User> creatorMap = getLikeCreatorMap(likes.getContent());

        List<LikeResponse> postResponses = likes.map(like -> {
            return ModelMapper.mapLikeToLikeResponse(like,
                    creatorMap.get(like.getCreatedBy()), followList);
        }).getContent();

        return new PagedResponse<>(postResponses, likes.getNumber(),
                likes.getSize(), likes.getTotalElements(), likes.getTotalPages(), likes.isLast());
    }

    public LikedReponse addPostLike(Long postId, UserPrincipal currentUser) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.getOne(currentUser.getId());

        Optional<Like> like = likeRepository.findLikeByPostIdByUserId(user.getId(), postId);

        if (like.isPresent()) {
            likeRepository.deleteById(like.get().getId());

            Optional<PostNotification> postNotification = postNotificationRepository.findByNotificationCreatorIdAndNotificatorReceiverId(currentUser.getId(), post.getCreatedBy());
            if (postNotification.isPresent())
                postNotificationRepository.delete(postNotification.get());

            return new LikedReponse(false);
        }

        Like likeObject = new Like();
        likeObject.setUser(user);
        likeObject.setPost(post);
        likeRepository.save(likeObject);

        PostNotification postNotification = new PostNotification();
        postNotification.setPost(post);
        postNotification.setNotificationType(PostNotificationType.POST_LIKED);
        postNotification.setNotificationCreator(user);
        postNotification.setNotificationReceiver(userRepository.getOne(post.getCreatedBy()));
        postNotificationRepository.save(postNotification);

        return new LikedReponse(true);
    }

    public SavedPostResponse savePostForUser(Long postId, UserPrincipal currentUser) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.getOne(currentUser.getId());

        Optional<SavedPost> savedPost = savedPostRepository.findSavedPostByPostIdByUserId(user.getId(), postId);

        if (savedPost.isPresent()) {
            savedPostRepository.deleteById(savedPost.get().getId());
            return new SavedPostResponse(false);
        }

        SavedPost savedPostObject = new SavedPost();
        savedPostObject.setUser(user);
        savedPostObject.setPost(post);
        savedPostRepository.save(savedPostObject);
        return new SavedPostResponse(true);
    }

    public LikedReponse checkIfPostLiked(Long postId, UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        Optional<Like> like = likeRepository.findLikeByPostIdByUserId(user.getId(), postId);
        if (like.isPresent()) {
            return new LikedReponse(true);
        }
        return new LikedReponse(false);
    }

    public SavedPostResponse checkIfPostSaved(Long postId, UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        Optional<SavedPost> savedPost = savedPostRepository.findSavedPostByPostIdByUserId(user.getId(), postId);
        if (savedPost.isPresent()) {
            return new SavedPostResponse(true);
        }
        return new SavedPostResponse(false);
    }

    public LikeCountResponse getLikesCountByPostId(Long postId) {
        Long count = likeRepository.countByPostId(postId);
        return new LikeCountResponse(count);
    }

    public PhotoModalResponse getPhotoModalInfo(Long postId, UserPrincipal currentUser) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Poll", "id", postId));

        // Retrieve post creator details
        User creator = userRepository.findById(post.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", post.getCreatedBy()));

        long likeCount = likeRepository.countByPostId(postId);
        LikedReponse isLiked = checkIfPostLiked(post.getId(), currentUser);
        SavedPostResponse isSaved = checkIfPostSaved(post.getId(), currentUser);

        return ModelMapper.mapDataToPhotoModalResponse(post, creator, likeCount, isLiked, isSaved);
    }

    public Map<Long, User> getPostCreatorMap(List<Post> posts) {
        // Get Poll Creator details of the given list of polls
        List<Long> creatorIds = posts.stream()
                .map(Post::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }

    public Map<Long, User> getLikeCreatorMap(List<Like> likes) {
        // Get Poll Creator details of the given list of polls
        List<Long> creatorIds = likes.stream()
                .map(Like::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }

    public void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
