package com.sangnk.core.repository;

import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.view.ViewAdmUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmUserRepository extends JpaSpecificationExecutor<AdmUser>, org.springframework.data.jpa.repository.JpaRepository<AdmUser, Long> {

    Optional<AdmUser> findByUsername(String username);

    @Query(value="select au from #{#entityName} au where au.id in (:ids)")
    List<AdmUser> loadByListIds(@Param("ids") List<Long> ids);

    Optional<AdmUser> findAdmUserByEmail(String email);

//    @Query(nativeQuery = true, value = "" +
//            "select u.* from adm_users u " +
//            "left join chat_room c on CONVERT(c.sender_id, INT) = u.id " +
//            "where 1=1 and u.id = :id ")
//    List<AdmUser> loadListChatRecent(Long id);

    @Query("select u from ViewAdmUser u left join ChatRoom c on CAST(c.senderId AS int) = u.id  where u.id = :id")
    List<ViewAdmUser> loadListChatRecent(Long id);
}
