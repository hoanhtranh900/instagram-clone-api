package com.sangnk.core.controller;

import com.sangnk.core.contants.ConstantAuthor;
import com.sangnk.core.dto.response.ResponseData;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.exception.Result;
import com.sangnk.core.exception.UnauthorizedException;
import com.sangnk.core.service.BaseService;
import com.sangnk.core.utils.UtilsCommon;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class BaseControllerImpl<E, S extends BaseService<E>> implements BaseController<E> {

	private final S service;
    private final MessageSource messageSource;

    protected BaseControllerImpl(S service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
    }

    /*@Override
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(new ResponseData(service.deleteById(id), Result.SUCCESS), HttpStatus.OK);
    }*/

    /*@Override
    public ResponseEntity deleteAll() {
        return new ResponseEntity(new ResponseData(service.deleteAll(), Result.SUCCESS), HttpStatus.OK);
    }*/

    /*@Override
    public ResponseEntity<ResponseData> save(@RequestBody E entity) {
        E e = service.save(entity).orElseThrow(() -> new BaseException(
                        String.format(ErrorType.ENTITY_NOT_SAVED.getDescription(), entity.toString())
                ));
        return new ResponseEntity(new ResponseData(e, Result.SUCCESS), HttpStatus.OK);
    }*/

    /*@Override
    public ResponseEntity update(@RequestBody E entity) {
        E e = service.update(entity)
                .orElseThrow(() -> new BaseException(
                        String.format(ErrorType.ENTITY_NOT_UPDATED.getDescription(), entity)
                ));
        return new ResponseEntity(new ResponseData(e, Result.SUCCESS), HttpStatus.OK);
    }*/

    @Override
    public ResponseEntity get(Long id) throws UnauthorizedException {
        E e = service.get(id).orElseThrow(() -> new BaseException( messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"Entity"}, UtilsCommon.getLocale())));
        return new ResponseEntity(new ResponseData(e, Result.SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPaging(Pageable pageable) throws UnauthorizedException {
    	return new ResponseEntity(new ResponseData(service.getPaging(pageable), Result.SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAll() throws UnauthorizedException {
        return new ResponseEntity(new ResponseData(service.getAll(), Result.SUCCESS), HttpStatus.OK);
    }

}
