package com.sangnk.core.repository.post;

import com.sangnk.core.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l where l.post.id = :postId and l.user.id = :userId")
    Optional<Like> findLikeByPostIdByUserId(@Param("userId") Long userId, @Param("postId") Long postId);

    Page<Like> findByPostId(Long postId, Pageable pageable);

    @Query("SELECT COUNT(l.id) from Like l where l.post.id = :postId")
    long countByPostId(@Param("postId") Long postId);

    @Query("SELECT l FROM Like l where l.isDelete = :isDelete and l.post.id = :postId and l.user.id = :userId")
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId, Long isDelete);


    @Query("SELECT l FROM Like l where l.post.id = :postId and l.isDelete = :isDelete")
    List<Like> findAllByPostIdAndIsDelete(Long postId, Long isDelete);
}
