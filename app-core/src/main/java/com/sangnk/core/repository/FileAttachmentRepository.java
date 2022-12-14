package com.sangnk.core.repository;

import com.sangnk.core.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileAttachmentRepository extends JpaSpecificationExecutor<FileAttachment>, org.springframework.data.jpa.repository.JpaRepository<FileAttachment, Long> {

    Optional<FileAttachment> findByIdAndIsDelete(Long id, Long isDelete);

    @Query("select a from FileAttachment a where a.isDelete =:isDelete and a.objectId =:objectId and  a.objectType = :objectType and (:storageType is null or a.storageType = :storageType) ")
    List<FileAttachment> findByObjectIdAndObjectTypeAndIsDelete(Long objectId, Long objectType, String storageType, Long isDelete);

    @Query("select a from FileAttachment a where a.isDelete =:isDelete and a.objectId =:objectId and ( COALESCE(:objectType) is null or a.objectType in (:objectType) ) and ( COALESCE(:storageType) is null or a.storageType in (:storageType)) ")
    List<FileAttachment> findByObjectIdAndObjectTypeInAndIsDeleteAndStorageTypeIn(Long objectId, List<Long> objectType, Long isDelete, List<String> storageType);

    @Query("select a from FileAttachment a where a.isDelete = :isDelete and a.objectId = :objectId and (:objectType is null or a.objectType = :objectType)")
    List<FileAttachment> getAllByObject(Long objectId, Long isDelete, Long objectType);

    @Query("select a from FileAttachment a where a.isDelete =:isDelete and a.id in (:ids) and (:objectType is null or a.objectType = :objectType) ")
    List<FileAttachment> findByIdInAndObjectType(List<Long> ids, Long isDelete, Long objectType);

    @Query("select a from FileAttachment a where a.isDelete = :isDelete and a.objectId = :objectId")
    List<FileAttachment> findByObjectIdAndIsdelete(Long objectId, Long isDelete);
}
