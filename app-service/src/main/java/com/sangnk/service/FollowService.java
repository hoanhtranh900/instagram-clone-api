package com.sangnk.service;


import com.sangnk.core.entity.Follow;
import com.sangnk.core.entity.view.ViewFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService {
    Follow followUser(Long userIdAdmUser);
    Follow isFollowing(Long userIdAdmUser);
    Page<ViewFollow> getUserFollowers(Long userId, Pageable pageable);
    Follow isUserFollowedByCurrentUser(Long userId);
    Page<ViewFollow> getUserFollowing(Long userId, Pageable pageable);
    Follow acceptFollow(Long followRequestId);
    Follow declineFollow(Long followRequestId);
}
