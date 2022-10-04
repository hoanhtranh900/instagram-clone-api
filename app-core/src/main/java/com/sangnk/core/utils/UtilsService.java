package com.sangnk.core.utils;

import com.sangnk.core.contants.ConstantString;
import com.sangnk.core.contants.Constants;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.base.Creatable;
import com.sangnk.core.entity.base.Deletable;
import com.sangnk.core.entity.base.Updatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UtilsService {


    public static String standardlizeIsdn(String rawPhoneNumber) {
        if (rawPhoneNumber == null || rawPhoneNumber.trim().isEmpty()) return rawPhoneNumber;
        rawPhoneNumber = rawPhoneNumber.trim();
        if (rawPhoneNumber.startsWith("0")) return rawPhoneNumber.substring(1);
        else if (rawPhoneNumber.startsWith("84")) return rawPhoneNumber.substring(2);
        else if (rawPhoneNumber.startsWith("+84")) return rawPhoneNumber.substring(3);
        else return rawPhoneNumber;
    }

    /**
     * @param repository
     * @param model
     */
    public <DomainType extends Creatable, IDFieldType extends Serializable> void save(
            JpaRepository<DomainType, IDFieldType> repository, DomainType model) {

        Optional<AdmUser> user = UtilsCommon.getUserLogin();
        populateForSave(model, user.get());
        repository.save(model);
    }
    public <DomainType extends Creatable, IDFieldType extends Serializable> void saveAndFlush(
            JpaRepository<DomainType, IDFieldType> repository, DomainType model) {

        Optional<AdmUser> user = UtilsCommon.getUserLogin();
        populateForSave(model, user.get());
        repository.saveAndFlush(model);
    }

    public <DomainType extends Creatable, IDFieldType extends Serializable> void save(
            JpaRepository<DomainType, IDFieldType> repository, List<DomainType> models) {
        Optional<AdmUser> user = UtilsCommon.getUserLogin();
        H.each(models, (index, model) -> populateForSave(model, user.get()));
        repository.saveAll(models);
    }

    private void populateForSave(Creatable model, AdmUser token) {
        if (model instanceof Deletable)
            ((Deletable) model).setIsDelete(ConstantString.IS_DELETE.active);
        model.setCreateTime(Calendar.getInstance().getTime());
//        model.setCreatorId(token.getDelegateToUserId() != null ? token.getDelegateToUserId() : token.getUserId());
//        model.setCreatorName(token.getDelegateToUserId() != null ? token.getDelegateToFullName() : token.getFullName());
        model.setCreatorId(token.getId());
        model.setCreatorName(token.getFullName());
        this.populateForUpdate(model, token);
    }

    public <DomainType extends Creatable, IDFieldType extends Serializable> void update(
            JpaRepository<DomainType, IDFieldType> repository, List<DomainType> models) {
        Optional<AdmUser> user = UtilsCommon.getUserLogin();
        H.each(models, (index, model) -> populateForUpdate(model, user.get()));
        repository.saveAll(models);
    }

    /**
     * @param repository
     * @param model
     */
    public <DomainType extends Creatable, IDFieldType extends Serializable> void update(
            JpaRepository<DomainType, IDFieldType> repository, DomainType model) {

        Optional<AdmUser> user = UtilsCommon.getUserLogin();
        populateForUpdate(model, user.get());
        repository.save(model);
    }

    private void populateForUpdate(Updatable model, AdmUser token) {
        model.setUpdateTime(Calendar.getInstance().getTime());
//        model.setUpdatorId(token.getDelegateToUserId() != null ? token.getDelegateToUserId() : token.getUserId());
//        model.setUpdatorName(token.getDelegateToUserId() != null ? token.getDelegateToFullName() : token.getFullName());
        model.setUpdatorId(token.getId());
        model.setUpdatorName(token.getFullName());
    }

    /**
     * @param repository
     * @param model
     */
    public <DomainType extends Deletable, IDFieldType extends Serializable> void delete(
            JpaRepository<DomainType, IDFieldType> repository, DomainType model) {
        Optional<AdmUser> user = UtilsCommon.getUserLogin();
        model.setIsDelete(ConstantString.IS_DELETE.delete);
        populateForUpdate(model, user.get());
        repository.save(model);
    }

    public <DomainType extends Deletable, IDFieldType extends Serializable> void delete(
            JpaRepository<DomainType, IDFieldType> repository, List<DomainType> models) {
        if (!H.isTrue(models)) return;
        Optional<AdmUser> user = UtilsCommon.getUserLogin();
        H.each(models, (index, model) -> {
            model.setIsDelete(ConstantString.IS_DELETE.delete);
            populateForUpdate(model, user.get());
        });
        repository.saveAll(models);
    }

    /**
     * @param body
     * @param url
     * @param readTimeout
     * @param connectionTimeout
     * @param clazz
     * @param <BodyClass>
     * @param <ReturnClass>
     * @return
     */
    public <BodyClass, ReturnClass> ReturnClass postUploadRequest(
            BodyClass body, String url, int readTimeout, int connectionTimeout, Class<ReturnClass> clazz) {
        return this.postRequest(body, url, readTimeout, connectionTimeout, clazz, MediaType.MULTIPART_FORM_DATA);
    }

    public <BodyClass, ReturnClass> ReturnClass postRequest(
            BodyClass body, String url, int readTimeout, int connectionTimeout, Class<ReturnClass> clazz, MediaType contentType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        HttpEntity<BodyClass> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        requestFactory.setReadTimeout(readTimeout * 1000);
        requestFactory.setConnectTimeout(connectionTimeout * 1000);
        return restTemplate.postForEntity(url, requestEntity, clazz).getBody();
    }

    public <T extends Deletable> T returnNullIfDeleted(T object) {
        return object != null && ConstantString.IS_DELETE.active.equals(object.getIsDelete()) ? object : null;
    }

    public Boolean isIpad(String modelCode) {
        return H.isTrue(modelCode) && modelCode.trim().toLowerCase().startsWith("ipad");
    }

    public Boolean isAndroid(String os) {
        return H.isTrue(os) && os.trim().toLowerCase().startsWith("android");
    }

    public Boolean isIos(String os) {
        return H.isTrue(os) && os.trim().toLowerCase().startsWith("ios");
    }

    public Boolean isMobile(String os) {
        return isAndroid(os) || isIos(os);
    }


}
