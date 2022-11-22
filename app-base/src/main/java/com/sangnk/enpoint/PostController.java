package com.sangnk.enpoint;

import com.sangnk.core.contants.Constants;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.dto.response.ResponseData;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Post;
import com.sangnk.core.entity.view.ViewFollow;
import com.sangnk.core.entity.view.ViewPost;
import com.sangnk.core.exception.Result;
import com.sangnk.core.utils.JsonHelper;
import com.sangnk.service.CommentService;
import com.sangnk.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/post", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @ApiOperation(response = ViewPost.class, notes = Constants.NOTE_API + "empty_note", value = "Danh sách bai dang", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping(value = "/getPage")
    public ResponseEntity<ResponseData> getPage(Pageable pageable, @ApiParam(value = Constants.NOTE_API_PAGEABLE) @RequestParam @Valid String search) {
        SearchForm searchObject = JsonHelper.jsonToObject(search, SearchForm.class);
        Page<ViewPost> pages = postService.getAllPost(searchObject, pageable);
        return new ResponseEntity<>(new ResponseData<>(pages, Result.SUCCESS), HttpStatus.OK);
    }

    //detail post
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseData> getPost(@PathVariable Long id) {
        Post post = postService.detail(id);
        return new ResponseEntity<>(new ResponseData<>(post, Result.SUCCESS), HttpStatus.OK);
    }

    //add post
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseData> create(@RequestBody @Valid Post post) {
        return new ResponseEntity<>(new ResponseData<>(postService.createPost(post), Result.SUCCESS), HttpStatus.OK);
    }
    //update post
    @PostMapping(value = "/update")
    public ResponseEntity<ResponseData> update(@RequestBody @Valid Post post) {
        return new ResponseEntity<>(new ResponseData<>(postService.updatePost(post), Result.SUCCESS), HttpStatus.OK);
    }
    //delete post
    @PostMapping(value = "/delete")
    public ResponseEntity<ResponseData> delete(@RequestBody @Valid Post post) {
        return new ResponseEntity<>(new ResponseData<>(postService.deletePost(post), Result.SUCCESS), HttpStatus.OK);
    }

    //add commnet
    @PostMapping(value = "/addComment/{id}")
    public ResponseEntity<ResponseData> addComment(@PathVariable("id") Long postId, @RequestBody @Valid String commentBody) {
        return new ResponseEntity<>(new ResponseData<>(commentService.commentPost(postId, commentBody), Result.SUCCESS), HttpStatus.OK);
    }

    //getComment of Post
    @GetMapping(value = "/getComment/{id}")
    public ResponseEntity<ResponseData> getComment(Pageable pageable, @PathVariable("id") Long postId) {
        return new ResponseEntity<>(new ResponseData<>(commentService.getCommentsByPostId(postId, pageable), Result.SUCCESS), HttpStatus.OK);
    }


    //like or unlike post
    @PostMapping(value = "/like/{id}")
    public ResponseEntity<ResponseData> likeOrUnlike(@PathVariable("id") Long postId) {
        return new ResponseEntity<>(new ResponseData<>(postService.likeOrUnlike(postId), Result.SUCCESS), HttpStatus.OK);
    }

    //check like
    @GetMapping(value = "/like/{postid}")
public ResponseEntity<ResponseData> checkLike(@PathVariable("postid") Long postId) {
        return new ResponseEntity<>(new ResponseData<>(postService.checkLike(postId), Result.SUCCESS), HttpStatus.OK);
    }


    //get all like of post
    @GetMapping(value = "/like/getPage/{id}")
    public ResponseEntity<ResponseData> getLike(@PathVariable("id") Long postId) {
        return new ResponseEntity<>(new ResponseData<>(postService.getLikeByPostId(postId), Result.SUCCESS), HttpStatus.OK);
    }

    //get my post
    @GetMapping(value = "/getMyPost")
    public ResponseEntity<ResponseData> getMyPost(Pageable pageable, @ApiParam(value = Constants.NOTE_API_PAGEABLE) @RequestParam @Valid String search) {
        SearchForm searchObject = JsonHelper.jsonToObject(search, SearchForm.class);
        Page<ViewPost> pages = postService.getMyPost(searchObject, pageable);
        return new ResponseEntity<>(new ResponseData<>(pages, Result.SUCCESS), HttpStatus.OK);
    }
}
