package com.sangnk.core.repository;

import com.sangnk.core.entity.AdmUser;
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
}
