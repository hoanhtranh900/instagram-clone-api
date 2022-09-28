package com.sangnk.service;


import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.Follow;
import com.sangnk.core.entity.view.ViewFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FollowService<E> {
    Optional<E> save(E entity);
    Optional<E> update(E entity);
    Optional<E> get(Long id);
    Follow followUser(Long userIdAdmUser);
    Follow isFollowing(Long userIdAdmUser);

    //Lấy danh sách người theo dõi mình
    Page<ViewFollow> getUserFollowers(SearchForm searchForm, Pageable pageable);

    Follow isUserFollowedByCurrentUser(Long userId);
    Page<ViewFollow> getUserFollowing(SearchForm searchForm, Pageable pageable);
    Follow acceptFollow(Long followRequestId);
    Follow declineFollow(Long followRequestId);
}
