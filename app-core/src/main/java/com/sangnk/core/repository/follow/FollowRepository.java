package com.sangnk.core.repository.follow;

import com.sangnk.core.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, JpaSpecificationExecutor<Follow> {
    Optional<Follow> findFollowByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<Follow> findAllByFollowingId(Long followingId);

    List<Follow> findAllByFollowerId(Long followerId);

    int countByFollowerId(Long followerId);

    int countByFollowingId(Long followingId);

    @Query(value = "SELECT fl FROM Follow fl WHERE fl.follower.id = :follower AND fl.following.id = :following")
    Optional<Follow> findByFollowerAndFollowing(Long follower, Long following);
}
