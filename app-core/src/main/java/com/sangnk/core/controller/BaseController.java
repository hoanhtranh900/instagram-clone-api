package com.sangnk.core.controller;

import com.sangnk.core.contants.Constants;
import com.sangnk.core.exception.UnauthorizedException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BaseController<E> {

    //@DeleteMapping("/{id}")
    //ResponseEntity delete(@RequestParam("id") Long id);

    //@DeleteMapping("/all")
    //ResponseEntity deleteAll();

    //@PostMapping
    //ResponseEntity save(@RequestBody E entity);

    //@PutMapping
    //ResponseEntity update(@RequestBody E entity);

    @ApiOperation(notes = Constants.NOTE_API + "empty_note", value = "Chi tiết theo ID", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping("/{id}")
    ResponseEntity get(@PathVariable("id") Long id) throws UnauthorizedException;

    @ApiOperation(notes = Constants.NOTE_API + "empty_note", value = "Danh sách phân trang", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping("/getPaging")
    ResponseEntity getPaging(Pageable pageable) throws UnauthorizedException;

    @ApiOperation(notes = Constants.NOTE_API + "empty_note", value = "Danh sách ALL", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping("/all")
    ResponseEntity getAll() throws UnauthorizedException;

}
