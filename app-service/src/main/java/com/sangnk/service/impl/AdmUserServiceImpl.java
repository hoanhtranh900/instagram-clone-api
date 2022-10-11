package com.sangnk.service.impl;

import com.sangnk.core.contants.Constants;
import com.sangnk.core.dto.request.LoginRequest;
import com.sangnk.core.dto.response.UserInfo;
import com.sangnk.core.entity.*;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.exception.BadRequestException;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.exception.UnauthorizedException;
import com.sangnk.core.service.BaseServiceImpl;
import com.sangnk.core.utils.*;
import com.sangnk.service.*;
import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.repository.AdmUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * TODO: write you class description here
 *
 * @author
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AdmUserServiceImpl extends BaseServiceImpl<AdmUser, AdmUserRepository> implements AdmUserService<AdmUser> {
    public AdmUserServiceImpl(AdmUserRepository repository) {
        super(repository);
    }
    @Autowired private MessageSource messageSource;
    @Autowired private AdmUserRepository admUserRepository;
    @PersistenceContext private EntityManager entityManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private SrsSystemSercice srsSystemSercice;

    @Autowired
    private UtilsService utilsService;

    @Override
    public Page<ViewAdmUser> getPage(SearchForm searchObject, Pageable pageable) {
        Page<ViewAdmUser> page = null;
        try {
            List<ViewAdmUser> list = new ArrayList<>();
            String hql = " from ViewAdmUser u where 1=1 ";
            QueryBuilder builder = new QueryBuilder(entityManager, "select count(u)", new StringBuffer(hql), false);
            builder.and(QueryUtils.EQ, "u.isDelete", ConstantString.IS_DELETE.active);

            List<QueryBuilder.ConditionObject> conditionObjects = new ArrayList<>();
            conditionObjects.add(new QueryBuilder.ConditionObject(QueryUtils.EQ,"u.status", ConstantString.STATUS.active));
            conditionObjects.add(new QueryBuilder.ConditionObject(QueryUtils.EQ,"u.status", ConstantString.STATUS.lock));
            builder.andOrListNative(conditionObjects);

            if (StringUtils.isNotBlank(searchObject.getUsername())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.username)", "%" + searchObject.getUsername().trim().toUpperCase() + "%");
            }
            if (StringUtils.isNotBlank(searchObject.getPhoneNumber())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.phoneNumber)", "%" + searchObject.getPhoneNumber().trim().toUpperCase() + "%");
            }
            if (StringUtils.isNotBlank(searchObject.getStatus())) {
                builder.and(QueryUtils.EQ, "u.status", Long.parseLong(searchObject.getStatus().trim()));
            }
            if (StringUtils.isNotBlank(searchObject.getEmail())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.email)", "%" + searchObject.getEmail().trim().toUpperCase() + "%");
            }
            if (StringUtils.isNotBlank(searchObject.getFullName())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.fullName)", "%" + searchObject.getFullName().trim().toUpperCase() + "%");
            }
            if (StringUtils.isNotBlank(searchObject.getPosition())) {
                builder.and(QueryUtils.LIKE, "UPPER(u.position)", "%" + searchObject.getPosition().trim().toUpperCase() + "%");
            }

            Query query = builder.initQuery(false);
            int count = Integer.parseInt(query.getSingleResult().toString());
            pageable.getSort().iterator().forEachRemaining(order -> {
                builder.addOrder("u." + order.getProperty(), order.getDirection().isAscending() ? "ASC" : "DESC");
            });
            builder.addOrder("u.createTime", QueryUtils.DESC);
            builder.setSubFix("select u");
            query = builder.initQuery(ViewAdmUser.class);
            if(pageable.getPageSize() > 0){
                query.setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset()))).setMaxResults(pageable.getPageSize());
            }
            list = query.getResultList();


            if (list != null) {
                page = new PageImpl<>(list, pageable, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public List<AdmUser> deleteByIds(List<Long> ids) {
        List<AdmUser> users = admUserRepository.loadByListIds(ids);
        for (AdmUser user: users) {
            user.setIsDelete(ConstantString.IS_DELETE.delete);
//            admUserRepository.save(user);
            utilsService.save(admUserRepository, user);
        }
        return users;
    }

    @Override
    public List<AdmUser> locks(List<Long> ids) {
        List<AdmUser> users = admUserRepository.loadByListIds(ids);
        for (AdmUser user: users) {
            user.setStatus(ConstantString.STATUS.lock);
            utilsService.save(admUserRepository, user);
        }
        return users;
    }

    @Override
    public List<AdmUser> unlocks(List<Long> ids) {
        List<AdmUser> users = admUserRepository.loadByListIds(ids);
        for (AdmUser user: users) {
            user.setStatus(ConstantString.STATUS.active);
            utilsService.save(admUserRepository, user);
        }
        return users;
    }

    @Override
    public AdmUser profile(String username) {
        AdmUser user = admUserRepository.findByUsername(username).orElseThrow(() -> new BaseException(messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"User"}, UtilsCommon.getLocale())));
        return user;
    }

    @Override
    public AdmUser updateProfile(AdmUser form) throws BadRequestException {
        // lấy user DB
        AdmUser user = get(form.getId()).orElseThrow(
                () -> new BadRequestException(messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"User"}, UtilsCommon.getLocale()))
        );
        // sét lại data cần sửa
        user = user.updateProfile(form, user);
        // save lại thông tin
//        return save(user);
        utilsService.save(admUserRepository, user);
        return user;
    }

    @Override
    public AdmUser changePass(AdmUser form) throws BadRequestException {
        AdmUser user = admUserRepository.findByUsername(UtilsCommon.getUserLogin().get().getUsername()).orElseThrow(() -> new BaseException(messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"User"}, UtilsCommon.getLocale())));
        if (form.getPassword() == null || form.getRePassWord() == null || !form.getPassword().equals(form.getRePassWord())) {
            throw new BadRequestException(messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"Authorities"}, UtilsCommon.getLocale()));
        }
        if (!passwordEncoder.matches(form.getOldPassWord(), user.getPassword())) {
            throw new BadRequestException(messageSource.getMessage("MSG_PW_OLD_INVALID", null, UtilsCommon.getLocale()));
        }
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        utilsService.update(admUserRepository, user);
        return user;
    }

    @Override
    public AdmUser add(AdmUser form) throws BadRequestException {

        //if (documentBasicRepository.findByDocumentCode(dto.getDocumentCode()).isPresent())
//        if(admUserRepository.findAdmUserByEmail(form.getEmail()).isPresent()) {
//            throw new BadRequestException( messageSource.getMessage("error.EMAIL_EXIT", null, UtilsCommon.getLocale()));
//        }

        if(admUserRepository.findByUsername(form.getUsername()).isPresent()) {
            throw new BadRequestException((messageSource.getMessage("error.USERNAME_EXIT", null, UtilsCommon.getLocale())));
        }

        form.setPassword(passwordEncoder.encode(form.getPassword()));
        utilsService.save(admUserRepository, form);

        return form;
    }

    @Override
    public AdmUser edit(AdmUser form) throws BadRequestException {
        AdmUser bo = get(form.getId()).orElseThrow(
                () -> new BadRequestException(messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"AdmUser"}, UtilsCommon.getLocale()))
        );
        bo = bo.formToBo(form, bo);
        utilsService.update(admUserRepository, bo);
        return bo;
    }

    @Override
    public List<ViewAdmUser> getListChatRecent() {
        return admUserRepository.loadListChatRecent(UtilsCommon.getUserLogin().get().getId());
    }


}
