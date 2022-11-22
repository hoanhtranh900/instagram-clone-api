package com.sangnk.enpoint;


import com.sangnk.core.contants.Constants;
import com.sangnk.core.controller.BaseControllerImpl;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.dto.response.ResponseData;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Follow;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.entity.view.ViewFollow;
import com.sangnk.core.exception.Result;
import com.sangnk.core.utils.JsonHelper;
import com.sangnk.service.FollowService;
import com.sangnk.service.impl.AdmUserServiceImpl;
import com.sangnk.service.impl.FollowServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/follow", produces = MediaType.APPLICATION_JSON_VALUE)
public class FollowResource extends BaseControllerImpl<Follow, FollowServiceImpl> {
    @Autowired
    private FollowService followService;

    protected FollowResource(FollowServiceImpl service, MessageSource messageSource) {
        super(service, messageSource);
    }

    @ApiOperation(value = "", response = Follow.class, authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping(value = "/getPageFollower")
    public ResponseEntity<ResponseData> getPageFollower(Pageable pageable, @ApiParam(value = Constants.NOTE_API_PAGEABLE) @RequestParam @Valid String search) {
        SearchForm searchObject = JsonHelper.jsonToObject(search, SearchForm.class);
        Page<ViewFollow> pages = followService.getUserFollowers(searchObject, pageable);
        return new ResponseEntity<>(new ResponseData<>(pages, Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(value = "", response = Follow.class, authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping(value = "/getPageFollowing")
    public ResponseEntity<ResponseData> getPageFollowing(Pageable pageable, @ApiParam(value = Constants.NOTE_API_PAGEABLE) @RequestParam @Valid String search) {
        SearchForm searchObject = JsonHelper.jsonToObject(search, SearchForm.class);
        Page<ViewFollow> pages = followService.getUserFollowing(searchObject, pageable);
        return new ResponseEntity<>(new ResponseData<>(pages, Result.SUCCESS), HttpStatus.OK);
    }

    @PostMapping(value = "/followUser/{id}")
    public ResponseEntity<ResponseData> followUser(@PathVariable("id") Long id) {
        Boolean follow = followService.followUser(id);
        return new ResponseEntity<>(new ResponseData<>(follow, Result.SUCCESS), HttpStatus.OK);
    }

    //check follow
    @GetMapping(value = "/checkFollow/{userId}")
    public ResponseEntity<ResponseData> checkFollow(@PathVariable("userId") Long userId) {
        Boolean isFollow = followService.checkFollow(userId);
        return new ResponseEntity<>(new ResponseData<>(isFollow, Result.SUCCESS), HttpStatus.OK);
    }
}
