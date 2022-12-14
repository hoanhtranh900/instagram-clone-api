package com.sangnk.service;

import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdmUserService<E> {
    Optional<E> save(E entity);
    Optional<E> update(E entity);
    Optional<E> get(Long id);
    Page<E> getPaging(Pageable pageable);
    List<E> getAll();
    Boolean deleteById(Long id);
    Boolean deleteAll();

    Page<ViewAdmUser> getPage(SearchForm searchObject, Pageable pageable);
    List<AdmUser> deleteByIds(List<Long> ids);
    List<AdmUser> locks(List<Long> ids);
    List<AdmUser> unlocks(List<Long> ids);

    AdmUser profile(String username);
    AdmUser updateProfile(AdmUser form) throws BadRequestException;
    AdmUser changePass(AdmUser form) throws BadRequestException;

    AdmUser add(AdmUser form) throws BadRequestException;

    AdmUser edit(AdmUser form) throws BadRequestException;

    List<ViewAdmUser> getListChatRecent();

    AdmUser getProfile(Long id);
}
